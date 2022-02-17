package com.poker.logic.game.logic;

import com.poker.logic.factory.card.CardFactory;
import com.poker.logic.game.ETypeOfGame;
import com.poker.model.card.ICard;
import com.poker.model.enums.RoundState;
import com.poker.model.filter.Log;
import com.poker.model.payment.EServices;
import com.poker.model.payment.ServiceAdapter;
import com.poker.model.player.Player;
import com.poker.utils.CardsUtils;
import com.poker.utils.MapUtils;

import java.io.Serializable;
import java.util.*;

/**
 * In this class must be all the components that can simplify the logic functions
 */
public class GameEngine implements Serializable {
    private static final Log LOG = Log.getInstance();
    private static final long serialVersionUID = 2948663272030254773L;

    private final ServiceAdapter walletUtils = new ServiceAdapter(EServices.UNKNOWN);
    private final Map<String, Player> players;
    private final Queue<String> queuePlayOrder;
    private final Map<String, Integer> playerBetsList;
    private final List<String> playerFoldList;
    private final List<ICard> deck;
    private final List<ICard> tableCards;
    private Player dealer;
    private Integer pot;
    private RoundState roundState;
    private Integer higherBet;
    private Integer smallBlind;
    private Integer bigBlind;

    public GameEngine(Map<String, Player> players, Integer bigBlind) {
        this.players = players;
        this.queuePlayOrder = new ArrayDeque<>();
        this.dealer = null;
        this.deck = new CardFactory().createObject(null);
        this.tableCards = new ArrayList<>();
        this.pot = 0;
        this.roundState = RoundState.SETUP;
        this.higherBet = null;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        playerBetsList = new HashMap<>();
        playerFoldList = new ArrayList<>();
    }

    public boolean playerInGame(String username) {
        return !Objects.isNull(this.players.get(username));
    }

    // TODO: generalize this method for all games or validate in another place TBC
    // Add player to the game and convert PCs into PCJs
    public boolean addPlayer(Player player, int entryFee, ETypeOfGame typeOfGame) {
        if (!this.playerInGame(player.getName())) {
            //TODO: [TBC] convert PCs into PCJs in a competitive game
            if (Objects.equals(ETypeOfGame.COMPETITIVE, typeOfGame) && player.getWallet().getPokerChips() >= entryFee) {
                walletUtils.chipsToGame(entryFee);
            }
            this.players.put(player.getName(), player);
            return true;
        }
        return false;
    }

    // Remove player from the game and convert PCJs into PCs
    public boolean removePlayer(String playerName, ETypeOfGame typeOfGame) {
        if (this.playerInGame(playerName)) {
            //TODO: [TBC] convert game money to wallet
            // TODO: validate if is a friendly game
            walletUtils.chipsToPocket(players.remove(playerName).getWallet().getPokerGameChips());
            return true;
        }
        return false;
    }

    public boolean startGame(String playerName, String creatorName, Integer minPlayers) {
        // TODO: [TBC] validate min numbers of players
        if (creatorName.equals(playerName) && minPlayers >= players.size()) {
            players.forEach((s, player) -> queuePlayOrder.add(s));
            startRound();
            System.out.println("[Game] Let's Poker. Good luck!");
            return true;
        }
        System.out.println("[Game] Game could not be started");
        return false;
    }

    public void startRound() {
        this.chooseDealer(dealer);
        // TODO: [TBC] give each player cards
        CardsUtils.distributeCardsPerPlayer(players, deck);
        roundState = RoundState.SETUP;
        pot = 0;
        reset();
    }

    private void reset() {
        higherBet = null;
        playerBetsList.clear();
        playerFoldList.clear();
        tableCards.clear();
        fillQueue();
    }

    // FIXME: maybe move this to a GameUtils
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
        // Check if is the player turn
        if (!playerName.equals(queuePlayOrder.peek())) {
            System.out.println();
            LOG.addAndShowLog("[Game] " + queuePlayOrder.peek() + " needs to bet first!");
            return false;
        }

        // Don't have PCJs enough
        if (!players.get(playerName).getWallet().removePokerGameChips(amount)) {
            return false;
        }

        // TODO: [TBC] bet logic (check if the player needs to bet more, history if bets in that turn)
        // Compare with the higher bet
        if (higherBet == null) {
            higherBet = amount;
        } else if (amount < higherBet) {
            System.out.println("[Game] Player: " + playerName + " need to bet at least " + higherBet + " PCJs");
            return false;
        } else if (amount > higherBet) {
            higherBet = amount;
            players.forEach((s, player) -> {
                if (s.equals(playerName)) return;
                queuePlayOrder.add(s);
            });
        }

        playerBetsList.put(playerName, playerBetsList.containsKey(playerName) ? playerBetsList.get(playerName) + amount : amount);

        addToPot(amount);
        System.out.println("[Game] Player: " + playerName + " made a bet of " + amount + " PCJs");
        queuePlayOrder.remove(playerName);
        return queuePlayOrder.size() == 0;
    }

    public boolean check(String playerName) {
        if (!playerName.equals(queuePlayOrder.peek())) {
            System.out.println("[Game]" + queuePlayOrder.peek() + " needs to play first!");
            return false;
        }

        // TODO: check if the player can check or needs to bet
        if (higherBet != 0) {
            System.out.println("[Game] Player: " + playerName + " needs to bet or fold! Value to call: " + higherBet);
            return false;
        }

        System.out.println("[Game] Player: " + playerName + " Check!");
        queuePlayOrder.remove(playerName);
        return true;
    }

    public boolean fold(String playerName) {
        if (!playerName.equals(queuePlayOrder.peek())) {
            System.out.println("[Game]" + queuePlayOrder.peek() + " needs to wait is turn to give up!");
            return false;
        }
        // TODO: [TBC] remove from queue and save that he fold from this round
        queuePlayOrder.remove(playerName);
        playerFoldList.add(playerName);
        return true;
    }

    private void printDeck() {
        System.out.println("## Table cards: " + CardsUtils.cardsToString(tableCards.toArray(ICard[]::new)));
    }

    public boolean triggerTheFlop() {
        tableCards.addAll(Objects.requireNonNull(CardsUtils.withdrawMoreThanOne(3, deck)));
        printDeck();
        fillQueue();
        return isRoundOver();
    }

    public boolean triggerNextCard() {
        withdrawCardToTable();
        printDeck();
        fillQueue();
        return isRoundOver();
    }

    public void triggerShowdown() {
        // TODO: calculate the winner, info that and set new cards to the players
        System.out.println("[Game] Winner winner chicken dinner"); // TODO: TBR
        startRound();
    }

//    public boolean turnCard() {
//        switch (roundState) {
//            case SETUP: // 3 cards face down
//                roundState = RoundState.THE_FLOP;
//                tableCards.addAll(Objects.requireNonNull(CardsUtils.withdrawMoreThanOne(3, deck)));
//                System.out.println("[Game] show 3 cards face up"); // TODO: TBR
//                printDeck();
//                break;
//            case THE_FLOP: // 3 cards face up
//                roundState = RoundState.THE_TURN;
//                withdrawCardToTable();
//                System.out.println("[Game] show 4º card"); // TODO: TBR
//                printDeck();
//                break;
//            case THE_TURN: // 4º card
//                roundState = RoundState.THE_RIVER;
//                withdrawCardToTable();
//                System.out.println("[Game] show 5º card"); // TODO: TBR
//                printDeck();
//                break;
//            case THE_RIVER: // show 5º card
//                roundState = RoundState.SETUP;
//                // TODO: calculate the winner, info that and set new cards to the players
//                System.out.println("[Game] Winner winner chicken dinner"); // TODO: TBR
//                if (isRoundOver()) return true; // TODO: need to check for this every round (card turn)
//                break;
//            case SHOWDOWN:
//                // TODO:
//                System.out.println("Winner winner chicken dinner"); // TODO: TBR
//                if (isRoundOver()) return true;
//                break;
//        }
//        fillQueue();
//        // Isn't the last round
//        return false;
//    }

    private void withdrawCardToTable() {
        tableCards.add(CardsUtils.withdrawFromTop(deck));
    }

    public boolean isRoundOver() {
        // TODO: check if is the endRound check the number of players in the queue before fillQueue()
        // if true show the table winner
        return false;
    }

    public boolean isGameOver() {
        // TODO: check if is the endgame check the number of players and the minimum bet
        return false;
    }

    private void fillQueue() {
        if (queuePlayOrder.size() == 0) {
            players.forEach((s, player) -> {
                        if (!playerGaveUp(player.getName())) {
                            queuePlayOrder.add(s);
                        }
                    }
            );
        }
    }

    private boolean playerGaveUp(String playerName) {
        return playerFoldList.contains(playerName);
    }

    public String showGameInfo(String gameName) {
        StringBuilder str = new StringBuilder();
        str.append("$$$$$ Game: ").append(gameName).append(" $$$$$");
        str.append("\n## Players: [");
        players.forEach((s, player) -> str.append(s).append(", "));
        str.setLength(str.length() - 2); // remove the last 2 characters
        str.append("]");
        str.append("\n## Table cards: ").append(CardsUtils.printCards(tableCards.toArray(ICard[]::new)));
        str.append("\n## Pot value: ").append(pot).append(" PCJs");
        str.append("\n## Next turn: ").append(queuePlayOrder.peek());
        str.append("\n## Blinds: ").append("S: ").append(smallBlind).append(" | B: ").append(bigBlind);
        str.append("\n## Higher bet: ").append(higherBet == null ? 0 : higherBet);
        return str.toString();
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

    public Map<String, Player> getPlayers() {
        return players;
    }

    //</editor-fold>
}
