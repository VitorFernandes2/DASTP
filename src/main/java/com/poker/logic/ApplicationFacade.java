package com.poker.logic;

import com.poker.logic.command.BetCommand;
import com.poker.logic.command.CheckCommand;
import com.poker.logic.command.CommandManager;
import com.poker.logic.command.FoldCommand;

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

    public void buyChips(String commandLine) {
        commandManager.buyChips(commandLine);
    }

    public boolean createFriendlyGame(String commandLine) {
        return commandManager.createFriendlyGame(commandLine);
    }

    public boolean joinGame(String commandLine) {
        return commandManager.joinGame(commandLine);
    }

    public boolean startGame(String commandLine) {
        return commandManager.startGame(commandLine);
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
        //TODO: create implementation
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
}
