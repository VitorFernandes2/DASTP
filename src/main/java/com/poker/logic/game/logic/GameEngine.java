package com.poker.logic.game.logic;

import com.poker.logic.game.Game;
import com.poker.logic.game.state.DealerButtonState;
import com.poker.model.player.Player;
import com.poker.logic.game.state.IGameState;
import com.poker.utils.MapUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * In this class must be all the components that can simplify the logic functions
 */
public class GameEngine {

    private final Map<String, Player> players;
    private IGameState state;

    public GameEngine(Map<String, Player> players, IGameState state) {
        this.players = players;
        this.state = state;
    }

    public boolean userInGame(String username) {
        return !Objects.isNull(this.players.get(username));
    }

    public boolean addUserToGame(Player player) {
        if (!this.userInGame(player.getName())) {
            //TODO: remove game money
            this.players.put(player.getName(),player);
            return true;
        }
        return false;
    }

    public void startGame(Game game) {
        this.state = this.state.startGame();
    }

    public void startTurn(Game game) throws Exception {
        if (this.state instanceof DealerButtonState) {
            this.chooseDealer(game);
        }
        this.state = this.state.startTurn();
    }

    private void chooseDealer(Game game) throws Exception {
        Player dealer = game.getDealer();
        if (Objects.isNull(dealer)) {
            //TODO: use the correct implementation of dealer choose
            if (this.players.size() > 0) {
                Map.Entry<String, Player> newDealerSet = this.players.entrySet().iterator().next();
                Player newDealer = newDealerSet.getValue();
                game.setDealer(newDealer);
            } else {
                throw new Exception("No players found!");
            }
        } else {
            if (this.players.size() > 0) {
                MapUtils<String, Player> mapUtils = new MapUtils<>();
                int position = mapUtils.getMapPosition(this.players, dealer.getName());
                if (position >= 0) {
                    int newDealerPosition = (position==(this.players.size()-1)) ? 0 : position + 1;
                    Player value = (new ArrayList<Player>(((LinkedHashMap<String, Player>)this.players).values())).get(newDealerPosition);
                    game.setDealer(value);
                }
            } else {
                throw new Exception("No players found!");
            }
        }
    }

    public void bet(Game game) {
        this.state.bet();
    }

    public void fold(Game game) {
        this.state.fold();
    }

    public void check(Game game) {
        this.state.check();
    }
}
