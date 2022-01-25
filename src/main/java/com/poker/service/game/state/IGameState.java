package com.poker.service.game.state;

public interface IGameState {
    IGameState buyCoins();
    IGameState selectDealer();
    IGameState beginTurn();
    IGameState bet();
    IGameState check();
    IGameState fold();
    IGameState turnCards();
    IGameState showdown();
    IGameState endGame();
    IGameState newTurn();
}
