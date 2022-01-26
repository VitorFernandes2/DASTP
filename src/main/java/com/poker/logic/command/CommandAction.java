package com.poker.logic.command;

public class CommandAction {
    private final String game;
    private final String playerName;
    private final Double amount;

    public CommandAction(String game, String playerName, Double amount) {
        this.game = game;
        this.playerName = playerName;
        this.amount = amount;
    }

    public String getGame() {
        return game;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Double getAmount() {
        return amount;
    }
}
