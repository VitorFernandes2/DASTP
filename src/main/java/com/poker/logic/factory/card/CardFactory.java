package com.poker.logic.factory.card;

import com.poker.logic.factory.IFactory;
import com.poker.model.card.*;
import com.poker.model.constants.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardFactory implements IFactory<List<ICard>, Object> {

    @Override
    public List<ICard> createObject(Object object) {
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
