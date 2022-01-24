package com.poker.model.game.state;

public interface IGameStates {
    IGameStates buyCoins();
    IGameStates selectDealer();
    IGameStates beginTurn();
    IGameStates bet();
    IGameStates check();
    IGameStates fold();
    IGameStates turnCards();
    IGameStates showdown();
    IGameStates endGame();
    IGameStates newTurn();
}
