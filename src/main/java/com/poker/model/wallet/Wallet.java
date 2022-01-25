package com.poker.model.wallet;

public class Wallet {
    private double amount;
    private int pokerChips;
    private int pokerGameChips;

    public Wallet(double amount, int pokerChips, int pokerGameChips) {
        this.amount = amount;
        this.pokerChips = pokerChips;
        this.pokerGameChips = pokerGameChips;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPokerChips() {
        return pokerChips;
    }

    public void setPokerChips(int pokerChips) {
        this.pokerChips = pokerChips;
    }

    public int getPokerGameChips() {
        return pokerGameChips;
    }

    public void setPokerGameChips(int pokerGameChips) {
        this.pokerGameChips = pokerGameChips;
    }

    public void addAmount(double amount) {
        this.amount += amount;
    }

    public void removeAmount(double amount) {
        this.amount -= amount;
    }

    public void addPokerChips(int pokerChips) {
        this.pokerChips += pokerChips;
    }

    public void removePokerChips(int pokerChips) {
        this.pokerChips -= pokerChips;
    }

    public void addPokerGameChips(int pokerGameChips) {
        this.pokerGameChips += pokerGameChips;
    }

    public void removePokerGameChips(int pokerGameChips) {
        this.pokerGameChips -= pokerGameChips;
    }

    public void resetPokerGameChips() {
        this.pokerGameChips = 0;
    }
}