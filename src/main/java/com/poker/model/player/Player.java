package com.poker.model.player;

import com.poker.model.card.ICard;
import com.poker.model.wallet.Wallet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private static final long serialVersionUID = 1503947119842473197L;
    private final String name;
    private final Wallet wallet;
    private final List<String> friends;
    private final List<String> playersBlocked;
    private ICard[] gameCards;

    public Player(String name) {
        this.name = name;
        this.wallet = new Wallet(0, 0, 0);
        this.friends = new ArrayList<>();
        this.playersBlocked = new ArrayList<>();
        this.gameCards = null;
    }

    public Player(String name, Wallet wallet) {
        this.name = name;
        this.wallet = wallet;
        this.friends = new ArrayList<>();
        this.playersBlocked = new ArrayList<>();
        this.gameCards = null;
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

    public boolean equals(Player obj) {
        return obj.getName().equals(this.getName());
    }

    public List<String> getFriends() {
        return friends;
    }

    public void addFriend(String name) {
        friends.add(name);
    }

    public void removeFriend(String name) {
        friends.remove(name);
    }

    public List<String> getPlayersBlocked() {
        return playersBlocked;
    }

    public void blockPlayer(String name) {
        playersBlocked.add(name);
    }

    public void removeBlockedPlayer(String name) {
        playersBlocked.remove(name);
    }

    public ICard[] getGameCards() {
        return gameCards;
    }

    public void setGameCards(ICard[] gameCards) {
        this.gameCards = gameCards;
    }
}
