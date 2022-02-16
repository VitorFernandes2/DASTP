package com.poker.logic.game.state;

public interface IGameState {
    IGameState startGame(String playerName, String creatorName, int minimumAmount);

    IGameState startRound();

    IGameState bet(String playerName, Integer amount);

    IGameState check(String playerName);

    IGameState fold(String playerName);

    IGameState turnCards();

    IGameState showdown();

    IGameState endGame();
}
