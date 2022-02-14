package com.poker.logic.factory;

import com.poker.logic.factory.card.CardFactory;
import com.poker.logic.factory.game.GameFactory;

public class FactoryProvider {
    public static IFactory getFactory(EFactory factoryType) {
        switch (factoryType) {
            case CARDS:
                return new CardFactory();
            case GAMES:
                return new GameFactory();
        }
        return null;
    }
}
