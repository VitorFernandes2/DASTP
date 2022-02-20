package com.poker.logic.factory.game;

import com.poker.logic.factory.IFactory;
import com.poker.logic.game.Game;

public class GameFactory implements IFactory<Game, GameCreationData> {
    @Override
    public Game createObject(GameCreationData object) {
        return new Game.Builder(object.getGameName())
                .setMinimumPlayers(object.getFriendlyGameMinimumPlayers())
                .setMinimumAmount(object.getMinimumAmount())
                .setPlayers(object.getPlayers())
                .setTypeOfGame(object.getTypeOfGame())
                .setCreator(object.getPlayer())
                .setBigBlind(object.getBigBlind())
                .setConvertionTax(object.getFee())
                .setIncrement(object.getIncrement())
                .build();
    }
}
