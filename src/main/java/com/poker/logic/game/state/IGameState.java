package com.poker.logic.game.state;

public interface IGameState {
    IGameState startGame(String playerName, String creatorName);

    IGameState startTurn();

    IGameState bet(String playerName, double amount);

    IGameState check(String playerName);

    IGameState fold(String playerName);

    IGameState turnCards();

    IGameState showdown();

    IGameState endGame();
}
