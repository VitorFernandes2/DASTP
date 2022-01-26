package com.poker.logic.game.state;

import com.poker.logic.game.Game;

public class StateAdapter implements IGameState {
    @Override
    public IGameState startGame(Game game) {
        return this;
    }

    @Override
    public IGameState selectDealer() {
        return null;
    }

    @Override
    public IGameState beginTurn() {
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

    @Override
    public IGameState newTurn() {
        return this;
    }
}
