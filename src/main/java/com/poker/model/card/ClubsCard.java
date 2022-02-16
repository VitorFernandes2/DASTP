package com.poker.model.card;

public class ClubsCard extends Card {
    private static final long serialVersionUID = 5022738293431520829L;

    public ClubsCard(Integer cardValue) {
        super(cardValue);
    }

    @Override
    public String getStringCardValue() {
        return super.getStringCardValue() + " Clubs";
    }
}
