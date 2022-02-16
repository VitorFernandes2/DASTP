package com.poker.model.card;

public class HeartsCard extends Card {
    private static final long serialVersionUID = -3229657491301266194L;

    public HeartsCard(Integer cardValue) {
        super(cardValue);
    }

    @Override
    public String getStringCardValue() {
        return super.getStringCardValue() + " Hearts";
    }
}
