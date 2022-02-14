package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final ApplicationData applicationData;
    private final List<ICommand> undoList;
    private final List<ICommand> redoList;

    public CommandManager(ApplicationData applicationData) {
        this.applicationData = applicationData;
        this.undoList = new ArrayList<>();
        this.redoList = new ArrayList<>();
    }

    public void apply(ICommand command) {
        command.execute(applicationData);
        // TODO: only add if done with success
        undoList.add(command);
        redoList.clear();
    }

    public void undo() {
        if (undoList.isEmpty()) {
            return;
        }

        ICommand lastCommand = undoList.remove(undoList.size() - 1);
        lastCommand.undo(applicationData);
        redoList.add(lastCommand);
    }

    public Map<String, Player> getOnlinePlayers() {
        return this.applicationData.getOnlinePlayers();
    }

    public void getOnlinePlayersToString() {
        CommandAdapter.getOnlinePlayersToString(this.getOnlinePlayers());
    }

    public boolean loginUser(String commandLine) {
        return CommandAdapter.loginUser(commandLine, this.getOnlinePlayers());
    }

    public void logoutUser(String commandLine) {
        CommandAdapter.logoutUser(commandLine, this.getOnlinePlayers());
    }

    public boolean addUser(String commandLine) {
        return CommandAdapter.addUser(commandLine, this.getOnlinePlayers());
    }

    public void buyChips(String commandLine) {
        CommandAdapter.buyChips(commandLine, this.getOnlinePlayers());
    }

    public boolean createFriendlyGame(String commandLine) {
        return CommandAdapter.createFriendlyGame(commandLine, this.applicationData);
    }

    public void startGame(String commandLine) {
        CommandAdapter.startGame(commandLine, this.applicationData);
    }

    public boolean joinGame(String commandLine) {
        return CommandAdapter.joinGame(commandLine, this.applicationData);
    }

    public void sendMessage(String commandLine) {
        CommandAdapter.sendMessage(commandLine, this.getOnlinePlayers());
    }

    public void addFriend(String commandLine) {
        CommandAdapter.addFriend(commandLine, this.getOnlinePlayers());
    }

    public void blockPlayer(String commandLine) {
        CommandAdapter.blockPlayer(commandLine, this.getOnlinePlayers());
    }

    public void startTurn(String commandLine) {
        CommandAdapter.startTurn(commandLine, applicationData);
    }
}
