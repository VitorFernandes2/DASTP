package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;

@Deprecated
public class DealingState extends StateAdapter {

    private static final long serialVersionUID = -3438780049176281460L;

    public DealingState(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public IGameState bet(String playerName, Integer amount) {
        boolean turnCard = getGameEngine().bet(playerName, amount);
        // This will be true in the end of each round
        if(turnCard) {
//            if(!getGameEngine().turnCard()) {
//                return this;
//            } else {
//
//            }
        }
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
}
