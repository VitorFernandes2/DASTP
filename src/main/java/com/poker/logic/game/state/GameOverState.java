package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class GameOverState extends StateAdapter {

    private static final long serialVersionUID = -1367836270627143084L;

    public GameOverState(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public IGameState endGame() {
        return super.endGame();
    }
}
