package com.poker.service.command;

import com.poker.model.player.Player;
import com.poker.utils.DatabaseUtils;
import com.poker.utils.StringUtils;

import java.util.List;

public class CommandAdapter {

    public static boolean addUser(String commandLine, List<Player> playerList) {
        String[] tokens = StringUtils.tokenizeString(commandLine);

        if (tokens.length > 1) {
            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].contains("name=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "name=");

                    if (parameters.length > 0) {
                        String name = parameters[1];

                        boolean userExists;
                        try {
                            userExists = DatabaseUtils.getPlayerByName(name);
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
                                playerList.add(new Player(name));
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

    public static boolean loginUser(String commandLine, List<Player> playerList) {
        String[] tokens = StringUtils.tokenizeString(commandLine);

        if (tokens.length > 1) {
            for (int i = 1; i < tokens.length; i++) {
                if (tokens[i].contains("name=")) {
                    String[] parameters = StringUtils.tokenizeString(tokens[i], "name=");

                    if (parameters.length > 0) {
                        String name = parameters[1];

                        boolean userExists;
                        try {
                            userExists = DatabaseUtils.getPlayerByName(name);
                        } catch (Exception e) {
                            return false;
                        }

                        if (userExists && !inPlayersArray(playerList, name)) {
                            playerList.add(new Player(name));
                        }
                        return userExists;
                    }
                }
            }
        }
        return false;
    }

    private static boolean inPlayersArray(List<Player> playerList, String name) {
        for (var player : playerList) {
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
