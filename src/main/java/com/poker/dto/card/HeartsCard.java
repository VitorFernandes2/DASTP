package com.poker.dto.card;

public class HeartsCard extends Card {
    public HeartsCard(Integer cardValue) {
        super(cardValue);
    }

    @Override
    public String getStringCardValue() {
        return super.getStringCardValue() + " Hearts";
    }
}
