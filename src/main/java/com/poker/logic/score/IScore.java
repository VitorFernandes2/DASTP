package com.poker.logic.score;

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
    int calculateWithHandScore(List<ICard> playerHand);

    /**
     * @param singleCard
     */
    void addTableCard(ICard singleCard);

    /**
     * @param multipleCards
     */
    void addTableCard(List<ICard> multipleCards);
}
