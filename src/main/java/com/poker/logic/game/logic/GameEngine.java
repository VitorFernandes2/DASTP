package com.poker.logic.game.logic;

import com.poker.logic.factory.card.CardFactory;
import com.poker.model.card.ICard;
import com.poker.model.enums.RoundState;
import com.poker.model.player.Player;
import com.poker.utils.MapUtils;

import java.util.*;

/**
 * In this class must be all the components that can simplify the logic functions
 */
public class GameEngine {
    private final Map<String, Player> players;
    private final Queue<String> queuePlayOrder;
    private final List<ICard> deck;
    private final List<ICard> tableCards;
    private Player dealer;
    private Integer pot;
    private RoundState roundState;

    public GameEngine(Map<String, Player> players) {
        this.players = players;
        queuePlayOrder = new ArrayDeque<>();
        this.dealer = null;
        this.deck = new CardFactory().createDeck();
        this.tableCards = new ArrayList<>();
        this.pot = 0;
        roundState = RoundState.FIRST_STATE;
    }

    public boolean userInGame(String username) {
        return !Objects.isNull(this.players.get(username));
    }

    // TODO: remove player from this game or player leaving
    // TODO: generalize this method for all games or validate in another place TBC
    public boolean addUserToGame(Player player) {
        if (!this.userInGame(player.getName())) {
            //TODO: remove game money
            this.players.put(player.getName(), player);
            return true;
        }
        return false;
    }

    public boolean startGame(String playerName, String creatorName) {
        // TODO: validate min numbers of players
        if (creatorName.equals(playerName)) {
            players.forEach((s, player) -> queuePlayOrder.add(s));
            return true;
        }
        return false;
    }

    public void startRound() throws Exception {
        System.out.println("Start new turn!");
        this.chooseDealer(dealer);
        // TODO: give each player cards
        // TODO: three cards on the table face down
        roundState = RoundState.SECOND_STATE;
    }

    // TODO: maybe move this to a GameUtils
    private void chooseDealer(Player dealer) throws Exception {
        if (Objects.isNull(dealer)) {
            //TODO: [IMPROVEMENT] use the correct implementation of dealer choose (witch one take a card and the higher one start the game and receive the dealer)
            if (this.players.size() > 0) {
                Map.Entry<String, Player> newDealerSet = this.players.entrySet().iterator().next();
                setDealer(newDealerSet.getValue());
            } else {
                throw new Exception("No players found!");
            }
        } else {
            if (this.players.size() > 0) {
                MapUtils<String, Player> mapUtils = new MapUtils<>();
                int position = mapUtils.getMapPosition(this.players, dealer.getName());
                if (position >= 0) {
                    int newDealerPosition = (position == (this.players.size() - 1)) ? 0 : position + 1;
                    Player newDealer = (new ArrayList<>(this.players.values())).get(newDealerPosition);
                    setDealer(newDealer);
                }
            } else {
                throw new Exception("No players found!");
            }
        }
    }

    public boolean bet(String playerName, Integer amount) {
        if (!playerName.equals(queuePlayOrder.peek())) {
            System.out.println(queuePlayOrder.peek() + " needs to bet first!");
            return false;
        }

        // Don't have PCJs enough
        if (!players.get(playerName).getWallet().removePokerGameChips(amount)) {
            return false;
        }

        addToPot(amount);
        System.out.println("[Game] Player: " + playerName + " made a bet of " + amount + " PCJs");
        queuePlayOrder.remove(playerName);
        // TODO: bet logic (check if the player needs to bet more, history if bets in that turn)
        return queuePlayOrder.size() == 0;
    }

    public boolean check(String playerName) {
        if (!playerName.equals(queuePlayOrder.peek())) {
            System.out.println(queuePlayOrder.peek() + " needs to play first!");
            return false;
        }

        // TODO: check if the player can check or needs to bet

        System.out.println("[Game] Player: " + playerName + " Check!");
        queuePlayOrder.remove(playerName);
        return true;
    }

    public boolean fold(String playerName) {
        if (!playerName.equals(queuePlayOrder.peek())) {
            System.out.println(queuePlayOrder.peek() + " needs to wait is turn to give up!");
            return false;
        }
        queuePlayOrder.remove(playerName);
        int amountInGame = players.remove(playerName).getWallet().getPokerGameChips();
        // TODO: convert PCJs to PCs
        return true;
    }

    public boolean turnCard() {
        if (roundState.equals(RoundState.FOURTH_STATE)) {
            // TODO: show all cards, calculate the winner and set new cards to the players
            fillQueue();
            roundState = RoundState.FIRST_STATE;
        }

        if(roundState == RoundState.SECOND_STATE) {
            // TODO: turn Card
            roundState = RoundState.THIRD_STATE;
        }
        if(roundState == RoundState.THIRD_STATE) {
            // TODO: turn Card
            roundState = RoundState.FOURTH_STATE;
        }
        return false;
    }

    private void fillQueue() {
        if (queuePlayOrder.size() == 0)
            players.forEach((s, player) -> queuePlayOrder.add(s));
    }

    //<editor-fold defaultstate="collapsed" desc=" Gets and Sets ">
    public List<ICard> getDeck() {
        return deck;
    }

    public List<ICard> getTableCards() {
        return tableCards;
    }

    public Player getDealer() {
        return dealer;
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public Integer getPot() {
        return pot;
    }

    public void setPot(Integer pot) {
        this.pot = pot;
    }

    public void addToPot(Integer value) {
        this.pot += value;
    }

    public boolean checkEndRound() {

        return false;
    }
    //</editor-fold>
}
