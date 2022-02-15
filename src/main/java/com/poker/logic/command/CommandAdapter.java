package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.logic.factory.EFactory;
import com.poker.logic.factory.FactoryProvider;
import com.poker.logic.factory.game.GameCreationData;
import com.poker.logic.factory.game.GameFactory;
import com.poker.logic.game.ETypeOfGame;
import com.poker.logic.game.Game;
import com.poker.model.constants.Constants;
import com.poker.model.filter.Log;
import com.poker.model.payment.EServices;
import com.poker.model.payment.ServiceAdapter;
import com.poker.model.player.Player;
import com.poker.model.wallet.Wallet;
import com.poker.utils.DatabaseUtils;
import com.poker.utils.StringUtils;

import java.util.LinkedHashMap;
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
                    Map<String, Player> players = new LinkedHashMap<>();
                    players.put(creator, player);

                    GameFactory factory = (GameFactory) FactoryProvider.getFactory(EFactory.GAMES);

                    if (factory == null) {
                        return false;
                    }

                    GameCreationData gameCreationData = new GameCreationData(gameName, Constants.FRIENDLY_GAME_MINIMUM_PLAYERS, 0, typeOfGame, players, player);
                    Game game = factory.createObject(gameCreationData);

                    return data.addGame(game);
                }
            }
        }
        return false;
    }

    public static boolean joinGame(String commandLine, ApplicationData data) {
        String[] tokens = StringUtils.tokenizeString(commandLine);
        Map<String, Player> playerList = data.getOnlinePlayers();
        Map<String, Game> gameList = data.getGamesList();

        // TODO: Optimize this code
        if (tokens.length > 1) {
            String gameName = "";
            String playerName = "";

            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].contains("name=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "name=");
                    gameName = parameters[1];
                } else if (tokens[i].contains("player=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "player=");
                    playerName = parameters[1];
                }
            }

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

    public static void startTurn(String commandLine, ApplicationData data) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER); // TODO: change this to "game"
        data.getGamesList().get(gameName).startTurn();
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
}
