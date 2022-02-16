package com.poker.model.card;

public class DiamondsCard extends Card {
    private static final long serialVersionUID = 9171142132765478503L;

    public DiamondsCard(Integer cardValue) {
        super(cardValue);
    }

    @Override
    public String getStringCardValue() {
        return super.getStringCardValue() + " Diamonds";
    }
}
