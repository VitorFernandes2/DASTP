package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.logic.game.Game;
import com.poker.model.payment.EServices;
import com.poker.model.payment.ServiceAdapter;
import com.poker.model.player.Player;
import com.poker.model.wallet.Wallet;
import com.poker.utils.DatabaseUtils;
import com.poker.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandAdapter {

    public static boolean addUser(String commandLine, Map<String, Player> playerList) {
        String[] tokens = StringUtils.tokenizeString(commandLine);

        if (tokens.length > 1) {
            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].contains("name=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "name=");

                    if (parameters.length > 0) {
                        String name = parameters[1];

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
                            } else {
                                return false;
                            }
                        }
                        return !userExists;
                    }
                }
            }
        }
        return false;
    }

    public static boolean loginUser(String commandLine, Map<String, Player> playerList) {
        String[] tokens = StringUtils.tokenizeString(commandLine);

        if (tokens.length > 1) {
            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].contains("name=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "name=");

                    if (parameters.length > 0) {
                        String name = parameters[1];

                        Player player = null;
                        try {
                            player = DatabaseUtils.getPlayerByName(name);
                        } catch (Exception e) {
                            return false;
                        }

                        if (!Objects.isNull(player) && !inPlayersArray(playerList, name)) {
                            playerList.put(name, player);
                        }
                        return !Objects.isNull(player);
                    }
                }
            }
        }
        return false;
    }

    private static boolean inPlayersArray(Map<String, Player> playerList, String name) {
        return playerList.get(name) != null;
    }

    public static void buyChips(String commandLine, Map<String, Player> playerList) {
        String[] tokens = StringUtils.tokenizeString(commandLine);
        String username = "";
        double value = 0;
        String method = "";

        if (tokens.length > 1) {
            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].contains("name=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "name=");
                    username = parameters[1];
                } else if (tokens[i].contains("value=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "value=");
                    String valueStr = parameters[1];
                    value = Double.parseDouble(valueStr);
                } else if (tokens[i].contains("payment=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "payment=");
                    method = parameters[1];
                }
            }

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
                        } catch (Exception e) {
                            return;
                        }
                    }
                }
            }
        }
    }

    public static boolean createFriendlyGame(String commandLine, ApplicationData data) {
        String[] tokens = StringUtils.tokenizeString(commandLine);
        Map<String, Player> playerList = data.getOnlinePlayers();

        if (tokens.length > 1) {
            String gameName = "";
            String creator = "";

            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].contains("name=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "name=");
                    gameName = parameters[1];
                } else if (tokens[i].contains("creator=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "creator=");
                    creator = parameters[1];
                }
            }

            if (!gameName.equals("") && !creator.equals("")) {
                Player player = playerList.get(creator);
                if (!Objects.isNull(player)) {
                    Map<String, Player> players = new HashMap<>();
                    players.put(creator, player);

                    Game game = new Game.Builder(gameName)
                            .setMinimumPlayers(2)
                            .setMinimumAmount(0)
                            .setPlayers(players)
                            .setCreator(player)
                            .build();

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
                        return game.addUserToGame(player);
                    }
                }
            }
        }
        return false;
    }
}
