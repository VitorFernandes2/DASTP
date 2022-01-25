package com.poker.factory.card;

import com.poker.constants.Constants;
import com.poker.model.card.*;
import com.poker.utils.IntegerUtils;

import java.util.*;

public class CardFactory implements ICardFactory {
    @Override
    public ICard createCard() {
        Integer value = IntegerUtils.randomizeInteger(
                Constants.MINIMUM_NUMBER_CARD,
                Constants.MAXIMUM_NUMBER_CARD
        );
        Integer typeOfCard = IntegerUtils.randomizeInteger(1, 4);

        if (value == null || typeOfCard == null) {
            return null;
        }

        switch (typeOfCard) {
            case 1:
                return new DiamondsCard(value);
            case 2:
                return new HeartsCard(value);
            case 3:
                return new ClubsCard(value);
            case 4:
                return new SpadesCard(value);
            default:
                return null;
        }
    }

    @Override
    public List<ICard> createDeck() {
        List<ICard> deck = new ArrayList<>();
        for (int i = Constants.MINIMUM_NUMBER_CARD; i <= Constants.MAXIMUM_NUMBER_CARD; i++) {
            deck.add(new SpadesCard(i));
            deck.add(new HeartsCard(i));
            deck.add(new DiamondsCard(i));
            deck.add(new ClubsCard(i));
        }
        Collections.shuffle(deck);
        return deck;
    }
}
