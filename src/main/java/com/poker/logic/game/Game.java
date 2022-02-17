package com.poker.logic.game;

import com.poker.logic.game.logic.GameEngine;
import com.poker.logic.game.state.BuyInState;
import com.poker.logic.game.state.IGameState;
import com.poker.model.constants.Constants;
import com.poker.model.player.Player;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class Game implements Serializable {
    private static final long serialVersionUID = -5860017999599725631L;

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

    private Game(String gameName, Player creator, ETypeOfGame typeOfGame, int minimumPlayers, int minimumAmount, GameEngine gameEngine) {
        this.gameName = gameName;
        this.creator = creator;
        this.typeOfGame = typeOfGame;
        this.minimumPlayers = minimumPlayers;
        this.minimumAmount = minimumAmount;
        this.gameEngine = gameEngine;
        this.state = new BuyInState(this.gameEngine);
    }

    public String getGameName() {
        return gameName;
    }

    public boolean addPlayer(Player player, int fee) {
        return this.gameEngine.addPlayer(player, fee, typeOfGame);
    }

    public boolean removePlayer(String player) {
        return this.gameEngine.removePlayer(player, typeOfGame);
    }

    public void startGame(Player player) {
        setState(this.state.startGame(player.getName(), creator.getName(), minimumPlayers));
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

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public ETypeOfGame getTypeOfGame() {
        return typeOfGame;
    }

    public String getCreatorName() {
        return this.creator.getName();
    }

    public IGameState getState() {
        return state;
    }

    public boolean isNull() {
        return getState() == null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Game.Builder(this.gameName)
                .setMinimumPlayers(this.minimumPlayers)
                .setMinimumAmount(this.minimumAmount)
                .setGameEngine(this.gameEngine)
                .setTypeOfGame(this.typeOfGame)
                .setCreator(this.creator)
                .build();
    }

    public static class Builder {
        private final String gameName;
        private Map<String, Player> players;
        private Player creator;
        private ETypeOfGame typeOfGame;
        private GameEngine gameEngine;
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

        public Builder setGameEngine(GameEngine gameEngine) {
            this.gameEngine = gameEngine;
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
            return (Objects.isNull(this.gameName) || Objects.isNull(this.creator)) && this.minimumPlayers > 1 && this.minimumAmount >= 0;
        }

        public Game build() {
            if (isValid()) {
                return null;
            }

            if (gameEngine != null) {
                return new Game(this.gameName, this.creator, this.typeOfGame, this.minimumPlayers, this.minimumAmount, this.gameEngine);
            } else if (!Objects.isNull(this.players)) {
                return new Game(this.gameName, this.typeOfGame, this.players, this.creator, this.minimumPlayers, this.minimumAmount);
            }

            return null;
        }
    }
}
