package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class TheFlopState extends StateAdapter {
    private static final long serialVersionUID = 1960110780643280564L;

    public TheFlopState(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public IGameState bet(String playerName, Integer amount) {
        boolean nextState = getGameEngine().bet(playerName, amount);
        if (nextState) {
            return isRoundOver();
        }
        return this;
    }

    @Override
    public IGameState check(String playerName) {
        boolean nextState = getGameEngine().check(playerName);
        if (nextState) {
            return isRoundOver();
        }
        return this;
    }

    @Override
    public IGameState fold(String playerName) {
        boolean nextState = getGameEngine().fold(playerName);
        if (nextState) {
            return isRoundOver();
        }
        return this;
    }

    private IGameState isRoundOver() {
        boolean isRoundOver = getGameEngine().triggerNextCard();
        if (isRoundOver) {
            return isGameOver();
        }
        return new TheTurnState(getGameEngine());
    }
}
