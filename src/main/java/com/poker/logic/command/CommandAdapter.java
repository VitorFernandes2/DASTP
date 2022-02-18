package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.logic.factory.EFactory;
import com.poker.logic.factory.FactoryProvider;
import com.poker.logic.factory.game.GameCreationData;
import com.poker.logic.factory.game.GameFactory;
import com.poker.logic.game.ETypeOfGame;
import com.poker.logic.game.Game;
import com.poker.model.constants.Constants;
import com.poker.model.filter.FilterDecorator;
import com.poker.model.filter.Log;
import com.poker.model.filter.UserFilter;
import com.poker.model.payment.EServices;
import com.poker.model.payment.ServiceAdapter;
import com.poker.model.player.EPlayerRelation;
import com.poker.model.player.Player;
import com.poker.model.wallet.Wallet;
import com.poker.utils.DatabaseUtils;
import com.poker.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommandAdapter {
    private static final Log LOG = Log.getInstance();

    public static boolean addUser(String commandLine, Map<String, Player> playerList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String name = command.get(Constants.NAME_PARAMETER);

        if (name != null) {
            boolean userExists;
            try {
                userExists = DatabaseUtils.playerExistsByName(name);
            } catch (Exception e) {
                return false;
            }

            if (!userExists) {
                boolean userCreated;
                try {
                    userCreated = DatabaseUtils.createPlayer(name);
                } catch (Exception e) {
                    return false;
                }

                if (userCreated) {
                    playerList.put(name, new Player(name));
                    LOG.addLog(commandLine);
                } else {
                    return false;
                }
            }
            return !userExists;
        }

        return false;
    }

    public static boolean loginUser(String commandLine, Map<String, Player> playerList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String name = command.get(Constants.NAME_PARAMETER);
        if (name != null) {
            Player player;
            try {
                player = DatabaseUtils.getPlayerByName(name);
            } catch (Exception e) {
                return false;
            }

            if (!Objects.isNull(player) && !inPlayersArray(playerList, name)) {
                // Notify other players
                notifyNewLogin(playerList, name);

                playerList.put(name, player);
                LOG.addLog(commandLine);
            }
            return !Objects.isNull(player);
        }

        return false;
    }

    public static void logoutUser(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);
        onlinePlayers.remove(playerName);
    }

    private static void notifyNewLogin(Map<String, Player> onlinePlayers, String name) {
        // notify each online player if some of is friends logged in
        onlinePlayers.forEach((s, player) -> player.getFriends().forEach(friendName -> {
            if (name.equals(friendName))
                System.out.println("[System][To:" + player.getName() + "] The player " + name + " logged in!");
        }));
    }

    private static boolean inPlayersArray(Map<String, Player> playerList, String name) {
        return playerList.get(name) != null;
    }

    public static void buyChips(String commandLine, Map<String, Player> playerList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String username = command.get(Constants.NAME_PARAMETER);
        String valueStr = command.get(Constants.VALUE_PARAMETER);
        String method = command.get(Constants.METHOD_PARAMETER);
        double value = 0;

        if (username != null && valueStr != null && method != null) {
            value = Double.parseDouble(valueStr);

            if (value != 0 && !username.equals("") && !method.equals("")) {
                Player player = playerList.get(username);
                if (player != null) {
                    EServices service = EServices.fromString(method);
                    if (!service.equals(EServices.UNKNOWN)) {
                        ServiceAdapter serviceAdapter = new ServiceAdapter(service);
                        Wallet playerWallet = player.getWallet();
                        serviceAdapter.buy(value, playerWallet);
                        try {
                            DatabaseUtils.updateWallet(username, playerWallet);
                            LOG.addLog(commandLine);
                        } catch (Exception e) {
                            LOG.addLog("Erro ao atualizar a carteira: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public static void sendMessage(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String senderName = command.get(Constants.FROM_PARAMETER);
        String receiverName = command.get(Constants.TO_PARAMETER);

        // check if the players exists on the BD
        try {
            if (!DatabaseUtils.playerExistsByName(senderName)) {
                System.out.println("[System] " + senderName + " doesn't exist!");
                return;
            } else if (!DatabaseUtils.playerExistsByName(receiverName)) {
                System.out.println("[System] " + receiverName + " doesn't exist!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error trying to get players name!");
            return;
        }

        if (!inPlayersArray(onlinePlayers, senderName)) {
            System.out.println("[System] " + senderName + " is offline!");
            return;
        }

        System.out.println(!inPlayersArray(onlinePlayers, receiverName) ?
                "[System] " + receiverName + " is offline!" :
                onlinePlayers.get(receiverName).getPlayersBlocked().contains(senderName) ?
                        "[System] You can't send messages to this user!" :
                        "[From:" + senderName + "][To:" + receiverName + "] "
                                + command.get(Constants.COMMAND_LAST_DIVISION));
    }

    public static boolean createGame(String commandLine, ApplicationData data, ETypeOfGame typeOfGame) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        Map<String, Player> playerList = data.getOnlinePlayers();

        if (command.size() > 3) {
            String gameName = command.get(Constants.NAME_PARAMETER);
            String creator = command.get(Constants.CREATOR_PARAMETER);

            if (!gameName.equals("") && !creator.equals("")) {
                Player player = playerList.get(creator);
                if (!Objects.isNull(player)) {
                    GameFactory factory = (GameFactory) FactoryProvider.getFactory(EFactory.GAMES);
                    if (factory == null) {
                        return false;
                    }

                    // TODO: deal with this minimumAmount, custom for competitive games, and the default value from Constants for friendly games
                    GameCreationData gameCreationData = new GameCreationData(gameName, Constants.FRIENDLY_GAME_MINIMUM_PLAYERS, 0, typeOfGame, player);
                    Game game = factory.createObject(gameCreationData);
                    game.addPlayer(player, 1); // TODO: adapt this fee on the competitive game

                    return data.addGame(game);
                }
            }
        }
        return false;
    }

    public static boolean joinGame(String commandLine, ApplicationData data) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER);
        String playerName = command.get(Constants.PLAYER_PARAMETER);

        Map<String, Player> playerList = data.getOnlinePlayers();
        Map<String, Game> gameList = data.getGamesList();

        if (gameList != null && playerName != null) {
            if (!gameName.equals("")) {
                Game game = gameList.get(gameName);
                if (!Objects.isNull(game) && !playerName.equals("")) {
                    Player player = playerList.get(playerName);
                    if (!Objects.isNull(player)) {
                        return game.addPlayer(player, game.getMinimumAmount());
                    }
                }
            }
        }

        return false;
    }

    public static void startGame(String commandLine, ApplicationData data) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER);
        String playerName = command.get(Constants.PLAYER_PARAMETER);

        Map<String, Player> playerList = data.getOnlinePlayers();
        Map<String, Game> gameList = data.getGamesList();

        if (gameName != null && playerName != null && !gameName.equals("")) {
            Game game = gameList.get(gameName);
            if (!Objects.isNull(game) && !playerName.equals("")) {
                Player player = playerList.get(playerName);
                if (!Objects.isNull(player)) {
                    game.startGame(player);
                    return;
                }
            }
        }
        System.out.println("[Game] Game not started");
    }

    public static void getOnlinePlayersToString(Map<String, Player> onlinePlayers) {
        StringBuilder str = new StringBuilder("# Online players:");
        onlinePlayers.forEach((s, player) -> str.append("\n ").append(s));
        System.out.println(str);
    }

    public static void addFriend(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        Player player = onlinePlayers.get(command.get(Constants.PLAYER_PARAMETER));
        player.addFriend(command.get(Constants.ADD_PARAMETER));
        player.removeBlockedPlayer(command.get(Constants.ADD_PARAMETER));
        System.out.println("Player added with success");
    }

    public static void blockPlayer(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        Player player = onlinePlayers.get(command.get(Constants.PLAYER_PARAMETER));
        player.blockPlayer(command.get(Constants.BLOCK_PARAMETER));
        player.removeFriend(command.get(Constants.BLOCK_PARAMETER));
        System.out.println("Player blocked with success");
    }

    public static void showGameInfo(String commandLine, Map<String, Game> games) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get("game");
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            String name = entry.getKey();
            Game game = entry.getValue();
            if (name.equals(gameName)) {
                System.out.println(game.showGameInfo(name));
                break;
            }
        }
    }

    public static void getGamesToString(Map<String, Game> gamesList, ETypeOfGame typeOfGame) {
        StringBuilder str = new StringBuilder();
        str.append("NAME\t\tCREATOR\n");
        gamesList.forEach((s, game) -> {
            if (game.getTypeOfGame().equals(typeOfGame)) {
                str.append(game.getGameName()).append("\t\t").append(game.getCreatorName()).append("\n");
            }
        });
        System.out.println(str);
    }

    public static void showPlayerRelationList(String commandLine, Map<String, Player> onlinePlayers, EPlayerRelation relation) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String username = command.get(Constants.NAME_PARAMETER);

        if (username != null && username.length() > 0) {
            Player player = onlinePlayers.get(username);
            if (player != null) {
                List<String> relatedPlayers = null;
                switch (relation) {
                    case FRIENDS:
                        relatedPlayers = player.getFriends();
                        break;
                    case BLOCKEDS:
                        relatedPlayers = player.getPlayersBlocked();
                        break;
                }
                StringBuilder strOut = new StringBuilder();
                strOut.append("List of ").append(relation).append(" of ").append(username).append(":\n");
                relatedPlayers.forEach(s -> strOut.append("> ").append(s).append("\n"));
                System.out.println(strOut);
            }
        }
    }

    public static void removeGame(String commandLine, Map<String, Game> gamesList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER);
        if (gameName != null && gameName.length() > 0) {
            gamesList.remove(gameName);
        }
    }

    public static void editUser(String commandLine, Map<String, Player> onlinePlayers) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);
        String playerNewName = command.get(Constants.NEW_NAME_PARAMETER);

        if (playerNewName != null && playerName != null &&
                playerName.length() > 0 && playerNewName.length() > 0) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                player.setName(playerNewName);
            }
        }
    }

    public static void kickUser(String commandLine, Map<String, Player> onlinePlayers, Map<String, Game> gamesList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && playerName.length() > 0) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                gamesList.forEach((s, game) -> {
                    boolean containsKey = game.getPlayersList().containsKey(playerName);
                    if (!containsKey) {
                        onlinePlayers.remove(playerName);
                        try {
                            DatabaseUtils.removePlayerFromDB(playerName);
                        } catch (Exception e) {
                            System.out.println("Error removing the player from the database");
                        }
                    }
                });
            }
        }
    }

    public static void checkUserActivities(String commandLine, Map<String, Player> onlinePlayers, Map<String, Game> gamesList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && playerName.length() > 0) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                FilterDecorator search = new UserFilter(LOG, playerName);
                System.out.println("User Activities:\n" + search.filter());
            }
        }
    }

    public static void seeGame(String commandLine, Map<String, Player> onlinePlayers, Map<String, Game> gamesList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER);

        if (gameName != null && gameName.length() > 0) {
            Game game = gamesList.get(gameName);
            if (game != null) {
                StringBuilder str = new StringBuilder();
                str.append("Creator: ").append(game.getCreatorName()).append("\n\n");
                str.append("Players\t\t");
                game.getPlayersList().forEach((s, player) -> str.append("> ").append(s).append("\n"));
                System.out.println(str);
            }
        }
    }
}
