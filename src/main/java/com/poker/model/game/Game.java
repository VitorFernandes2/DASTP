package com.poker.model.game;

import com.poker.dto.player.Player;

import java.util.List;

public class Game {
    private String gameName;
    private List<Player> players;
    private final int minimumPlayers;
    private final int minimumAmount;

    public Game(String gameName, List<Player> players, int minimumPlayers, int minimumAmount) {
        this.gameName = gameName;
        this.players = players;
        this.minimumPlayers = minimumPlayers;
        this.minimumAmount = minimumAmount;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }
}
