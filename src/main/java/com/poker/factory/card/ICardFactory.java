package com.poker.factory.card;

import com.poker.dto.card.ICard;

import java.util.List;

public interface ICardFactory {
    ICard createCard();
    List<ICard> createDeck();
}
