package com.poker.ui;

import com.poker.logic.ApplicationData;
import com.poker.logic.command.CommandAdapter;
import com.poker.model.enums.Command;
import com.poker.utils.DatabaseUtils;
import com.poker.utils.StringUtils;

public class TextUI {

    private static final ApplicationData appData = ApplicationData.getInstance();

    public static void start() {
        boolean running = true;

        try {
            DatabaseUtils.startDatabases();
        } catch (Exception e) {
            System.out.println("Error creating the databases, poker will shutdown!");
            return;
        }

        while (running) {
            System.out.print("♠ POKER  $ ");
            String commandLine = StringUtils.readString();
            String[] tokens = StringUtils.tokenizeString(commandLine);

            if (tokens.length > 0) {
                Command command = Command.fromString(tokens[0]);

                switch (command) {
                    case REGISTER:
                        boolean added = CommandAdapter.addUser(commandLine, appData.getOnlinePlayers());
                        System.out.println(added ? "User added with success" : "Error adding the user");
                        break;
                    case LOGIN:
                        boolean loggedIn = CommandAdapter.loginUser(commandLine, appData.getOnlinePlayers());
                        System.out.println(loggedIn ? "User logged in with success" : "Something went wrong or user does not exist!");
                        break;
                    case SHUTDOWN:
                        running = false;
                        break;
                }
            }
        }
    }

}