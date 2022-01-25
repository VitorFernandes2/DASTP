package com.poker.logic.game;

import com.poker.model.player.Player;
import com.poker.logic.game.logic.LogicFacade;
import com.poker.logic.game.state.IGameState;

import java.util.List;

public class Game {
    private String gameName;
    private List<Player> players;
    private final int minimumPlayers;
    private final int minimumAmount;
    private final LogicFacade logicFacade;

    public Game(String gameName, List<Player> players, int minimumPlayers, int minimumAmount, int bigBlind, int smallBlind, IGameState state) {
        this.gameName = gameName;
        this.players = players;
        this.minimumPlayers = minimumPlayers;
        this.minimumAmount = minimumAmount;
        this.logicFacade = new LogicFacade(players, bigBlind, smallBlind, state);
    }
}
