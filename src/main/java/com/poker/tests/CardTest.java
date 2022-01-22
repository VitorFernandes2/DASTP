package com.poker.tests;

import com.poker.dto.card.ICard;
import com.poker.factory.card.CardFactory;

public class CardTest {
    public static void main(String[] args) {
        var cardFactory = new CardFactory();
        var deck = cardFactory.createDeck();
        System.out.println("number of cards in the deck: " + deck.size());
        for (ICard card : deck) {
            System.out.println(card.getStringCardValue());
        }
    }
}
