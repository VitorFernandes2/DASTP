package com.poker.logic.game.state;

import com.poker.logic.game.Game;

public interface IGameState {
    IGameState startGame(Game game);
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
