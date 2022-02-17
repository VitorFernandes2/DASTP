package com.poker.logic.game.state;

public interface IGameState {
    IGameState startGame(String playerName, String creatorName, int minimumAmount);

    IGameState bet(String playerName, Integer amount);

    IGameState check(String playerName);

    IGameState fold(String playerName);
}
