package com.poker.logic;

import com.poker.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ApplicationData {
    private static final ApplicationData applicationData = new ApplicationData();
    private final List<Player> onlinePlayers;

    private ApplicationData() {
        this.onlinePlayers = new ArrayList<>();
    }

    public List<Player> getOnlinePlayers() {
        return onlinePlayers;
    }

    public static ApplicationData getInstance() {
        return applicationData;
    }
}
