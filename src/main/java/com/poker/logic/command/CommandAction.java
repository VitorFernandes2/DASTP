package com.poker.logic.command;

public class CommandAction {
    private final String game;
    private final String playerName;
    private final Integer amount;

    public CommandAction(String game, String playerName, Integer amount) {
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

    public Integer getAmount() {
        return amount;
    }
}
