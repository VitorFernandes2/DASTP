package com.poker.model.game.state;

public class StateAdapter implements IGameStates {
    @Override
    public IGameStates buyCoins() {
        return this;
    }

    @Override
    public IGameStates selectDealer() {
        return null;
    }

    @Override
    public IGameStates beginTurn() {
        return this;
    }

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
    public IGameStates turnCards() {
        return this;
    }

    @Override
    public IGameStates showdown() {
        return this;
    }

    @Override
    public IGameStates endGame() {
        return this;
    }

    @Override
    public IGameStates newTurn() {
        return this;
    }
}
