package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class DealingState extends StateAdapter {

    public DealingState(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public IGameState bet(String playerName, double amount) {
        boolean endTurn = getGameEngine().bet(playerName, amount);
        return endTurn ? new DealerButtonState(getGameEngine()) : this;
    }

    @Override
    public IGameState check(String playerName) {
        return this;
    }

    @Override
    public IGameState fold(String playerName) {
        return this;
    }

    @Override
    public IGameState showdown() {
        return super.showdown();
    }

    @Override
    public IGameState turnCards() {
        return super.turnCards();
    }
}
