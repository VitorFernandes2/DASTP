package com.poker.utils;

import com.poker.logic.game.logic.score.Score;
import com.poker.model.card.ICard;
import com.poker.model.filter.Log;
import com.poker.model.player.Player;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public class ScoreUtils {
    private static final Log LOG = Log.getInstance();

    /**
     * Function to calculate the winner of this game round and give him all the pot money.
     *
     * @param pot            {@link Integer} - Pot money in current game.
     * @param tableCards     {@link List} - Cards shown on the table.
     * @param queuePlayOrder {@link Queue} - Players who are currently playing until the end.
     * @param players        {@link Map} - Players map.
     */
    public static void scoring(Integer pot, List<ICard> tableCards, Queue<String> queuePlayOrder, Map<String, Player> players) {
        Score score = new Score(tableCards);
        int highestScore = -999999;
        String[] scoreAux;
        String winner = "", winnerDraw = "", winningPlay = "", winningPlayDraw = "";

        // Decide the winner
        for (String playerName : queuePlayOrder) {
            scoreAux = score.calculateWithHandScore(List.of(players.get(playerName).getGameCards()));
            int highAux = Integer.parseInt(scoreAux[0]);
            System.out.println("The player " + playerName + " has " + scoreAux[1] + "\nCards: " + CardsUtils.cardsToString(Stream.concat(tableCards.stream(), Stream.of(players.get(playerName).getGameCards())).toArray(ICard[]::new)));
            if (highestScore == highAux) {          // Checks if the winner end up with a draw with another player.
                ICard[] playerAux = players.get(playerName).getGameCards();
                ICard[] winnerAux = players.get(winner).getGameCards();

                int playerMax = playerAux[0].getCardValue() >= playerAux[1].getCardValue() ? playerAux[0].getCardValue() : playerAux[1].getCardValue();
                int playerMin = playerAux[0].getCardValue() < playerAux[1].getCardValue() ? playerAux[0].getCardValue() : playerAux[1].getCardValue();
                int winnerMax = winnerAux[0].getCardValue() >= winnerAux[1].getCardValue() ? winnerAux[0].getCardValue() : winnerAux[1].getCardValue();
                int winnerMin = winnerAux[0].getCardValue() < winnerAux[1].getCardValue() ? winnerAux[0].getCardValue() : winnerAux[1].getCardValue();

                if (playerMax > winnerMax) {
                    winner = playerName;
                } else if (playerMin > winnerMin) {
                    winner = playerName;
                } else {
                    winnerDraw = playerName;
                    winningPlayDraw = scoreAux[1];
                }
            } else if (highestScore < highAux) {    // Saves the winning details.
                highestScore = highAux;
                winner = playerName;
                winningPlay = scoreAux[1];
                winnerDraw = "";
            }
        }

        // If both the players have the same cards and are winners then the pot needs to be divided between the two of them.
        if (!winnerDraw.isEmpty()) {
            // Giving the pot money to the winners
            double halfPot = (double) pot / 2;
            players.get(winner).getWallet().addPokerGameChips((int) Math.ceil(halfPot));        // Because it was the first highest scoring player to be registered, it is the nearest to the dealer, and it should receive the possible odd game chip.
            players.get(winnerDraw).getWallet().addPokerGameChips((int) Math.floor(halfPot));

            System.out.println("\n[Game] " + winner + " Cards: " + CardsUtils.cardsToString(Stream.concat(tableCards.stream(), Stream.of(players.get(winner).getGameCards())).toArray(ICard[]::new)));
            System.out.println("\n[Game] " + winnerDraw + " Cards: " + CardsUtils.cardsToString(Stream.concat(tableCards.stream(), Stream.of(players.get(winnerDraw).getGameCards())).toArray(ICard[]::new)));
            LOG.addAndShowLog("[Game] Winners of this round are:\n" + winner + " with an " + winningPlay + "\n" + winnerDraw + " with an " + winningPlayDraw);
            LOG.addAndShowLog("[Game] Player " + winner + " win " + (int) Math.ceil(halfPot) + " PCJs, now has " + players.get(winner).getWallet().getPokerGameChips() + " PCJs.");
            LOG.addAndShowLog("[Game] Player " + winnerDraw + " win " + (int) Math.floor(halfPot) + " PCJs, now has " + players.get(winnerDraw).getWallet().getPokerGameChips() + " PCJs.");
        } else {
            // Giving the pot money to the winner
            players.get(winner).getWallet().addPokerGameChips(pot);

            System.out.println("\n[Game] Winning Cards: " + CardsUtils.cardsToString(Stream.concat(tableCards.stream(), Stream.of(players.get(winner).getGameCards())).toArray(ICard[]::new)));
            LOG.addAndShowLog("[Game] Winner of this round is " + winner + " with an " + winningPlay + ".");
            LOG.addAndShowLog("[Game] Player " + winner + " win " + pot + " PCJs, now has " + players.get(winner).getWallet().getPokerGameChips() + " PCJs.");
        }
    }
}
