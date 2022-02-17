package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class SetupState extends StateAdapter {
    private static final long serialVersionUID = 8676597357813307795L;

    public SetupState(GameEngine gameEngine) {
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
        return super.check(playerName);
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
        boolean isRoundOver = getGameEngine().triggerTheFlop();
        if (isRoundOver) {
            return isGameOver();
        }
        return new TheFlopState(getGameEngine());
    }
}
