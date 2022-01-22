package com.poker.dto.card;

public class ClubsCard extends Card {
    public ClubsCard(Integer cardValue) {
        super(cardValue);
    }

    @Override
    public String getStringCardValue() {
        return super.getStringCardValue() + " Clubs";
    }
}
