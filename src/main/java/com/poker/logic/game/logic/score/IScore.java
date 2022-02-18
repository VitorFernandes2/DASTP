package com.poker.logic.game.logic.score;

import com.poker.model.card.ICard;

import java.util.List;

public interface IScore {
    /**
     * @return
     */
    int calculateTableScore();

    /**
     * @param playerHand
     * @return
     */
    String[] calculateWithHandScore(List<ICard> playerHand);

    /**
     * @param singleCard
     */
    void addTableCard(ICard singleCard);

    /**
     * @param multipleCards
     */
    void addTableCard(List<ICard> multipleCards);
}
