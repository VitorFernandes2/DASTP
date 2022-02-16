package com.poker.logic.game;

import com.poker.logic.game.logic.GameEngine;
import com.poker.logic.game.state.BuyInState;
import com.poker.logic.game.state.IGameState;
import com.poker.model.constants.Constants;
import com.poker.model.player.Player;

import java.util.Map;
import java.util.Objects;

public class Game {
    private final String gameName;
    private final Player creator;
    private final ETypeOfGame typeOfGame;
    private final int minimumPlayers;
    private final int minimumAmount;
    private final GameEngine gameEngine;
    private IGameState state;

    private Game(String gameName, ETypeOfGame typeOfGame, Map<String, Player> players, Player creator, int minimumPlayers, int minimumAmount) {
        this.gameName = gameName;
        this.typeOfGame = typeOfGame;
        this.creator = creator;
        this.minimumPlayers = minimumPlayers;
        this.minimumAmount = minimumAmount;
        this.gameEngine = new GameEngine(players, Constants.PCJ_MINIMUM_BET);
        this.state = new BuyInState(this.gameEngine);
    }

    public String getGameName() {
        return gameName;
    }

    public boolean addPlayer(Player player) {
        return this.gameEngine.addPlayer(player, 1, typeOfGame); // TODO: get entry fee
    }

    public boolean removePlayer(String player) {
        return this.gameEngine.removePlayer(player, typeOfGame);
    }

    public void startGame(Player player) {
        setState(this.state.startGame(player.getName(), creator.getName(), minimumPlayers));
    }

    public void startTurn() {
        setState(this.state.startRound());
    }

    public void bet(String playerName, Integer amount) {
        setState(this.state.bet(playerName, amount));
    }

    public void fold(String playerName) {
        setState(this.state.fold(playerName));
    }

    public void check(String playerName) {
        setState(this.state.check(playerName));
    }

    public void setState(IGameState state) {
        this.state = state;
    }

    public String showGameInfo(String gameName) {
        return gameEngine.showGameInfo(gameName);
    }

    public static class Builder {
        private final String gameName;
        private Map<String, Player> players;
        private Player creator;
        private ETypeOfGame typeOfGame;
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

        public Builder setTypeOfGame(ETypeOfGame typeOfGame) {
            this.typeOfGame = typeOfGame;
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
            return new Game(this.gameName, this.typeOfGame, this.players, this.creator, this.minimumPlayers, this.minimumAmount);
        }
    }
}
