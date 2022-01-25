package com.poker.logic.factory.card;

import com.poker.model.card.ICard;

import java.util.List;

public interface ICardFactory {
    ICard createCard();
    List<ICard> createDeck();
}
