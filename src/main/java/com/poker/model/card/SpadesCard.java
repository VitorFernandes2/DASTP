package com.poker.model.card;

public class SpadesCard extends Card {
    public SpadesCard(Integer cardValue) {
        super(cardValue);
    }

    @Override
    public String getStringCardValue() {
        return super.getStringCardValue() + " Spades";
    }
}
