package com.poker.tests;

import com.poker.logic.factory.EFactory;
import com.poker.logic.factory.FactoryProvider;
import com.poker.logic.factory.card.CardFactory;
import com.poker.logic.game.logic.score.Score;
import com.poker.model.card.ICard;

import java.util.ArrayList;
import java.util.List;

public class ScoreTest {
    private static final Score SCORE = new Score(new ArrayList<>());

    public static void main(String[] args) {
        CardFactory cardFactory = (CardFactory) FactoryProvider.getFactory(EFactory.CARDS);
        List<ICard> deck = cardFactory.createObject(null);
        List<ICard> hand = new ArrayList<>();
        System.out.println("number of cards in the deck: " + deck.size());
        for (ICard card : deck) {
            System.out.println(card.getStringCardValue());
        }
        System.out.println("\n\n\n");
        SCORE.addTableCard(deck.get(0));
        SCORE.addTableCard(deck.get(1));
        SCORE.addTableCard(deck.get(2));
        SCORE.addTableCard(deck.get(3));
        SCORE.addTableCard(deck.get(4));

        System.out.println("Table total score: " + SCORE.calculateTableScore());

        System.out.println("\n\n\n");
        hand.add(deck.get(5));
        hand.add(deck.get(6));

        System.out.println("Table plus hand total score: " + SCORE.calculateWithHandScore(hand));
    }
}
