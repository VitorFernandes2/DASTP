package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

import java.io.Serializable;

public class StateAdapter implements IGameState, Serializable {
    private static final long serialVersionUID = 455139798414883697L;
    private final GameEngine gameEngine;

    public StateAdapter(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public IGameState startGame(String playerName, String creatorName, int minimumAmount) {
        return this;
    }

    @Override
    public IGameState bet(String playerName, Integer amount) {
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

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    protected IGameState isGameOver() {
        getGameEngine().triggerShowdown();
        if (getGameEngine().isGameOver()) {
            return null; // TODO: deal with the end game
        } else {
            return new SetupState(getGameEngine());
        }
    }
}
