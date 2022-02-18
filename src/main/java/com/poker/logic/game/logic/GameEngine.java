package com.poker.logic.game.logic;

import com.poker.logic.factory.card.CardFactory;
import com.poker.logic.game.ETypeOfGame;
import com.poker.model.card.ICard;
import com.poker.model.filter.Log;
import com.poker.model.payment.EServices;
import com.poker.model.payment.ServiceAdapter;
import com.poker.model.player.Player;
import com.poker.utils.CardsUtils;
import com.poker.utils.MapUtils;
import com.poker.utils.ScoreUtils;

import java.io.Serializable;
import java.util.*;

/**
 * In this class must be all the components that can simplify the logic functions
 */
public class GameEngine implements Serializable {
    private static final Log LOG = Log.getInstance();
    private static final long serialVersionUID = 2948663272030254773L;

    private final ServiceAdapter walletUtils = new ServiceAdapter(EServices.UNKNOWN);
    private final Map<String, Player> players; // TODO: update this list when the dealer change, to be the first
    private final Queue<String> queuePlayOrder;
    private final Map<String, Integer> playerBetsList;
    private final List<String> playerFoldList;
    private final List<ICard> deck;
    private final List<ICard> tableCards;
    private Player dealer;
    private Integer pot;
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
        this.higherBet = 0;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        playerBetsList = new HashMap<>();
        playerFoldList = new ArrayList<>();
    }

    // TODO: [TBC] generalize this method for all games or validate in another place TBC
    // Add player to the game and convert PCs into PCJs
    public boolean addPlayer(Player player, int fee, ETypeOfGame typeOfGame) {
        if (!this.playerInGame(player.getName())) {
            //TODO: [TBC] convert PCs into PCJs in a competitive game
            if (Objects.equals(ETypeOfGame.FRIENDLY, typeOfGame)) {
                walletUtils.chipsToGame(1);
            } else if (Objects.equals(ETypeOfGame.COMPETITIVE, typeOfGame) && player.getWallet().getPokerChips() >= fee) {
                walletUtils.chipsToGame(fee); // TODO: remove PCs
            } else {
                return false;
            }
            this.players.put(player.getName(), player);
            return true;
        }
        return false;
    }

    // Remove player from the game and convert PCJs into PCs
    public boolean removePlayer(String playerName, ETypeOfGame typeOfGame) {
        if (this.playerInGame(playerName)) {
            // TODO: [TBC] validate if is a friendly game and convert game money to wallet
            if (Objects.equals(ETypeOfGame.COMPETITIVE, typeOfGame)) {
                walletUtils.chipsToPocket(players.remove(playerName).getWallet().resetPokerGameChips());
            } else {
                players.remove(playerName).getWallet().resetPokerGameChips();
            }
            return true;
        }
        return false;
    }

    public boolean startGame(String playerName, String creatorName, Integer minPlayers) {
        if (creatorName.equals(playerName) && minPlayers <= players.size()) {
            players.forEach((s, player) -> queuePlayOrder.add(s));
            startRound();
            System.out.println("[Game] Let's Poker. Good luck!");
            return true;
        }
        System.out.println(!creatorName.equals(playerName)
                ? "[Game] The player " + creatorName + " needs to started this game!"
                : "[Game] You don't have the minimum players to start! minimum: " + minPlayers + " players.");
        return false;
    }

    public void startRound() {
        reset();
    }

    private void reset() {
        chooseDealer(dealer);
        CardsUtils.distributeCardsPerPlayer(players, deck);
        pot = 0;
        higherBet = 0;
        playerBetsList.clear();
        playerFoldList.clear();
        tableCards.clear();
        fillQueue();
    }

    // FIXME: maybe move this to a GameUtils
    private void chooseDealer(Player dealer) {
        if (Objects.isNull(dealer)) {
            //TODO: [IMPROVEMENT] use the correct implementation of dealer choose (witch one take a card and the higher one start the game and receive the dealer)
            Map.Entry<String, Player> newDealerSet = this.players.entrySet().iterator().next();
            setDealer(newDealerSet.getValue());
        } else {
            MapUtils<String, Player> mapUtils = new MapUtils<>();
            int position = mapUtils.getMapPosition(this.players, dealer.getName());
            if (position >= 0) {
                int newDealerPosition = (position == (this.players.size() - 1)) ? 0 : position + 1;
                Player newDealer = (new ArrayList<>(this.players.values())).get(newDealerPosition);
                setDealer(newDealer);
            }
        }
    }

    private Integer getTotalAmount(String playerName, Integer amount) {
        return playerBetsList.get(playerName) == null ? amount : playerBetsList.get(playerName) + amount;
    }

    public boolean bet(String playerName, Integer amount) {
        if (!isPlayerTurn(playerName)) return false;

        // Don't have PCJs enough
        if (!players.get(playerName).getWallet().removePokerGameChips(amount)) {
            return false;
        }

        // TODO: check if is equals or higher then the minimum amount

        // TODO: [TBC] bet logic (check if the player needs to bet more, history if bets in that turn)
        // Compare with the higher bet
        if (higherBet == null) {
            higherBet = getTotalAmount(playerName, amount);
        } else if (getTotalAmount(playerName, amount) < higherBet) {
            System.out.println("[Game] Player: " + playerName +
                    " need to bet at least " +
                    (playerBetsList.containsKey(playerName) ? higherBet - playerBetsList.get(playerName) : higherBet) +
                    " PCJs");
            return false;
        } else if (getTotalAmount(playerName, amount) > higherBet) {
            if (higherBet != 0) {
                for (Map.Entry<String, Player> entry : players.entrySet()) {
                    String s = entry.getKey();
                    if (s.equals(playerName)) break;
                    queuePlayOrder.add(s);
                }
            }
            higherBet = getTotalAmount(playerName, amount);
        }

        // TODO: bet history of the actual state
        playerBetsList.put(playerName, playerBetsList.containsKey(playerName) ? playerBetsList.get(playerName) + amount : amount);

        addToPot(amount);
        System.out.println("[Game] Player: " + playerName + " made a bet of " + amount + " PCJs");
        queuePlayOrder.remove(playerName);
        return queuePlayOrder.size() == 0;
    }

    public boolean check(String playerName) {
        if (!playerName.equals(queuePlayOrder.peek())) {
            System.out.println("[Game] " + queuePlayOrder.peek() + " needs to play first!");
            return false;
        }

        // TODO: [TBC] check if the player can check or needs to bet
        if (higherBet != 0 && !higherBet.equals(playerBetsList.get(playerName))) {
            System.out.println("[Game] Player: " + playerName + " needs to bet or fold! Value to call: " + higherBet);
            return false;
        }

        System.out.println("[Game] Player: " + playerName + " Check!");
        queuePlayOrder.remove(playerName);
        return queuePlayOrder.size() == 0;
    }

    public boolean fold(String playerName) {
        if (!isPlayerTurn(playerName)) return false;

        queuePlayOrder.remove(playerName);
        playerFoldList.add(playerName);
        return queuePlayOrder.size() == 0;
    }

    public boolean triggerTheFlop() {
        tableCards.addAll(Objects.requireNonNull(CardsUtils.withdrawMoreThanOne(3, deck)));
        System.out.println("[Game] 3 cards has been turn up");
        printDeck();
        fillQueue();
        printCardsPerPlayer();
        return isRoundOver();
    }

    public boolean triggerNextCard() {
        withdrawCardToTable();
        System.out.println("[Game] 1 card has been turn up");
        printDeck();
        fillQueue();
        printCardsPerPlayer();
        return isRoundOver();
    }

    public void triggerShowdown() {
        fillQueue();
        ScoreUtils.scoring(this.pot, this.tableCards, this.queuePlayOrder, this.players);
        startRound();
    }

    private void withdrawCardToTable() {
        tableCards.add(CardsUtils.withdrawFromTop(deck));
    }

    public boolean playerInGame(String username) {
        return !Objects.isNull(this.players.get(username));
    }

    private boolean playerGaveUp(String playerName) {
        return playerFoldList.contains(playerName);
    }

    // Check if is the player turn
    public boolean isPlayerTurn(String playerName) {
        if (playerName.equals(queuePlayOrder.peek())) {
            return true;
        }
        LOG.addAndShowLog("[Game] " + queuePlayOrder.peek() + " needs to wait is turn!");
        return false;
    }

    // TODO: check if is the endRound check the number of players in the queue before fillQueue()
    public boolean isRoundOver() {
        // if true show the table winner
        return false;
    }

    // TODO: check if is the endgame check the number of players and the minimum bet
    public boolean isGameOver() {
        return false;
    }

    private void fillQueue() {
        if (queuePlayOrder.size() == 0) {
            // Clear the bets from the last card turn phase
            playerBetsList.clear();
            higherBet = 0;

            players.forEach((s, player) -> {
                        if (!playerGaveUp(player.getName())) {
                            queuePlayOrder.add(s);
                        }
                    }
            );
        }
    }

    private void printCardsPerPlayer() {
        System.out.println(CardsUtils.cardsPerPlayerToString(players));
    }

    private void printDeck() {
        System.out.println("## Table cards: " + CardsUtils.cardsToString(tableCards.toArray(ICard[]::new)));
    }

    private void printPlayers() {
        StringBuilder str = new StringBuilder();
        str.append("\n## Players: [");
        players.forEach((s, player) -> str.append(s).append(", "));
        str.setLength(str.length() - 2); // remove the last 2 characters
        str.append("]");
        System.out.println(str);
    }

    public String showGameInfo(String gameName) {
        StringBuilder str = new StringBuilder();
        str.append("$$$$$ Game: ").append(gameName).append(" $$$$$");
        str.append("\n## Table cards: ").append(CardsUtils.cardsToString(tableCards.toArray(ICard[]::new)));
        str.append("\n## Pot value: ").append(pot).append(" PCJs\n");
        str.append(CardsUtils.cardsPerPlayerToString(players));
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
