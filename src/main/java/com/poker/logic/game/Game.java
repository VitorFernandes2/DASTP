package com.poker.logic.game;

import com.poker.logic.game.logic.LogicFacade;
import com.poker.logic.game.state.BuyInState;
import com.poker.logic.game.state.IGameState;
import com.poker.model.player.Player;

import java.util.Map;
import java.util.Objects;

public class Game {
    private final String gameName;
    private final Player creator;
    private final int minimumPlayers;
    private final int minimumAmount;
    private final LogicFacade logicFacade;

    private Game(String gameName, Map<String, Player> players, Player creator, int minimumPlayers, int minimumAmount, IGameState state) {
        this.gameName = gameName;
        this.creator = creator;
        this.minimumPlayers = minimumPlayers;
        this.minimumAmount = minimumAmount;
        this.logicFacade = new LogicFacade(players, state);
    }

    public static class Builder {
        private final String gameName;
        private Map<String, Player> players;
        private Player creator;
        private int minimumPlayers;
        private int minimumAmount;

        public Builder(String gameName) {
            this.gameName = gameName;
        }

        public Builder setPlayers(Map<String, Player> players) {
            this.players = players;
            return this;
        }

        public Builder setCreator(Player creator) {
            this.creator = creator;
            return this;
        }

        public Builder setMinimumPlayers(int minimumPlayers) {
            this.minimumPlayers = minimumPlayers;
            return this;
        }

        public Builder setMinimumAmount(int minimumAmount) {
            this.minimumAmount = minimumAmount;
            return this;
        }

        private boolean isValid() {
            return (Objects.isNull(this.gameName) || Objects.isNull(this.players) || Objects.isNull(this.creator)) && this.minimumPlayers > 1 && this.minimumAmount >= 0;
        }

        public Game build() {
            if (isValid()) {
                return null;
            }
            return new Game(this.gameName, this.players, this.creator, this.minimumPlayers, this.minimumAmount, new BuyInState());
        }
    }

    public String getGameName() {
        return gameName;
    }

    public boolean addUserToGame(Player player) {
        return this.logicFacade.addUserToGame(player);
    }

    public boolean startGame(Player player) {
        if (creator.equals(player)) {
            this.logicFacade.startGame(this);
            return true;
        }
        return false;
    }
}
