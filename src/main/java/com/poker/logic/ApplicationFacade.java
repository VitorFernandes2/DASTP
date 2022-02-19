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
}
