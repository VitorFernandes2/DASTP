package com.poker.model.card;

import java.io.Serializable;

public class Card implements ICard, Serializable {

    private static final long serialVersionUID = -7925218915150511886L;

    //Card value is the numeric value of the card (1-13)
    private final Integer cardValue;

    public Card(Integer cardValue) {
        this.cardValue = cardValue;
    }

    @Override
    public Integer getCardValue() {
        return cardValue;
    }

    @Override
    public String getStringCardValue() {
        String strCardValue = "";
        switch (cardValue) {
            case 1:
                strCardValue = "A";
                break;
            case 11:
                strCardValue = "J";
                break;
            case 12:
                strCardValue = "Q";
                break;
            case 13:
                strCardValue = "K";
                break;
            default:
                strCardValue = "" + this.cardValue;
                break;
        }
        return strCardValue;
    }
}
