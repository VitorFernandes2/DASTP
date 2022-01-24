package com.poker.model.game.state;

public class DealingState extends StateAdapter {
    @Override
    public IGameStates bet() {
        return this;
    }

    @Override
    public IGameStates check() {
        return this;
    }

    @Override
    public IGameStates fold() {
        return this;
    }

    @Override
    public IGameStates showdown() {
        return super.showdown();
    }

    @Override
    public IGameStates turnCards() {
        return super.turnCards();
    }
}
