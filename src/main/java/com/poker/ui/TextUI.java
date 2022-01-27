package com.poker.ui;

import com.poker.logic.ApplicationData;
import com.poker.logic.ApplicationFacade;
import com.poker.model.enums.ECommand;
import com.poker.utils.DatabaseUtils;
import com.poker.utils.StringUtils;

public class TextUI {

    private final ApplicationFacade logic;

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

                    // $$ Chat Functionalities $$
                    case LIST_PLAYERS:
                        logic.getOnlinePlayersToString(commandLine);
                        break;
                    case ADD_FRIEND:
                        logic.addFriend(commandLine);
                        break;
                    case BLOCK_PLAYER:
                        logic.blockPlayer(commandLine);
                        break;
                        //FIXME: remove blocked and added players or not
                    case SEND_MESSAGE:
                        logic.sendMessage(commandLine);
                        break;

                    // $$ Virtual Money $$
                    case BUY_POKER_CHIPS:
                        logic.buyChips(commandLine);
                        break;
                    case CREATE_FRIENDLY_GAME:
                        boolean gameCreated = logic.createFriendlyGame(commandLine);
                        System.out.println(gameCreated ? "Game created with success" : "Game was not created!");
                        break;
                    case JOIN_GAME:
                        boolean joinedGame = logic.joinGame(commandLine);
                        System.out.println(joinedGame ? "User joined the game" : "User couldn't join the game!");
                        break;
                    case START_GAME:
                        boolean gameStarted = logic.startGame(commandLine);
                        System.out.println(gameStarted ? "Game started" : "Game could not be started");
                        break;
                    case BET:
                        boolean betted = logic.bet(commandLine);
                        System.out.println(betted ? "Bet correctly made" : "Can't perform bet");
                        break;
                    case CHECK:
                        boolean skipped = logic.check(commandLine);
                        System.out.println(skipped ? "Skip correctly made" : "Can't perform skip");
                        break;
                    case FOLD:
                        boolean gaveUp = logic.fold(commandLine);
                        System.out.println(gaveUp ? "User gave up" : "Can't perform gave up for the selected user");
                        break;
                    case SHOW_GAME_INFO:
                        logic.showGameInfo(commandLine);
                        break;
                    case SHUTDOWN:
                        running = false;
                        break;
                    default: ECommand.getCommandsExample();
                }
            }
        }
    }

}
