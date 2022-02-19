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
    private final List<ICard> tableCards;
    private final ETypeOfGame typeOfGame;
    private List<ICard> deck;
    private Player dealer;
    private Integer pot;
    private Integer higherBet;
    private Integer smallBlind; // TODO: apply small and big blind automatically
    private Integer bigBlind;

    public GameEngine(Map<String, Player> players, Integer bigBlind, ETypeOfGame typeOfGame) {
        this.players = players;
        this.queuePlayOrder = new ArrayDeque<>();
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
    }

    // TODO: [TBC] generalize this method for all games or validate in another place TBC
    // Add player to the game and convert PCs into PCJs
    public boolean addPlayer(Player player, int fee) {
        if (!this.playerInGame(player.getName())) {
            //TODO: [TBC] convert PCs into PCJs in a competitive game
            if (Objects.equals(ETypeOfGame.FRIENDLY, typeOfGame)) {
                player.getWallet().setPokerGameChips(walletUtils.chipsToGame(1));
            } else if (Objects.equals(ETypeOfGame.COMPETITIVE, typeOfGame) && player.getWallet().getPokerChips() >= fee) {
                player.getWallet().setPokerGameChips(walletUtils.chipsToGame(fee)); // TODO: [TBC] remove PCs
                player.getWallet().removePokerChips(fee);
            } else {
                return false;
            }
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
        chooseDealer(dealer);
        deck = new CardFactory().createObject(null);
        CardsUtils.distributeCardsPerPlayer(players, deck);
        reset();
    }

    private void reset() {
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

    private Integer getBetsAmount(String playerName, Integer amount) {
        return playerBetsList.get(playerName) == null ? amount : playerBetsList.get(playerName) + amount;
    }

    public boolean bet(String playerName, Integer amount) {
        if (!isPlayerTurn(playerName)) return false;

        int betsAmount = getBetsAmount(playerName, amount);
        if (betsAmount < bigBlind) {
            System.out.println("[Game] The player " + playerName + " needs to bet at least " + bigBlind + " PCJs");
            return false;
        }

        // Compare with the higher bet
        if (higherBet == null) {
            higherBet = betsAmount;
        } else if (betsAmount < higherBet) { // need to call
            System.out.println("[Game] The player " + playerName +
                    " need to bet more! Value to call: " +
                    (playerBetsList.containsKey(playerName) ? higherBet - playerBetsList.get(playerName) : higherBet) +
                    " PCJs");
            return false;
        } else if (betsAmount > higherBet) { // made a raise
            // Check if any player needs to be added to the queue
            if (players.size() - playerFoldList.size() > queuePlayOrder.size()) {
                List<String> playersAux = new ArrayList<>(players.keySet());
                for (Map.Entry<String, Player> e : players.entrySet()) {
                    String key = e.getKey();
                    String playerNameAux = playersAux.remove(0);
                    if (key.equals(playerName)) break;
                    if (!playerFoldList.contains(playerNameAux) && !queuePlayOrder.contains(playerNameAux)) {
                        playersAux.add(playerNameAux);
                    }
                }
                playersAux.removeAll(queuePlayOrder); // FIXME: check if can remove this line
                queuePlayOrder.addAll(playersAux);
            }
            higherBet = betsAmount;
        }

        // Don't have PCJs enough
        if (!players.get(playerName).getWallet().removePokerGameChips(amount)) {
            return false;
        }
        playerBetsList.put(playerName, playerBetsList.containsKey(playerName) ? playerBetsList.get(playerName) + amount : amount);

        addToPot(amount);
        System.out.println("[Game] The player " + playerName + " made a bet of " + amount + " PCJs");
        queuePlayOrder.remove(playerName);
        return queuePlayOrder.size() == 0;
    }

    public boolean check(String playerName) {
        if (!playerName.equals(queuePlayOrder.peek())) {
            System.out.println("[Game] The player " + queuePlayOrder.peek() + " needs to play first!");
            return false;
        }

        if (higherBet != 0 && !higherBet.equals(playerBetsList.get(playerName))) {
            System.out.println("[Game] The player " + playerName + " needs to bet or fold! Value to call: " + higherBet);
            return false;
        }

        System.out.println("[Game] The player " + playerName + " Check!");
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
        clearPlayer();
        startRound();
    }

    private void clearPlayer() {
        List<String> playersToBeRemoved = new ArrayList<>();
        players.forEach((s, player) -> {
            if (!playerHasEnoughPockerGameChips(player)) {
                playersToBeRemoved.add(s);
            }
        });
        playersToBeRemoved.forEach(this::removePlayer);
    }

    private void withdrawCardToTable() {
        tableCards.add(CardsUtils.withdrawFromTop(deck));
    }

    public boolean playerInGame(String username) {
        return !Objects.isNull(this.players.get(username));
    }

    // Check if the player made fold
    private boolean playerGaveUp(String playerName) {
        return playerFoldList.contains(playerName);
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
        System.out.println("[DEBUG] Number of player: " + queuePlayOrder.size());
        return queuePlayOrder.size() == 1;
    }

    // TODO: [TBC] check if is the endgame check the number of players and the minimum bet
    public boolean isGameOver() {
        return players.size() == 1;
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
            });
        }
    }

    private boolean playerHasEnoughPockerGameChips(Player player) {
        return player.getWallet().getPokerGameChips() >= bigBlind;
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

    public ETypeOfGame getTypeOfGame() {
        return typeOfGame;
    }

    //</editor-fold>
}
