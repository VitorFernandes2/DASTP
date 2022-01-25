package com.poker.logic.game.logic;

import com.poker.model.player.Player;
import com.poker.logic.game.state.IGameState;

import java.util.List;

/**
 * In this class must be all the components that can simplify the logic functions
 */
public class LogicFacade {

    private List<Player> playerList;
    private Integer bigBlind;
    private Integer smallBlind;
    private IGameState state;

    public LogicFacade(List<Player> playerList, Integer bigBlind, Integer smallBlind, IGameState state) {
        this.playerList = playerList;
        this.bigBlind = bigBlind;
        this.smallBlind = smallBlind;
        this.state = state;
    }
}
