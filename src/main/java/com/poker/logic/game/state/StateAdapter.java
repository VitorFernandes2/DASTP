package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class StateAdapter implements IGameState {
    private final GameEngine gameEngine;

    public StateAdapter(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public IGameState startGame(String playerName, String creatorName) {
        return this;
    }

    @Override
    public IGameState startTurn() {
        return this;
    }

    @Override
    public IGameState bet(String playerName, double amount) {
        return this;
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

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}
