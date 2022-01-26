package com.poker.logic;

import com.poker.logic.game.Game;
import com.poker.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ApplicationData {
    private static final ApplicationData applicationData = new ApplicationData();
    private final List<Player> onlinePlayers;
    private final List<Game> gamesList;

    private ApplicationData() {
        this.onlinePlayers = new ArrayList<>();
        this.gamesList = new ArrayList<>();
    }

    public List<Player> getOnlinePlayers() {
        return onlinePlayers;
    }

    public List<Game> getGamesList() {
        return gamesList;
    }

    public static ApplicationData getInstance() {
        return applicationData;
    }
}
