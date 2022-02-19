package com.poker.model.ranking;

import java.io.Serializable;

public class RankingLine implements Serializable {
    private static final long serialVersionUID = -6131760770439565948L;

    private String playerName;
    private int wins;

    public RankingLine(String playerName) {
        this.playerName = playerName;
    }

    public RankingLine(String playerName, int wins) {
        this.playerName = playerName;
        this.wins = wins;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
        RankingProvider.getInstance().registerUpdate(this);
    }
}
