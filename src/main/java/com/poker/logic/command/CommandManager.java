package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.logic.game.ETypeOfGame;
import com.poker.logic.game.Game;
import com.poker.model.constants.Constants;
import com.poker.model.filter.Log;
import com.poker.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final ApplicationData applicationData;
    private final List<Game> undoList;
    private final List<Game> redoList;

    public CommandManager(ApplicationData applicationData) {
        this.applicationData = applicationData;
        this.undoList = new ArrayList<>();
        this.redoList = new ArrayList<>();
    }

    public void apply(ICommand command) {
        command.execute(applicationData);
        Game currentGameCopy;
        try {
            currentGameCopy = (Game) applicationData.getGame(command.getGameName()).clone();
        } catch (CloneNotSupportedException e) {
            return;
        }
        undoList.add(currentGameCopy);
        redoList.clear();
    }

    public void redo() {
        if (redoList.isEmpty()) {
            return;
        }

        Game nextGame = redoList.get(redoList.size() -1);
        Game currentGame = applicationData.getGame(nextGame.getGameName());
        applicationData.getGamesList().put(nextGame.getGameName(), nextGame);
        undoList.add(currentGame);
    }

    public void undo() {
        if (undoList.isEmpty()) {
            return;
        }

        Game lastGame = undoList.remove(undoList.size() - 1);
        Game currentGame = applicationData.getGame(lastGame.getGameName());
        applicationData.getGamesList().put(lastGame.getGameName(), lastGame);
        redoList.add(currentGame);
    }

    public void saveGame() {
        try {
            applicationData.saveGames(Constants.GAME_NAME);
        } catch (IOException e) {
            Log.getInstance().addLog("Couldn't save the game " + e.getMessage());
        }
    }

    public void loadGame() {
        try {
            applicationData.loadGames(Constants.GAME_NAME);
        } catch (IOException | ClassNotFoundException e) {
            Log.getInstance().addLog("Couldn't load the game " + e.getMessage());
        }
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
        return CommandAdapter.createGame(commandLine, this.applicationData, ETypeOfGame.FRIENDLY);
    }

    public boolean createCompetitiveGame(String commandLine) {
        return CommandAdapter.createGame(commandLine, this.applicationData, ETypeOfGame.COMPETITIVE);
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

    public void showGameInfo(String commandLine) {
        CommandAdapter.showGameInfo(commandLine, applicationData.getGamesList());
    }

    public void listGames(ETypeOfGame typeOfGame) {
        CommandAdapter.getGamesToString(applicationData.getGamesList(), typeOfGame);
    }
}
