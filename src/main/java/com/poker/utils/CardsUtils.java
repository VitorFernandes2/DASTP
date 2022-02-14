package com.poker.utils;

import com.poker.model.card.Card;
import com.poker.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CardsUtils {

    public static Card withdrawFromTop(List<Card> deck) {
        if (deck != null && deck.size() > 0) {
            return deck.remove(0);
        }
        return null;
    }

    public static List<Card> withdrawMoreThanOne(int numCards, List<Card> deck) {
        if (numCards > 1) {
            List<Card> list = new ArrayList<>();
            for (int i = 0; i < numCards; i++) {
                Card card = withdrawFromTop(deck);

                if (card == null) {
                    if (list.size() > 0) {
                        deck.addAll(list);
                    }
                    return null;
                }

                list.add(card);
            }
            return list;
        }
        return null;
    }

    public static Card[] distributeCardsPerPlayer(List<Player> players, List<Card> deck) {
        if (players != null && players.size() > 0 && deck != null && deck.size() > 0) {
            for (Player player : players) {
                List<Card> cardsFromTop = withdrawMoreThanOne(2, deck);
                if (cardsFromTop == null) {
                    return null;
                }

                Card[] cards = new Card[]{cardsFromTop.get(0), cardsFromTop.get(1)};
                player.setGameCards(cards);
                return cards;
            }
        }
        return null;
    }
}
