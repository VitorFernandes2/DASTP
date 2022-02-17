package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class TheTurnState extends StateAdapter {
    private static final long serialVersionUID = -8269703045202834939L;

    public TheTurnState(GameEngine gameEngine) {
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
        // TODO
        return super.fold(playerName);
    }

    private IGameState isRoundOver() {
        boolean isRoundOver = getGameEngine().triggerNextCard();
        if (isRoundOver) {
            return isGameOver();
        }
        return new TheRiverState(getGameEngine());
    }
}
