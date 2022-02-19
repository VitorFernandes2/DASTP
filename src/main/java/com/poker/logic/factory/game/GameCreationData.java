package com.poker.logic.factory.game;

import com.poker.logic.game.ETypeOfGame;
import com.poker.model.constants.Constants;
import com.poker.model.player.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameCreationData {
    private final String gameName;
    private final Integer friendlyGameMinimumPlayers;
    private final int minimumAmount;
    private final ETypeOfGame typeOfGame;
    private final Map<String, Player> players;
    private final Player player;
    private final double fee;
    private int bigBlind;
    private int increment;

    public GameCreationData(String gameName, Integer friendlyGameMinimumPlayers, int minimumAmount, ETypeOfGame typeOfGame, Player player, double fee, int bigBlind, int increment) {
        this.gameName = gameName;
        this.friendlyGameMinimumPlayers = friendlyGameMinimumPlayers;
        this.minimumAmount = minimumAmount;
        this.typeOfGame = typeOfGame;
        this.fee = fee;
        this.bigBlind = bigBlind;
        this.players = new LinkedHashMap<>();
        this.player = player;
        this.increment = increment;
    }

    public GameCreationData(String gameName, Integer friendlyGameMinimumPlayers, int minimumAmount, ETypeOfGame typeOfGame, Player player) {
        this.gameName = gameName;
        this.friendlyGameMinimumPlayers = friendlyGameMinimumPlayers;
        this.minimumAmount = minimumAmount;
        this.typeOfGame = typeOfGame;
        this.fee = Constants.DEFAULT_FEE;
        this.bigBlind = Constants.DEFAULT_BIG_BLIND;
        this.increment = Constants.DEFAULT_INCREMENT;
        this.players = new LinkedHashMap<>();
        this.player = player;
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

    public double getFee() {
        return fee;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public int getIncrement() {
        return increment;
    }
}
