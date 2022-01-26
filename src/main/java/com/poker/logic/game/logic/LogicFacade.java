package com.poker.logic.game.logic;

import com.poker.model.player.Player;
import com.poker.logic.game.state.IGameState;

import java.util.List;
import java.util.Map;

/**
 * In this class must be all the components that can simplify the logic functions
 */
public class LogicFacade {

    private final Map<String, Player> playerList;
    private final IGameState state;

    public LogicFacade(Map<String, Player> playerList, IGameState state) {
        this.playerList = playerList;
        this.state = state;
    }


}
