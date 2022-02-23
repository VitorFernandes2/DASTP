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
            List<String> commandsList = COMPETITIVE_GAME_WINNING; // DEBUG: change this Command List to change the mocked commands
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
                        appFacade.addUser(commandLine);
                        break;
                    case LOGIN:
                        appFacade.loginUser(commandLine);
                        break;
                    case LOGOUT:
                        appFacade.logoutUser(commandLine);
                        break;

                    // $$ Admin Functionalities $$
                    case CREATE_USER:
                        appFacade.createUser(commandLine);
                        break;
                    case EDIT_USER:
                        appFacade.editUser(commandLine);
                        break;
                    case KICK_USER:
                        appFacade.kickUser(commandLine);
                        break;
                    case KICK_FROM_GAME:
                        appFacade.kickFromGame(commandLine);
                        break;
                    case CHECK_USER_ACTIVITIES:
                        appFacade.checkUserActivities(commandLine);
                        break;
                    case SEE_GAME:
                        appFacade.seeGame(commandLine);
                        break;
                    case ADD_GAME:
                        appFacade.addGame(commandLine);
                        break;
                    case REMOVE_GAME:
                        appFacade.removeGame(commandLine);
                        break;

                    // $$ DEBUG Commands $$
                    case ADD_CARDS_TO_USER:
                        appFacade.addCardsToUser(commandLine);
                        break;
                    case ADD_CUSTOM_RANKINGS:
                        appFacade.addCustomRankings(commandLine);
                        break;
                    case REMOVE_CUSTOM_RANKING:
                        appFacade.removeRanking(commandLine);
                        break;
                    case SET_TABLE_CARDS:
                        appFacade.setTableCards(commandLine);
                        break;

                    // $$ Chat Functionalities $$
                    case LIST_PLAYERS:
                        appFacade.getOnlinePlayersToString(commandLine);
                        break;
                    case LIST_PLAYERS_DETAILS:
                        appFacade.listPlayersDetails();
                        break;
                    case LIST_FRIENDS:
                        appFacade.listPlayerFriends(commandLine);
                        break;
                    case LIST_BLOCKED:
                        appFacade.showPlayerBlockedPlayers(commandLine);
                        break;
                    case LIST_CHAMPIONSHIPS:
                        appFacade.showTournamentsInfo();
                        break;
                    case LIST_RANKING:
                        appFacade.listRankings();
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
                    case TRANSFER_MONEY:
                        appFacade.transferMoney(commandLine);
                        break;
                    case BUY_POKER_CHIPS:
                        appFacade.buyChips(commandLine);
                        break;
                    case CREATE_FRIENDLY_GAME:
                        boolean gameCreated = appFacade.createFriendlyGame(commandLine);
                        System.out.println("[Game] " + (gameCreated ? "Game created with success" : "Game was not created!"));
                        break;
                    case CREATE_COMPETITIVE_GAME:
                        appFacade.createCompetitiveGame(commandLine);
                        break;
                    case JOIN_GAME:
                        appFacade.joinGame(commandLine);
                        break;
                    case LEAVE_GAME:
                        appFacade.leaveGame(commandLine);
                        break;
                    case START_GAME:
                        appFacade.startGame(commandLine);
                        break;
                    case CREATE_TOURNAMENT:
                        appFacade.createTournament(commandLine);
                        break;
                    case JOIN_TOURNAMENT:
                        appFacade.joinTournament(commandLine);
                        break;
                    case START_TOURNAMENT:
                        appFacade.startTournament(commandLine);
                        break;
                    case START_FINAL_TOURNAMENT:
                        //TODO: [TBC] Test
                        appFacade.startFinalGame(commandLine);
                        break;
                    case BET:
                        appFacade.bet(commandLine);
                        break;
                    case CHECK:
                        appFacade.check(commandLine);
                        break;
                    case FOLD:
                        appFacade.fold(commandLine);
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
