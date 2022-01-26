package com.poker.logic.game.state;

import com.poker.logic.game.Game;

public class BuyInState extends StateAdapter {
    @Override
    public IGameState startGame(Game game) {
        return new DealerButtonState();
    }
}
