package com.poker.model.player;

import com.poker.model.wallet.Wallet;

public class Player {
    private final String name;
    private final Wallet wallet;

    public Player(String name) {
        this.name = name;
        this.wallet = new Wallet(0, 0, 0);
    }

    public Player(String name, Wallet wallet) {
        this.name = name;
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setAmount(double amount) {
        this.getWallet().setAmount(amount);
    }

    public void setPokerChips(int pokerChips) {
        this.getWallet().setPokerChips(pokerChips);
    }

    public void setPokerGameChips(int pokerGameChips) {
        this.getWallet().setPokerGameChips(pokerGameChips);
    }
}
