package com.poker.dto.wallet;

public class Wallet {
    private Integer amount = 0;

    public Wallet(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount += amount;
    }
}
