package com.poker.logic.factory.game;

import com.poker.logic.game.ETypeOfGame;
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

    public GameCreationData(String gameName, Integer friendlyGameMinimumPlayers, int minimumAmount, ETypeOfGame typeOfGame, Player player) {
        this.gameName = gameName;
        this.friendlyGameMinimumPlayers = friendlyGameMinimumPlayers;
        this.minimumAmount = minimumAmount;
        this.typeOfGame = typeOfGame;
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
}
