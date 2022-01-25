package com.poker.service.game.state;

public class DealingState extends StateAdapter {
    @Override
    public IGameState bet() {
        return this;
    }

    @Override
    public IGameState check() {
        return this;
    }

    @Override
    public IGameState fold() {
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
