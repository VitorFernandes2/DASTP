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
    private final Map<String, Player> players;
    private final Queue<String> queuePlayOrder;
    private final Queue<String> queueDealerOrder;
    private final Map<String, Integer> playerBetsList;
    private final List<String> playerFoldList;
    private final List<String> playerAllInList;
    private final List<ICard> tableCards;
    private final ETypeOfGame typeOfGame;
    private final Integer smallBlind;
    private final Integer increment;
    private final Integer fee;
    private List<ICard> deck;
    private String dealer;
    private Integer pot;
    private Integer higherBet;
    private Integer bigBlind;
    private boolean isSmallBlind;

    public GameEngine(Map<String, Player> players, Integer bigBlind, ETypeOfGame typeOfGame, Integer increment, Integer fee) {
        this.players = players;
        this.queuePlayOrder = new ArrayDeque<>();
        this.queueDealerOrder = new ArrayDeque<>();
        this.dealer = null;
        this.deck = new CardFactory().createObject(null);
        this.tableCards = new ArrayList<>();
        this.typeOfGame = typeOfGame;
        this.pot = 0;
        this.higherBet = 0;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        this.playerBetsList = new HashMap<>();
        this.playerFoldList = new ArrayList<>();
        this.playerAllInList = new ArrayList<>();
        this.increment = increment;
        this.fee = fee;
    }

    // Add player to the game
    public boolean addPlayer(Player player) {
        if (!this.playerInGame(player.getName())) {
            this.players.put(player.getName(), player);
            return true;
        }
        return false;
    }

    // Remove player from the game and convert PCJs into PCs
    public boolean removePlayer(String playerName) {
        if (this.playerInGame(playerName)) {
            Player player = players.get(playerName);
            // TODO: [TBC] validate if is a friendly game and convert game money to wallet
            if (Objects.equals(ETypeOfGame.COMPETITIVE, typeOfGame)) {
                player.getWallet().addPokerChips(walletUtils.chipsToPocket(players.remove(playerName).getWallet().resetPokerGameChips()));
            } else {
                players.remove(playerName).getWallet().resetPokerGameChips();
            }
            queuePlayOrder.remove(playerName);
            queueDealerOrder.remove(playerName);
            return true;
        }
        return false;
    }

    // Start game and convert PCs into PCJs
    public boolean startGame(String playerName, String creatorName, Integer minPlayers) {
        if (creatorName.equals(playerName) && minPlayers <= players.size()) {
            List<String> playersToBeRemoved = new ArrayList<>();

            // Give each player PCJs
            players.forEach((s, player) -> {
                //TODO: [TBC] convert PCs into PCJs in a competitive game
                if (Objects.equals(ETypeOfGame.FRIENDLY, typeOfGame)) {
                    player.getWallet().setPokerGameChips(walletUtils.chipsToGame(1));
                } else if (Objects.equals(ETypeOfGame.COMPETITIVE, typeOfGame) && player.getWallet().getPokerChips() >= fee) {
                    player.getWallet().setPokerGameChips(walletUtils.chipsToGame(fee));
                    player.getWallet().removePokerChips(fee);
                } else {
                    System.out.println("[Game] The player " + s + " doesn't have PCs enough to play in this game");
                    playersToBeRemoved.add(s);
                }
            });
            // Remove players that doesn't have enough PCs
            playersToBeRemoved.forEach(players::remove);

            // fill queues of play and dealer order
            players.forEach((s, player) -> queueDealerOrder.add(s));
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

    // Start round
    public void startRound() {
        deck = new CardFactory().createObject(null);
        CardsUtils.distributeCardsPerPlayer(players, deck);

        reset();
    }

    // Reset for next round
    private void reset() {
        pot = 0;
        higherBet = 0;
        playerBetsList.clear();
        playerFoldList.clear();
        playerAllInList.clear();
        tableCards.clear();
        isSmallBlind = true;
        fillQueue();

        // dealer goes to the end of the queues
        queueDealerOrder.add(dealer = queueDealerOrder.poll());
        queuePlayOrder.add(queuePlayOrder.poll());
        System.out.println("[Game] The player " + dealer + " is the dealer");
        distributeSmallAndBigBlind();
    }

    // Set small and big blind
    private void distributeSmallAndBigBlind() {
        // Small Blind implementation
        String smallBlindPlayer = queuePlayOrder.peek();
        System.out.println("[Game] Small blind removed automatically form the player " + smallBlindPlayer);
        bet(smallBlindPlayer, smallBlind);
        isSmallBlind = false;
        // Add small blind player to the end of the queue
        queuePlayOrder.add(smallBlindPlayer);

        // Big Blind implementation
        String bigBlindPlayer = queuePlayOrder.peek();
        System.out.println("[Game] Big blind removed automatically form the player " + bigBlindPlayer);
        bet(bigBlindPlayer, bigBlind);
        // Add big blind player to the end of the queue, he is the player that decides is turn the cards or will raise more
        queuePlayOrder.add(bigBlindPlayer);
    }

    // FIXME: maybe move this to a GameUtils or remove
    private void chooseDealer(Player dealer) {
        if (Objects.isNull(dealer)) {
            Map.Entry<String, Player> newDealerSet = this.players.entrySet().iterator().next();
            dealer = newDealerSet.getValue();
        } else {
            MapUtils<String, Player> mapUtils = new MapUtils<>();
            int position = mapUtils.getMapPosition(this.players, dealer.getName());
            if (position >= 0) {
                int newDealerPosition = (position == (this.players.size() - 1)) ? 0 : position + 1;
                Player newDealer = (new ArrayList<>(this.players.values())).get(newDealerPosition);
                dealer = newDealer;
            }
        }
    }

    // Get the total amount bet in the actual state
    private Integer getBetsAmount(String playerName, Integer amount) {
        return playerBetsList.get(playerName) == null ? amount : playerBetsList.get(playerName) + amount;
    }

    // Bet logic
    public boolean bet(String playerName, Integer amount) {
        if (!isPlayerTurn(playerName)) return false;

        int betsAmount = getBetsAmount(playerName, amount);
        if (betsAmount < bigBlind && !isSmallBlind()) {
            System.out.println("[Game] The player " + playerName + " needs to bet at least " + bigBlind + " PCJs");
            return false;
        }

        // Compare with the higher bet
        if (higherBet == null) {
            higherBet = betsAmount;
        } else if (players.get(playerName).getWallet().getPokerGameChips() > higherBet && betsAmount < higherBet) { // need to call (if the player have enough PCJs and the bet is lower)
            System.out.println("[Game] The player " + playerName +
                    " need to bet more! Value to call: " +
                    (playerBetsList.containsKey(playerName) ? higherBet - playerBetsList.get(playerName) : higherBet) +
                    " PCJs");
            return false;
        } else if (betsAmount > higherBet) { // made a raise
            // Check if any player needs to be added to the queue
            if (players.size() - playerFoldList.size() > queuePlayOrder.size()) {
                List<String> playersAux = new ArrayList<>(queueDealerOrder);
                for (String player : queueDealerOrder) {
                    String playerNameAux = playersAux.remove(0);
                    if (player.equals(playerName)) break;
                    if (!playerFoldList.contains(playerNameAux) && !playerAllIn(playerNameAux) && !queuePlayOrder.contains(playerNameAux)) {
                        playersAux.add(playerNameAux);
                    }
                }
                playersAux.removeAll(queuePlayOrder);
                queuePlayOrder.addAll(playersAux);
            }
            higherBet = betsAmount;
        }

        // Don't have PCJs enough
        if (!players.get(playerName).getWallet().removePokerGameChips(amount)) {
            return false;
        }
        if (players.get(playerName).getWallet().getPokerGameChips() == 0) {
            playerAllInList.add(playerName);
        }
        playerBetsList.put(playerName, playerBetsList.containsKey(playerName) ? playerBetsList.get(playerName) + amount : amount);

        addToPot(amount);
        System.out.println("[Game] The player " + playerName + " made a bet of " + amount + " PCJs");
        queuePlayOrder.remove(playerName);
        return queuePlayOrder.size() == 0;
    }

    // Check logic
    public boolean check(String playerName) {
        if (!playerName.equals(queuePlayOrder.peek())) {
            System.out.println("[Game] The player " + queuePlayOrder.peek() + " needs to play first!");
            return false;
        }

        if (higherBet != 0 && !higherBet.equals(playerBetsList.get(playerName))) {
            System.out.println("[Game] The player " + playerName + " needs to bet or fold! Value to call: " +
                    (playerBetsList.containsKey(playerName) ? higherBet - playerBetsList.get(playerName) : higherBet));
            return false;
        }

        System.out.println("[Game] The player " + playerName + " Check!");
        queuePlayOrder.remove(playerName);
        return queuePlayOrder.size() == 0;
    }

    // Fold logic
    public boolean fold(String playerName) {
        if (!isPlayerTurn(playerName)) return false;

        queuePlayOrder.remove(playerName);
        playerFoldList.add(playerName);
        return queuePlayOrder.size() == 0;
    }

    // Trigger The Flop - turn 3 cards up
    public boolean triggerTheFlop() {
        tableCards.addAll(Objects.requireNonNull(CardsUtils.withdrawMoreThanOne(3, deck)));
        System.out.println("[Game] 3 cards has been turn up");
        printDeck();
        fillQueue();
        printCardsPerPlayer();
        return isRoundOver();
    }

    // Trigger next card - used when a card is turned in The Turn and The River state
    public boolean triggerNextCard() {
        withdrawCardToTable();
        System.out.println("[Game] 1 card has been turn up");
        printDeck();
        fillQueue();
        printCardsPerPlayer();
        return isRoundOver();
    }

    // Trigger Showdown - final state when best set of cards win
    public void triggerShowdown() {
        fillQueue();
        Queue<String> allPlayers = new ArrayDeque<>(queuePlayOrder);
        allPlayers.addAll(playerAllInList);

        switch (tableCards.size()) {
            case 0:
                tableCards.addAll(Objects.requireNonNull(CardsUtils.withdrawMoreThanOne(3, deck)));
            case 3:
                withdrawCardToTable();
            case 4:
                withdrawCardToTable();
        }

        ScoreUtils.scoring(this.pot, this.tableCards, allPlayers, this.players);
        clearPlayer();
    }

    // Remove players from the game if they don't have enough money to continue
    private void clearPlayer() {
        List<String> playersToBeRemoved = new ArrayList<>();
        players.forEach((s, player) -> {
            if (!playerHasEnoughPokerGameChips(player)) {
                playersToBeRemoved.add(s);
            }
        });
        playersToBeRemoved.forEach(this::removePlayer);
    }

    // Remove a card from the deck to the table
    private void withdrawCardToTable() {
        tableCards.add(CardsUtils.withdrawFromTop(deck));
    }

    // Check if a player is in game
    public boolean playerInGame(String username) {
        return !Objects.isNull(this.players.get(username));
    }

    // Check if the player made fold
    private boolean playerGaveUp(String playerName) {
        return playerFoldList.contains(playerName);
    }

    // Check if the player made all-in
    private boolean playerAllIn(String playerName) {
        return playerAllInList.contains(playerName);
    }

    // Check if is the player turn
    public boolean isPlayerTurn(String playerName) {
        if (playerName.equals(queuePlayOrder.peek())) {
            return true;
        }
        LOG.addAndShowLog("[Game] " + playerName + " needs to wait is turn!");
        return false;
    }

    // Check if is the end round by checking the number of players in the queue
    public boolean isRoundOver() {
        System.out.println("[DEBUG] Number of player: " + queuePlayOrder.size()); // FIXME: To be removed
        return queuePlayOrder.size() <= 1;
    }

    // Check if is game over
    public boolean isGameOver() {
        return players.size() <= 1;
    }

    // Check if the player has enough PCJs to bet the minimum amount
    private boolean playerHasEnoughPokerGameChips(Player player) {
        return player.getWallet().getPokerGameChips() >= bigBlind;
    }

    // Announce the table winner
    public void announceTableWinner() {
        StringBuilder str = new StringBuilder();
        int amount = players.get(getWinner()).getWallet().getPokerGameChips();

        str.append("\n[Game] The table winner was ").append(getWinner()).append(" with ").append(amount).append(" PCJs.");

        if (ETypeOfGame.COMPETITIVE.equals(typeOfGame)) {
            players.get(getWinner()).getWallet().addPokerChips(walletUtils.chipsToPocket(amount));
            str.append("\n[Game] This players as won ").append(walletUtils.chipsToPocket(amount)).append(" PCs.\n");
        }

        LOG.addAndShowLog(str.toString());
    }

    // Fill the queue for the next state
    private void fillQueue() {
        if (queuePlayOrder.size() == 0) {
            // Clear the bets from the last card turn phase
            playerBetsList.clear();
            higherBet = 0;

            // The small blind is the first of this queue, so will always the first to play
            queueDealerOrder.forEach((playerName) -> {
                if (!playerGaveUp(playerName) && !playerAllIn(playerName)) {
                    queuePlayOrder.add(playerName);
                }
            });
        }
    }

    // History of bets to string
    public String printHistoryOfBets() {
        StringBuilder str = new StringBuilder();
        str.append("\n## Bets in the table:");
        playerBetsList.forEach((playerName, betValue) -> {
            str.append("\nPlayer ").append(playerName).append(" has made a bet of ").append(betValue);
        });
        return str.toString();
    }

    // Cards of each player to string
    private void printCardsPerPlayer() {
        System.out.println(CardsUtils.cardsPerPlayerToString(players));
    }

    // Deck to string
    private void printDeck() {
        System.out.println("## Table cards: " + CardsUtils.cardsToString(tableCards.toArray(ICard[]::new)));
    }

    // Players to string
    private String printPlayers(List<String> players) {
        StringBuilder str = new StringBuilder();
        str.append("[");
        players.forEach((s) -> str.append(s).append(", "));
        str.setLength(str.length() - 2); // remove the last 2 characters
        str.append("]");
        return str.toString();
    }

    // Game Info to string
    public String showGameInfo(String gameName) {
        StringBuilder str = new StringBuilder();
        str.append("$$$$$ Game: ").append(gameName).append(" $$$$$");
        str.append("\n## Players in game: ").append(printPlayers(new ArrayList<>(queueDealerOrder)));
        str.append("\n## Table cards: ").append(CardsUtils.cardsToString(tableCards.toArray(ICard[]::new)));
        str.append("\n## Pot value: ").append(pot).append(" PCJs\n");
        str.append(CardsUtils.cardsPerPlayerToString(new ArrayList<>(queuePlayOrder), players));
        str.append("\n## Next turn: ").append(queuePlayOrder.peek());
        str.append("\n## Blinds: ").append("S: ").append(smallBlind).append(" | B: ").append(bigBlind);
        str.append("\n## Higher bet: ").append(higherBet == null ? 0 : higherBet);
        if (playerBetsList.size() > 0) str.append(printHistoryOfBets());
        return str.toString();
    }

    //<editor-fold defaultstate="collapsed" desc=" Gets and Sets ">
    public List<ICard> getDeck() {
        return deck;
    }

    public List<ICard> getTableCards() {
        return tableCards;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
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

    public String getWinner() {
        return ScoreUtils.getLastWinner();
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public ETypeOfGame getTypeOfGame() {
        return typeOfGame;
    }

    public Integer getBigBlind() {
        return bigBlind;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void incrementBigBlind() {
        this.bigBlind += this.increment;
    }

    // Return if is the actual bet is the small blind
    private boolean isSmallBlind() {
        return isSmallBlind;
    }
    //</editor-fold>
}
