package com.poker.logic.game.state;

public class DealerButtonState extends StateAdapter {
    @Override
    public IGameState startTurn() {
        return new DealingState();
    }
}
