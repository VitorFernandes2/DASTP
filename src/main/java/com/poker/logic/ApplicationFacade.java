package com.poker.logic;

import com.poker.logic.command.BetCommand;
import com.poker.logic.command.CheckCommand;
import com.poker.logic.command.CommandManager;
import com.poker.logic.command.FoldCommand;
import com.poker.logic.game.ETypeOfGame;

public class ApplicationFacade {
    private final CommandManager commandManager;

    public ApplicationFacade(ApplicationData data) {
        this.commandManager = new CommandManager(data);
    }

    public boolean addUser(String commandLine) {
        return commandManager.addUser(commandLine);
    }

    public boolean loginUser(String commandLine) {
        return commandManager.loginUser(commandLine);
    }

    public void logoutUser(String commandLine) {
        commandManager.logoutUser(commandLine);
    }

    public void buyChips(String commandLine) {
        commandManager.buyChips(commandLine);
    }

    public boolean createFriendlyGame(String commandLine) {
        return commandManager.createFriendlyGame(commandLine);
    }

    public boolean createCompetitiveGame(String commandLine) {
        return commandManager.createCompetitiveGame(commandLine);
    }

    public boolean joinGame(String commandLine) {
        return commandManager.joinGame(commandLine);
    }

    public void startGame(String commandLine) {
        commandManager.startGame(commandLine);
    }

    public boolean bet(String commandLine) {
        try {
            commandManager.apply(new BetCommand(commandLine));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean fold(String commandLine) {
        try {
            commandManager.apply(new FoldCommand(commandLine));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean check(String commandLine) {
        try {
            commandManager.apply(new CheckCommand(commandLine));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void showGameInfo(String commandLine) {
        commandManager.showGameInfo(commandLine);
    }

    public void sendMessage(String commandLine) {
        commandManager.sendMessage(commandLine);
    }

    public void getOnlinePlayersToString(String commandLine) {
        commandManager.getOnlinePlayersToString();
    }

    public void addFriend(String commandLine) {
        commandManager.addFriend(commandLine);
    }

    public void blockPlayer(String commandLine) {
        commandManager.blockPlayer(commandLine);
    }

    public void undo() {
        commandManager.undo();
    }

    public void save() {
        commandManager.saveGame();
    }

    public void redo() {
        commandManager.redo();
    }

    public void loadGame() {
        commandManager.loadGame();
    }

    public void listFriendlyGames() {
        commandManager.listGames(ETypeOfGame.FRIENDLY);
    }

    public void listCompetitiveGames() {
        commandManager.listGames(ETypeOfGame.COMPETITIVE);
    }

    public void listPlayerFriends(String commandLine) {
        commandManager.listPlayerFriends(commandLine);
    }

    public void showPlayerBlockedPlayers(String commandLine) {
        commandManager.showPlayerBlockedPlayers(commandLine);
    }

    public void createUser(String commandLine) {
        commandManager.createUser(commandLine);
    }

    public void addGame(String commandLine) {
        commandManager.addGame(commandLine);
    }

    public void removeGame(String commandLine) {
        commandManager.removeGame(commandLine);
    }

    public void editUser(String commandLine) {
        commandManager.editUser(commandLine);
    }

    public void kickUser(String commandLine) {
        commandManager.kickUser(commandLine);
    }

    public void checkUserActivities(String commandLine) {
        commandManager.checkUserActivities(commandLine);
    }

    public void seeGame(String commandLine) {
        commandManager.seeGame(commandLine);
    }

    public void addCardsToUser(String commandLine) {
        commandManager.addCardsToUser(commandLine);
    }

    public void listRankings() {
        commandManager.getRankings();
    }

    public void addCustomRankings(String commandLine) {
        commandManager.addCustomRankings(commandLine);
    }

    public void removeRanking(String commandLine) {
        commandManager.removeRanking(commandLine);
    }

    public void createTournament(String commandLine) {
        commandManager.createTournament(commandLine);
    }

    public void joinTournament(String commandLine) {
        commandManager.joinTournament(commandLine);
    }

    public void startTournament(String commandLine) {
        commandManager.startTournament(commandLine);
    }

    public void showTournamentsInfo() {
        commandManager.showTournamentsInfo();
    }

    public void startFinalGame(String commandLine) {
        commandManager.startFinalGame(commandLine);
    }

    public void listPlayersDetails() {
        commandManager.listPlayersDetails();
    }

    public void setTableCards(String commandLine) {
        commandManager.setTableCards(commandLine);
    }

    public void transferMoney(String commandLine) {
        commandManager.transferMoney(commandLine);
    }

    public void kickFromGame(String commandLine) {
        commandManager.kickFromGame(commandLine);
    }
}
