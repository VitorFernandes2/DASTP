package com.poker.utils;

import com.poker.model.card.ICard;
import com.poker.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardsUtils {

    public static ICard withdrawFromTop(List<ICard> deck) {
        if (deck != null && deck.size() > 0) {
            return deck.remove(0);
        }
        return null;
    }

    public static List<ICard> withdrawMoreThanOne(int numCards, List<ICard> deck) {
        if (numCards > 1) {
            List<ICard> list = new ArrayList<>();
            for (int i = 0; i < numCards; i++) {
                ICard card = withdrawFromTop(deck);

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

    public static void distributeCardsPerPlayer(Map<String, Player> players, List<ICard> deck) {
        if (players != null && players.size() > 0 && deck != null && deck.size() > 0) {
            for (Player player : players.values()) {
                List<ICard> cardsFromTop = withdrawMoreThanOne(2, deck);
                if (cardsFromTop == null) {
                    return;
                }

                ICard[] cards = new ICard[]{cardsFromTop.get(0), cardsFromTop.get(1)};
                player.setGameCards(cards);
            }
        }
    }

    public static String cardsToString(ICard ... cards) {
        StringBuilder str = new StringBuilder("[ ");
        for (ICard c : cards) {
            str.append(c.getStringCardValue()).append(" | ");
        }
        if(cards.length > 0)
            str.setLength(str.length() - 2); // remove the last 2 characters
        str.append("]");
        return str.toString();
    }

    public static String cardsPerPlayerToString(Map<String, Player> players) {
        StringBuilder str = new StringBuilder();
        str.append("## Player's cards:");
        players.forEach((playerName, player) -> {
            str.append("\n\tName: ").append(playerName).append(" | Cards: ").append(cardsToString(player.getGameCards()));
        });
        return str.toString();
    }
}
