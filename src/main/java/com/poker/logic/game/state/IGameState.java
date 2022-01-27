package com.poker.logic.game.state;

public interface IGameState {
    IGameState startGame();

    IGameState startTurn();

    IGameState bet();

    IGameState check();

    IGameState fold();

    IGameState turnCards();

    IGameState showdown();

    IGameState endGame();
}
