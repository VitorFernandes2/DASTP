package com.poker.logic.game.state;

public class StateAdapter implements IGameState {
    @Override
    public IGameState startGame() {
        return this;
    }

    @Override
    public IGameState startTurn() {
        return this;
    }

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
    public IGameState turnCards() {
        return this;
    }

    @Override
    public IGameState showdown() {
        return this;
    }

    @Override
    public IGameState endGame() {
        return this;
    }
}
