package com.poker.logic.factory.game;

import com.poker.logic.game.ETypeOfGame;
import com.poker.model.constants.Constants;
import com.poker.model.player.Player;
import com.poker.model.ranking.RankingLine;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameCreationData {
    private final String gameName;
    private final Integer friendlyGameMinimumPlayers;
    private final int minimumAmount;
    private final ETypeOfGame typeOfGame;
    private final Map<String, Player> players;
    private final Player player;
    private int fee;
    private int bigBlind;
    private int increment;
    private Map<String, RankingLine> rankings;

    public GameCreationData(String gameName, Integer friendlyGameMinimumPlayers, int minimumAmount, ETypeOfGame typeOfGame, Player player, Map<String, RankingLine> rankings) {
        this.gameName = gameName;
        this.friendlyGameMinimumPlayers = friendlyGameMinimumPlayers;
        this.minimumAmount = minimumAmount;
        this.typeOfGame = typeOfGame;
        switch (typeOfGame) {
            case COMPETITIVE:
                this.fee = Constants.COMPETITIVE_DEFAULT_FEE;
                break;
            case FRIENDLY:
                this.fee = Constants.FRIENDLY_DEFAULT_FEE;
                break;
        }
        this.bigBlind = Constants.DEFAULT_BIG_BLIND;
        this.increment = Constants.DEFAULT_INCREMENT;
        this.players = new LinkedHashMap<>();
        this.player = player;
        this.rankings = rankings;
    }

    public String getGameName() {
        return gameName;
    }

    public Integer getFriendlyGameMinimumPlayers() {
        return friendlyGameMinimumPlayers;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public ETypeOfGame getTypeOfGame() {
        return typeOfGame;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public Player getPlayer() {
        return player;
    }

    public int getFee() {
        return fee;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public int getIncrement() {
        return increment;
    }

    public Map<String, RankingLine> getRankings() {
        return rankings;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
