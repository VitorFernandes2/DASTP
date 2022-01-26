package com.poker.logic.game.logic;

import com.poker.logic.game.Game;
import com.poker.model.player.Player;
import com.poker.logic.game.state.IGameState;

import java.util.Map;
import java.util.Objects;

/**
 * In this class must be all the components that can simplify the logic functions
 */
public class LogicFacade {

    private final Map<String, Player> players;
    private IGameState state;

    public LogicFacade(Map<String, Player> players, IGameState state) {
        this.players = players;
        this.state = state;
    }

    public boolean userInGame(String username) {
        return !Objects.isNull(this.players.get(username));
    }

    public boolean addUserToGame(Player player) {
        if (!this.userInGame(player.getName())) {
            this.players.put(player.getName(),player);
            return true;
        }
        return false;
    }

    public void startGame(Game game) {
        this.state = this.state.startGame(game);
    }
}
