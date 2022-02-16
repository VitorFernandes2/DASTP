package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

public class BuyInState extends StateAdapter {
    private static final long serialVersionUID = -4327170690261216747L;

    public BuyInState(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public IGameState startGame(String playerName, String creatorName, int minimumAmount) {
        if (getGameEngine().startGame(playerName, creatorName, minimumAmount)) {
            return new DealerButtonState(getGameEngine());
        } else {
            System.out.println("Game could not be started");
            return this;
        }
    }
}
