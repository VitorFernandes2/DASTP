package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class TheRiverState extends StateAdapter {
    private static final long serialVersionUID = 2621216973336804222L;

    public TheRiverState(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public IGameState bet(String playerName, Integer amount) {
        boolean nextState = getGameEngine().bet(playerName, amount);
        if (nextState) {
            return isGameOver();
        }
        return this;
    }

    @Override
    public IGameState check(String playerName) {
        return super.check(playerName);
    }

    @Override
    public IGameState fold(String playerName) {
        boolean nextState = getGameEngine().fold(playerName);
        if (nextState) {
            return isGameOver();
        }
        return this;
    }
}
