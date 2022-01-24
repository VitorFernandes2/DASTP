package com.poker.dto.player;

import com.poker.dto.wallet.Wallet;
import com.poker.model.game.Game;

import java.util.List;

public class Player {
    private final String name;
    private List<Game> currentGames;
    private Wallet wallet;

    public Player(String name, List<Game> currentGames, Wallet wallet) {
        this.name = name;
        this.currentGames = currentGames;
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public List<Game> getCurrentGames() {
        return currentGames;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
