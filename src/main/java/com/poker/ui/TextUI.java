package com.poker.ui;

import com.poker.logic.ApplicationData;
import com.poker.logic.ApplicationFacade;
import com.poker.model.enums.ECommand;
import com.poker.utils.DatabaseUtils;
import com.poker.utils.StringUtils;

import java.util.List;

import static com.poker.tests.MockCommands.*;

public class TextUI {

    private final ApplicationFacade appFacade;

    public TextUI(ApplicationData data) {
        this.appFacade = new ApplicationFacade(data);
    }

    public void start() {
        boolean running = true;

        try {
            DatabaseUtils.startDatabases();
        } catch (Exception e) {
            System.out.println("Error creating the databases, poker will shutdown!");
            return;
        }

        appFacade.loadGame();

        while (running) {
            System.out.print("♠ POKER  $ ");
            String commandLine;
            List<String> commandsList = CREATE_GAME_WINNING; // DEBUG: change this Command List to change the mocked commands
            if (commandsList.isEmpty()) {
                commandLine = StringUtils.readString();
            } else {
                commandLine = commandsList.remove(0);
                System.out.println(commandLine);
            }
            String[] tokens = StringUtils.tokenizeString(commandLine);

            if (tokens.length > 0) {
                ECommand command = ECommand.fromString(tokens[0]);

                switch (command) {
                    case REGISTER:
                        boolean added = appFacade.addUser(commandLine);
                        System.out.println(added ? "User added with success!" : "Error adding the user!");
                        break;
                    case LOGIN:
                        boolean loggedIn = appFacade.loginUser(commandLine);
                        System.out.println(loggedIn ? "User logged in with success!" : "Something went wrong or user does not exist!");
                        break;
                    case LOGOUT:
                        appFacade.logoutUser(commandLine);
                        System.out.println("User logged out with success!");
                        break;

                    // $$ Chat Functionalities $$
                    case LIST_PLAYERS:
                        appFacade.getOnlinePlayersToString(commandLine);
                        break;
                    case ADD_FRIEND:
                        appFacade.addFriend(commandLine);
                        break;
                    case BLOCK_PLAYER:
                        appFacade.blockPlayer(commandLine);
                        break;
                    case SEND_MESSAGE:
                        appFacade.sendMessage(commandLine);
                        break;

                    // $$ Game Functionalities $$
                    case LIST_COMPETITIVE_GAMES:
                        appFacade.listCompetitiveGames();
                        break;
                    case LIST_FRIENDLY_GAMES:
                        appFacade.listFriendlyGames();
                        break;
                    case BUY_POKER_CHIPS:
                        appFacade.buyChips(commandLine);
                        break;
                    case CREATE_FRIENDLY_GAME:
                        boolean gameCreated = appFacade.createFriendlyGame(commandLine);
                        System.out.println("[Game] " + (gameCreated ? "Game created with success" : "Game was not created!"));
                        break;
                    case CREATE_COMPETITIVE_GAME:
                        boolean competitiveGameCreated = appFacade.createCompetitiveGame(commandLine);
                        System.out.println(competitiveGameCreated ? "Game created with success" : "Game was not created!");
                        break;
                    case JOIN_GAME:
                        boolean joinedGame = appFacade.joinGame(commandLine);
                        System.out.println(joinedGame ? "User joined the game" : "User couldn't join the game!");
                        break;
                    case START_GAME:
                        appFacade.startGame(commandLine);
                        appFacade.startTurn(commandLine);
//                        System.out.println(gameStarted ? "Game started" : "Game could not be started");
                        break;
                    case BET:
                        boolean betted = appFacade.bet(commandLine);
//                        System.out.println(betted ? "Bet correctly made" : "Can't perform bet");
                        break;
                    case CHECK:
                        boolean skipped = appFacade.check(commandLine);
//                        System.out.println(skipped ? "Skip correctly made" : "Can't perform skip");
                        break;
                    case FOLD:
                        boolean gaveUp = appFacade.fold(commandLine);
//                        System.out.println(gaveUp ? "User gave up" : "Can't perform gave up for the selected user");
                        break;
                    case SHOW_GAME_INFO:
                        appFacade.showGameInfo(commandLine);
                        break;
                    case UNDO:
                        appFacade.undo();
                        break;
                    case REDO:
                        appFacade.redo();
                        break;
                    case SHUTDOWN:
                        appFacade.save();
                        running = false;
                        break;
                    case HELP:
                        ECommand.getCommandsExample();
                        break;
                    default:
                        System.out.println("Wrong command ... please try again! Insert 'h' or 'help' to check the commands list!");
                }
            }
        }
    }

}
