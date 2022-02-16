package com.poker.model.card;

public class SpadesCard extends Card {
    private static final long serialVersionUID = 2510492922367599419L;

    public SpadesCard(Integer cardValue) {
        super(cardValue);
    }

    @Override
    public String getStringCardValue() {
        return super.getStringCardValue() + " Spades";
    }
}
