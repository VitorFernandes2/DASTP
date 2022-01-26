package com.poker.logic;

import com.poker.logic.game.Game;
import com.poker.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplicationData {
    private static final ApplicationData applicationData = new ApplicationData();
    private final Map<String, Player> onlinePlayers;
    private final Map<String, Game> gamesList;

    private ApplicationData() {
        this.onlinePlayers = new HashMap<>();
        this.gamesList = new HashMap<>();
    }

    public static ApplicationData getInstance() {
        return applicationData;
    }

    public Map<String, Player> getOnlinePlayers() {
        return onlinePlayers;
    }

    public Map<String, Game> getGamesList() {
        return gamesList;
    }
}
