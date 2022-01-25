package com.poker.logic.game;

import com.poker.logic.game.logic.LogicFacade;
import com.poker.logic.game.state.BuyInState;
import com.poker.logic.game.state.IGameState;
import com.poker.model.player.Player;

import java.util.List;
import java.util.Objects;

public class Game {
    private final String gameName;
    private final List<Player> players;
    private final int minimumPlayers;
    private final int minimumAmount;
    private final LogicFacade logicFacade;

    private Game(String gameName, List<Player> players, int minimumPlayers, int minimumAmount, int bigBlind, int smallBlind, IGameState state) {
        this.gameName = gameName;
        this.players = players;
        this.minimumPlayers = minimumPlayers;
        this.minimumAmount = minimumAmount;
        this.logicFacade = new LogicFacade(players, bigBlind, smallBlind, state);
    }

    public static class Builder {
        private final String gameName;
        private List<Player> players;
        private int minimumPlayers;
        private int minimumAmount;
        private int smallBlind;
        private int bigBlind;

        public Builder(String gameName) {
            this.gameName = gameName;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
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

        public Builder setSmallBlind(int smallBlind) {
            this.smallBlind = smallBlind;
            return this;
        }

        public Builder setBigBlind(int bigBlind) {
            this.bigBlind = bigBlind;
            return this;
        }

        private boolean isValid() {
            return (Objects.isNull(this.gameName) || Objects.isNull(this.players)) && minimumPlayers > 2 && minimumAmount > 0 && bigBlind > smallBlind && bigBlind > 0 && smallBlind > 0;
        }

        public Game build() {
            if (!isValid()) {
                return null;
            }
            return new Game(this.gameName, this.players, this.minimumPlayers, this.minimumAmount, this.bigBlind, this.smallBlind, new BuyInState());
        }
    }

    //TODO: function area - it will be removed once the logic is implemented
}
