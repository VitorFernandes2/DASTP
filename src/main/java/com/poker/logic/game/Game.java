package com.poker.logic.game;

import com.poker.logic.factory.card.CardFactory;
import com.poker.logic.game.logic.GameEngine;
import com.poker.logic.game.state.BuyInState;
import com.poker.logic.game.state.IGameState;
import com.poker.model.card.ICard;
import com.poker.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Game {
    private final String gameName;
    private final Player creator;
    private final ETypeOfGame typeOfGame;
    private Player dealer;
    private final int minimumPlayers;
    private final int minimumAmount;
    private final GameEngine gameEngine;
    private final List<ICard> deck;
    private final List<ICard> tableCards;

    private Game(String gameName, ETypeOfGame typeOfGame, Map<String, Player> players, Player creator, int minimumPlayers, int minimumAmount, IGameState state) {
        this.gameName = gameName;
        this.typeOfGame = typeOfGame;
        this.creator = creator;
        this.minimumPlayers = minimumPlayers;
        this.minimumAmount = minimumAmount;
        this.gameEngine = new GameEngine(players, state);
        CardFactory factory = new CardFactory();
        this.dealer = null;
        this.deck = factory.createDeck();
        this.tableCards = new ArrayList<>();
    }

    public String getGameName() {
        return gameName;
    }

    public Player getDealer() {
        return dealer;
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public boolean addUserToGame(Player player) {
        return this.gameEngine.addUserToGame(player);
    }

    public boolean startGame(Player player) {
        if (creator.equals(player)) {
            this.gameEngine.startGame(this);
            return true;
        }
        return false;
    }

    public void bet(String playerName, Double amount) {
        this.gameEngine.bet(this);
    }

    public void fold(String playerName, Double amount) {
        this.gameEngine.fold(this);
    }

    public void check(String playerName) {
        this.gameEngine.check(this);
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
            return new Game(this.gameName, this.typeOfGame, this.players, this.creator, this.minimumPlayers, this.minimumAmount, new BuyInState());
        }
    }
}
