package com.poker.tests;

import com.poker.logic.factory.EFactory;
import com.poker.logic.factory.FactoryProvider;
import com.poker.model.card.ICard;
import com.poker.logic.factory.card.CardFactory;

import java.util.List;

public class CardTest {
    public static void main(String[] args) {
        CardFactory cardFactory = (CardFactory) FactoryProvider.getFactory(EFactory.CARDS);
        List<ICard> deck = cardFactory.createObject(null);
        System.out.println("number of cards in the deck: " + deck.size());
        for (ICard card : deck) {
            System.out.println(card.getStringCardValue());
        }
    }
}
