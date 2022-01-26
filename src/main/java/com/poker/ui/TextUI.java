package com.poker.ui;

import com.poker.logic.ApplicationData;
import com.poker.logic.ApplicationFacade;
import com.poker.logic.command.BetCommand;
import com.poker.logic.command.CommandAction;
import com.poker.logic.command.CommandAdapter;
import com.poker.logic.command.CommandManager;
import com.poker.model.enums.ECommand;
import com.poker.utils.DatabaseUtils;
import com.poker.utils.StringUtils;

public class TextUI {

    private ApplicationFacade logic;

    public TextUI(ApplicationData data) {
        this.logic = new ApplicationFacade(data);
    }

    public void start() {
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
                ECommand command = ECommand.fromString(tokens[0]);

                switch (command) {
                    case REGISTER:
                        boolean added = logic.addUser(commandLine);
                        System.out.println(added ? "User added with success" : "Error adding the user");
                        break;
                    case LOGIN:
                        boolean loggedIn = logic.loginUser(commandLine);
                        System.out.println(loggedIn ? "User logged in with success" : "Something went wrong or user does not exist!");
                        break;
                    case BUY_POKER_CHIPS:
                        logic.buyChips(commandLine);
                        break;
                    case SHUTDOWN:
                        running = false;
                        break;
                }
            }
        }
    }

}
