package com.poker.dto.card;

public class DiamondsCard extends Card {
    public DiamondsCard(Integer cardValue) {
        super(cardValue);
    }

    @Override
    public String getStringCardValue() {
        return super.getStringCardValue() + " Diamonds";
    }
}
