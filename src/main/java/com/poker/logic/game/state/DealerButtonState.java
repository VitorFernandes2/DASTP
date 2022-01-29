package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class DealerButtonState extends StateAdapter {

    public DealerButtonState(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public IGameState startRound() {
        try {
            getGameEngine().startRound();
            return new DealingState(getGameEngine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
