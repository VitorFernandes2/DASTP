package com.poker.logic;

import com.poker.logic.game.Game;
import com.poker.model.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApplicationData {
    private static final ApplicationData applicationData = new ApplicationData();
    private final Map<String, Player> onlinePlayers;
    private final Map<String, Player> registeredPlayers;
    private final Map<String, Game> gamesList;

    private ApplicationData() {
        this.onlinePlayers = new HashMap<>();
        this.gamesList = new HashMap<>();
        registeredPlayers = new HashMap<>();
    }

    public static ApplicationData getInstance() {
        return applicationData;
    }

    public Map<String, Player> getOnlinePlayers() {
        return onlinePlayers;
    }

    public Map<String, Player> getRegisteredPlayers() {
        return registeredPlayers;
    }

    public Map<String, Game> getGamesList() {
        return gamesList;
    }

    private boolean gameExists(String gameName) {
        return !Objects.isNull(gamesList.get(gameName));
    }

    public boolean addGame(Game game) {
        String gameName = game.getGameName();
        boolean exists = gameExists(gameName);

        if (!exists) {
            gamesList.put(gameName, game);
        }

        return !exists;
    }

    public Game getGame(String gameName) {
        return gamesList.get(gameName);
    }
}
