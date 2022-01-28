package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class GameOverState extends StateAdapter {

    public GameOverState(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public IGameState endGame() {
        return super.endGame();
    }
}
