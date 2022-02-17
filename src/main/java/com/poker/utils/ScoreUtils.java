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
     * @param pot {@link Integer} - Pot money in current game.
     * @param tableCards {@link List} - Cards shown on the table.
     * @param queuePlayOrder {@link Queue} - Players who are currently playing until the end.
     * @param players {@link Map} - Players map.
     */
    public static void scoring(Integer pot, List<ICard> tableCards, Queue<String> queuePlayOrder, Map<String, Player> players) {
        Score score = new Score(tableCards);
        int highestScore = -999999, scoreAux;
        String winner = "", winningPlay = "";

        // Decide the winner
        for (String playerName : queuePlayOrder) {
            scoreAux = Integer.getInteger(score.calculateWithHandScore(List.of(players.get(playerName).getGameCards()))[0]);
            if (highestScore < scoreAux) {
                highestScore = scoreAux;
                winner = playerName;
                winningPlay = score.calculateWithHandScore(List.of(players.get(playerName).getGameCards()))[1];
            }
        }

        // Giving the pot money to the winner
        players.get(winner).getWallet().addPokerGameChips(pot);

        System.out.println("\n[Game] Winning Cards: " + CardsUtils.cardsToString(Stream.concat(tableCards.stream(), Stream.of(players.get(winner).getGameCards())).toArray(ICard[]::new)));
        LOG.addAndShowLog("[Game] Winner of this round is " + winner + " with an " + winningPlay + ".");
        LOG.addAndShowLog("[Game] Player " + winner + " now has " + players.get(winner).getWallet().getPokerGameChips() + " PCJs.");
    }

}
