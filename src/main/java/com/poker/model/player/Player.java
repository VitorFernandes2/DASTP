package com.poker.model.player;

import com.poker.model.wallet.Wallet;
import com.poker.service.game.Game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private List<Game> currentGames;
    private Wallet wallet;

    public Player(String name) {
        this.name = name;
        this.currentGames = new ArrayList<>();
        this.wallet = new Wallet(0, 0, 0);
    }

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
