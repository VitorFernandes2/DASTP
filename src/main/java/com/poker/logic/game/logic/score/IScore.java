package com.poker.logic.game.logic.score;

import com.poker.model.card.ICard;

import java.util.List;

public interface IScore {
    int calculateTableScore();
    String[] calculateWithHandScore(List<ICard> playerHand);
    void addTableCard(ICard singleCard);
}
