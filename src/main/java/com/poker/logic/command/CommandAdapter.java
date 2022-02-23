package com.poker.logic.command;

import com.poker.logic.ApplicationData;
import com.poker.logic.factory.EFactory;
import com.poker.logic.factory.FactoryProvider;
import com.poker.logic.factory.game.GameCreationData;
import com.poker.logic.factory.game.GameFactory;
import com.poker.logic.game.ETypeOfGame;
import com.poker.logic.game.Game;
import com.poker.model.card.ICard;
import com.poker.model.constants.Constants;
import com.poker.model.filter.FilterDecorator;
import com.poker.model.filter.Log;
import com.poker.model.filter.UserFilter;
import com.poker.model.payment.EServices;
import com.poker.model.payment.ServiceAdapter;
import com.poker.model.player.EPlayerRelation;
import com.poker.model.player.Player;
import com.poker.model.ranking.RankingLine;
import com.poker.model.ranking.RankingProvider;
import com.poker.model.tournament.Tournament;
import com.poker.model.wallet.Wallet;
import com.poker.utils.CardsUtils;
import com.poker.utils.DatabaseUtils;
import com.poker.utils.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CommandAdapter {
    private static final Log LOG = Log.getInstance();

    public static boolean addUser(String commandLine, Map<String, Player> playerList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String name = command.get(Constants.NAME_PARAMETER);
        int amount = Integer.parseInt(command.get(Constants.AMOUNT_PARAMETER) != null ? command.get(Constants.AMOUNT_PARAMETER) : "0");

        if (name != null) {
            boolean userExists;
            try {
                userExists = DatabaseUtils.playerExistsByName(name);
            } catch (Exception e) {
                System.out.println("[Register] Error while registering the user in database " + e.getMessage());
                return false;
            }

            if (!userExists) {
                boolean userCreated;
                try {
                    userCreated = DatabaseUtils.createPlayer(name, amount);
                } catch (Exception e) {
                    System.out.println("[Register] Error while registering the user in database " + e.getMessage());
                    return false;
                }

                if (userCreated) {
                    playerList.put(name, new Player(name, new Wallet(amount, 0, 0)));
                    LOG.addAndShowLog("[Register] User registered with success");
                } else {
                    return false;
                }
            } else {
                LOG.addAndShowLog("[Register] User already exists in the database!");
            }
            return !userExists;
        }

        return false;
    }

    public static boolean loginUser(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String name = command.get(Constants.NAME_PARAMETER);
        if (name != null) {
            Player player;
            try {
                player = DatabaseUtils.getPlayerByName(name);
                if (player == null) {
                    LOG.addAndShowLog("[Login] The player " + name + " needs to registered first before logging in!");
                }
            } catch (Exception e) {
                System.out.println("[Login] Error while logging the user in database " + e.getMessage());
                return false;
            }

            if (!Objects.isNull(player) && !playersInArray(onlinePlayers, name)) {
                // Notify other players
                notifyNewLogin(onlinePlayers, name);

                onlinePlayers.put(name, player);
                LOG.addAndShowLog("[Login] User logged in with success!");
            }
            return !Objects.isNull(player);
        }
        return false;
    }

    public static void logoutUser(String commandLine, ApplicationData applicationData) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);
        Player player = applicationData.getOnlinePlayers().get(playerName);
        if (player != null) {
            if (!playerInGame(applicationData.getGamesList(), player)) {
                applicationData.getOnlinePlayers().remove(playerName);
                LOG.addAndShowLog("[System] User logged out with success!");
                return;
            }
        }
        LOG.addAndShowLog("[System] Logout was not successful!");
    }

    private static void notifyNewLogin(Map<String, Player> onlinePlayers, String name) {
        // notify each online player if some of is friends logged in
        onlinePlayers.forEach((s, player) -> player.getFriends().forEach(friendName -> {
            if (name.equals(friendName))
                LOG.addAndShowLog("[System][To:" + player.getName() + "] The player " + name + " logged in!");
        }));
    }

    private static boolean playersInArray(Map<String, Player> playerList, String name) {
        return playerList.get(name) != null;
    }

    public static void buyChips(String commandLine, Map<String, Player> playerList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String username = command.get(Constants.NAME_PARAMETER);
        String valueStr = command.get(Constants.VALUE_PARAMETER);
        String method = command.get(Constants.METHOD_PARAMETER);
        double amount;

        if (username != null && valueStr != null && method != null) {
            amount = Double.parseDouble(valueStr);

            if (amount != 0 && !username.equals("") && !method.equals("")) {
                Player player = playerList.get(username);
                if (player != null) {
                    EServices service = EServices.fromString(method);
                    if (!service.equals(EServices.UNKNOWN)) {
                        ServiceAdapter serviceAdapter = new ServiceAdapter(service);
                        Wallet playerWallet = player.getWallet();
                        if (playerWallet.getAmount() >= amount) {
                            serviceAdapter.buy(amount, playerWallet);
                            try {
                                DatabaseUtils.updateWallet(username, playerWallet);
                                LOG.addAndShowLog("[System] Chips bought with success!");
                            } catch (Exception e) {
                                LOG.addLog("[System] Error while updating the wallet: " + e.getMessage());
                            }
                        } else {
                            LOG.addAndShowLog("[System] Player has no money to buy that value of chips.");
                        }
                    }
                    LOG.addAndShowLog("[System] The player " + player.getName() + " now has " + player.getWallet().getPokerChips() + " PCs");
                }
            }
        }
    }

    public static void sendMessage(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String senderName = command.get(Constants.FROM_PARAMETER);
        String receiverName = command.get(Constants.TO_PARAMETER);

        // check if the players exists on the BD
        try {
            if (!DatabaseUtils.playerExistsByName(senderName)) {
                LOG.addAndShowLog("[System] " + senderName + " doesn't exist!");
                return;
            } else if (!DatabaseUtils.playerExistsByName(receiverName)) {
                LOG.addAndShowLog("[System] " + receiverName + " doesn't exist!");
                return;
            }
        } catch (Exception e) {
            System.out.println("[System] Error trying to get players name!");
            return;
        }

        if (!playersInArray(onlinePlayers, senderName)) {
            LOG.addAndShowLog("[System] " + senderName + " is offline!");
            return;
        }

        LOG.addAndShowLog(!playersInArray(onlinePlayers, receiverName) ?
                "[System] " + receiverName + " is offline!" :
                onlinePlayers.get(receiverName).getPlayersBlocked().contains(senderName) ?
                        "[System] You can't send messages to this user!" :
                        "[From:" + senderName + "][To:" + receiverName + "] "
                                + command.get(Constants.COMMAND_LAST_DIVISION));
    }

    public static boolean createGame(String commandLine, ApplicationData data, ETypeOfGame typeOfGame) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        Map<String, Player> playerList = data.getOnlinePlayers();

        if (command.size() > 3) {
            String gameName = command.get(Constants.NAME_PARAMETER);
            String creator = command.get(Constants.CREATOR_PARAMETER);
            String fee = command.get(Constants.FEE_PARAMETER);
            String bigBlind = command.get(Constants.BIG_BLIND_PARAMETER);
            String increment = command.get(Constants.INCREMENT_BLIND_PARAMETER);

            // Remove a last game with this name
            if (data.getGamesList().get(gameName) != null && data.getGamesList().get(gameName).isNull()) {
                data.getGamesList().remove(gameName);
            }

            if (!gameName.equals("") && !creator.equals("")) {
                Player player = playerList.get(creator);
                if (!Objects.isNull(player)) {
                    GameFactory factory = (GameFactory) FactoryProvider.getFactory(EFactory.GAMES);
                    if (factory == null) {
                        System.out.println("[Game] Error creating the factory");
                        return false;
                    }

                    int bigBlindValue = 0;
                    int incrementValue = 0;
                    int feeValue = 0;

                    GameCreationData gameCreationData = new GameCreationData(gameName, Constants.FRIENDLY_GAME_PLAYERS, Constants.GAME_MINIMUM_AMOUNT, typeOfGame, player);

                    if (typeOfGame != ETypeOfGame.FRIENDLY) {
                        if (fee != null) {
                            feeValue = Integer.parseInt(fee);
                            if (feeValue < Constants.COMPETITIVE_DEFAULT_FEE) {
                                LOG.addAndShowLog("[Game] Fee can't be smaller than " + Constants.COMPETITIVE_DEFAULT_FEE);
                                return false;
                            }
                            gameCreationData.setFee(feeValue);
                        }

                        if (bigBlind != null) {
                            bigBlindValue = Integer.parseInt(bigBlind);
                            if (bigBlindValue < Constants.DEFAULT_BIG_BLIND) {
                                LOG.addAndShowLog("[Game] Big blind can't be smaller than " + Constants.DEFAULT_BIG_BLIND);
                                return false;
                            }
                            gameCreationData.setBigBlind(bigBlindValue);
                        }

                        if (increment != null) {
                            incrementValue = Integer.parseInt(increment);
                            if (incrementValue > 0) {
                                gameCreationData.setIncrement(incrementValue);
                            }
                        }
                    }

                    Game game = factory.createObject(gameCreationData);

                    if (ETypeOfGame.COMPETITIVE.equals(game.getTypeOfGame()) && player.getWallet().getPokerChips() < gameCreationData.getFee()) {
                        LOG.addAndShowLog("[Game] The player " + player.getName() + " needs at least " + gameCreationData.getFee() + " PCs to play in this game");
                        return false;
                    }
                    game.addPlayer(player);
                    return data.addGame(game);
                }
            }
        }
        return false;
    }

    public static boolean joinGame(String commandLine, ApplicationData data) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER);
        String playerName = command.get(Constants.PLAYER_PARAMETER);

        Map<String, Player> playerList = data.getOnlinePlayers();
        Map<String, Game> gameList = data.getGamesList();

        if (gameList != null && playerName != null) {
            if (!gameName.equals("")) {
                Game game = gameList.get(gameName);
                if (!Objects.isNull(game) && !playerName.equals("")) {
                    Player player = playerList.get(playerName);
                    if (!Objects.isNull(player)) {
                        AtomicBoolean playerInGame = new AtomicBoolean(false);
                        gameList.forEach((s, game1) -> {
                            if (game1.playerInGame(playerName)) {
                                playerInGame.set(true);
                            }
                        });
                        if (!playerInGame.get()) {
                            LOG.addAndShowLog("[Game] The player has joined the game!");
                            return game.addPlayer(player);
                        } else {
                            LOG.addAndShowLog("[Game] The player " + playerName + " already are in another game");
                        }
                    }
                } else {
                    LOG.addAndShowLog("[Game] The game " + gameName + " doesn't exists!");
                }
            }
        }
        return false;
    }

    public static void startGame(String commandLine, ApplicationData data) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER);
        String playerName = command.get(Constants.PLAYER_PARAMETER);

        Map<String, Player> playerList = data.getOnlinePlayers();
        Map<String, Game> gameList = data.getGamesList();

        if (gameName != null && playerName != null && !gameName.equals("")) {
            Game game = gameList.get(gameName);
            if (!Objects.isNull(game) && !playerName.equals("")) {
                Player player = playerList.get(playerName);
                if (!Objects.isNull(player)) {
                    game.startGame(player);
                    return;
                }
            }
        }
        LOG.addAndShowLog("[Game] Game not started");
    }

    public static void getOnlinePlayersToString(Map<String, Player> onlinePlayers) {
        StringBuilder str = new StringBuilder("# Online players:");
        onlinePlayers.forEach((s, player) -> str.append("\n ").append(s));
        System.out.println(str);
    }

    public static void addFriend(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        Player player = onlinePlayers.get(command.get(Constants.PLAYER_PARAMETER));
        player.addFriend(command.get(Constants.ADD_PARAMETER));
        player.removeBlockedPlayer(command.get(Constants.ADD_PARAMETER));
        LOG.addAndShowLog("[Add Friend] Player added with success");
    }

    public static void blockPlayer(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        Player player = onlinePlayers.get(command.get(Constants.PLAYER_PARAMETER));
        player.blockPlayer(command.get(Constants.BLOCK_PARAMETER));
        player.removeFriend(command.get(Constants.BLOCK_PARAMETER));
        LOG.addAndShowLog("[Block Player] Player blocked with success");
    }

    public static void showGameInfo(String commandLine, Map<String, Game> games) {
        LOG.addLog(commandLine);
        boolean gameExists = false;
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.GAME_PARAMETER);
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            String name = entry.getKey();
            Game game = entry.getValue();
            if (name.equals(gameName) && !game.isNull()) {
                System.out.println(game.showGameInfo(name));
                gameExists = true;
                break;
            }
        }
        if (!gameExists) {
            LOG.addAndShowLog("[Game] The game with the name " + gameName + " doesn't exists!");
        }
    }

    public static void getGamesToString(Map<String, Game> gamesList, ETypeOfGame typeOfGame) {
        StringBuilder str = new StringBuilder();
        AtomicReference<Boolean> existsGames = new AtomicReference<>(false);
        str.append("NAME\t\tCREATOR");
        List<String> gamesToRemoved = new ArrayList<>();
        gamesList.forEach((s, game) -> {
            if (game.getTypeOfGame().equals(typeOfGame) && !game.isNull()) {
                str.append("\n").append(game.getGameName()).append("\t\t").append(game.getCreatorName());
                existsGames.set(true);
            } else if (game.isNull()) {
                gamesToRemoved.add(s);
            }
        });
        gamesToRemoved.forEach(gamesList::remove);
        LOG.addAndShowLog(existsGames.get() ? str.toString() : "[System] There are no " + typeOfGame.toString().toLowerCase() + " games!");
    }

    public static void showPlayerRelationList(String commandLine, Map<String, Player> onlinePlayers, EPlayerRelation relation) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String username = command.get(Constants.NAME_PARAMETER);

        if (username != null && username.length() > 0) {
            Player player = onlinePlayers.get(username);
            if (player != null) {
                List<String> relatedPlayers = null;
                switch (relation) {
                    case FRIENDS:
                        relatedPlayers = player.getFriends();
                        break;
                    case BLOCKEDS:
                        relatedPlayers = player.getPlayersBlocked();
                        break;
                }
                StringBuilder strOut = new StringBuilder();
                strOut.append("List of ").append(relation).append(" of ").append(username).append(":\n");
                relatedPlayers.forEach(s -> strOut.append("> ").append(s).append("\n"));
                LOG.addAndShowLog(strOut.toString());
            }
        }
    }

    public static void removeGame(String commandLine, Map<String, Game> gamesList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER);
        if (gameName != null && gameName.length() > 0) {
            Game game = gamesList.remove(gameName);
            game.getPlayersList().forEach((s, player) -> game.getGameEngine().removePlayer(player.getName()));
            LOG.addAndShowLog("[Game] The game " + gameName + " has been removed with success!");
        }
    }

    public static void editUser(String commandLine, ApplicationData applicationData) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);
        String playerNewName = command.get(Constants.NEW_NAME_PARAMETER);

        if (playerNewName != null && playerName != null &&
                playerName.length() > 0 && playerNewName.length() > 0) {
            applicationData.updatePlayerName(playerName, playerNewName);
        } else {
            LOG.addAndShowLog("[System] Names invalid!");
        }
    }

    public static void kickUser(String commandLine, Map<String, Player> onlinePlayers, Map<String, Game> gamesList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && playerName.length() > 0) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                boolean containsKey = gamesList.entrySet().stream().anyMatch(stringGameEntry ->
                        stringGameEntry.getValue().getPlayersList().containsKey(playerName));

                if (!containsKey) {
                    Player removedPlayer = onlinePlayers.remove(playerName);
                    onlinePlayers.forEach((s, p) -> {
                        p.getFriends().remove(removedPlayer.getName());
                        p.getPlayersBlocked().remove(removedPlayer.getName());
                    });

                    try {
                        DatabaseUtils.removePlayerFromDB(playerName);
                        LOG.addAndShowLog("[Admin] The player " + playerName + " has been kicked from the application!");
                    } catch (Exception e) {
                        System.out.println("[Admin] Error removing the player from the database");
                    }
                } else {
                    LOG.addAndShowLog("[Admin] The player " + playerName + " cannot be kicked from the application because is in game!");
                }
            }
        }
    }

    public static void checkUserActivities(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && playerName.length() > 0) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                FilterDecorator search = new UserFilter(LOG, playerName);
                System.out.println("User Activities:\n" + search.filter());
            }
        }
    }

    public static void addCardsToUser(String commandLine, Map<String, Player> onlinePlayers, Map<String, Game> gamesList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.PLAYER_PARAMETER);
        String cardOne = command.get(Constants.CARD_ONE_PARAMETER);
        String cardTwo = command.get(Constants.CARD_TWO_PARAMETER);

        if (playerName != null && cardOne != null && cardTwo != null) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                ICard[] newCards = new ICard[2];
                newCards[0] = CardsUtils.convertCardFromString(cardOne);
                newCards[1] = CardsUtils.convertCardFromString(cardTwo);

                if (newCards[0] != null && newCards[1] != null) {
                    if (newCards[0].getStringCardValue().equals(newCards[1].getStringCardValue())) {
                        LOG.addAndShowLog("[System] Change one of the cards, they can't be the same");
                        return;
                    }
                }

                var playerGame = gamesList
                        .values()
                        .stream()
                        .filter(v -> v.getPlayersList().containsKey(playerName))
                        .collect(Collectors.toList());
                Game game;
                if (playerGame.size() == 1) {
                    game = playerGame.get(0);
                } else {
                    LOG.addAndShowLog("[System] The player isn't in the game");
                    return;
                }

                if (cardsAreValid(newCards, game, player)) {
                    replaceCards(newCards, player.getGameCards(), game, player);
                }
            }
        }
    }

    private static boolean cardsAreValid(ICard[] newCards, Game game, Player player) {
        if (newCards[0] != null && newCards[1] != null
                && player.getGameCards()[0] != null && player.getGameCards()[0] != null) {
            //Validate Cards in player hands
            boolean inPlayersHand = game.getPlayersList().entrySet().stream().anyMatch(stringPlayerEntry -> {
                var gameCards = stringPlayerEntry.getValue().getGameCards();

                if (stringPlayerEntry.getKey().equals(player.getName())) {
                    return false;
                }

                if (gameCards == null) {
                    return false;
                }

                return Arrays.stream(gameCards).anyMatch(iCard ->
                        iCard.getStringCardValue().equals(newCards[0].getStringCardValue()) ||
                                iCard.getStringCardValue().equals(newCards[1].getStringCardValue()));
            });

            //Validate if in the deck
            boolean inDeck = game.getDeck().stream().filter(iCard -> {
                var value = iCard.getStringCardValue();
                return value.equals(newCards[0].getStringCardValue()) ||
                        value.equals(newCards[1].getStringCardValue());
            }).count() == 2;

            return inDeck && !inPlayersHand;
        }
        return true;
    }

    private static void replaceCards(ICard[] newCards, ICard[] cardsToDeck, Game game, Player player) {
        if (cardsToDeck[0] != null && cardsToDeck[1] != null && game.getDeck() != null && game.getDeck().size() > 0) {
            game.getDeck().removeIf(card -> card.getStringCardValue().equals(newCards[0].getStringCardValue()) ||
                    card.getStringCardValue().equals(newCards[1].getStringCardValue()));

            if (!cardsToDeck[0].getStringCardValue().equals(newCards[0].getStringCardValue()) &&
                    !cardsToDeck[0].getStringCardValue().equals(newCards[1].getStringCardValue())) {
                game.getDeck().add(cardsToDeck[0]);
            }
            if (!cardsToDeck[1].getStringCardValue().equals(newCards[0].getStringCardValue()) &&
                    !cardsToDeck[1].getStringCardValue().equals(newCards[1].getStringCardValue())) {
                game.getDeck().add(cardsToDeck[1]);
            }
        }

        player.getGameCards()[0] = newCards[0];
        player.getGameCards()[1] = newCards[1];
        System.out.println("[Game] Cards added to the player hand!");
    }

    public static void getRankings(Map<String, RankingLine> rankings) {
        if (rankings.size() == 0) {
            try {
                DatabaseUtils.setRankings(rankings);
                System.out.println("[System] Rankings retrieved from the database!");
            } catch (Exception e) {
                System.out.println("[System] Error getting rankings from Database! " + e.getMessage());
            }
        }

        if (rankings.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Player name\t\tNumber of wins\n");
            var entry = rankings.entrySet();
            entry.forEach(stringRankingLineEntry -> {
                RankingLine line = stringRankingLineEntry.getValue();
                stringBuilder.append(line.getPlayerName())
                        .append(line.getPlayerName().length() > 4 ? "\t\t\t" : line.getPlayerName().length() > 8 ? "\t\t" :  "\t\t\t\t")
                        .append(line.getWins())
                        .append("\n");
            });
            System.out.println(stringBuilder);
        } else {
            System.out.println("[System] There are no rankings available to show");
        }
    }

    public static void removeRanking(String commandLine, Map<String, RankingLine> rankings) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.PLAYER_PARAMETER);

        if (playerName != null && playerName.length() > 0) {
            if (rankings.size() > 0) {
                RankingLine line = rankings.remove(playerName);
                RankingProvider.getInstance().registerDelete(line);
                LOG.addAndShowLog("[System] Ranking removed from the system!");
            }
        }
    }

    public static void addCustomRankings(String commandLine, Map<String, RankingLine> rankings, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.PLAYER_PARAMETER);
        String wins = command.get(Constants.WINS_TWO_PARAMETER);

        if (wins != null && playerName != null) {
            int winsValue = Integer.parseInt(wins);
            if (rankings != null) {
                Player player = onlinePlayers.get(playerName);
                if (player != null) {
                    RankingLine rankingLine = rankings.get(playerName);
                    if (rankingLine == null) {
                        rankingLine = new RankingLine(playerName, winsValue);
                        rankings.put(playerName, rankingLine);
                        RankingProvider.getInstance().registerNew(rankingLine);
                    } else {
                        rankingLine.setWins(winsValue);
                    }
                    LOG.addAndShowLog("[System] Ranking added with success!");
                }
            }
        }
    }

    public static void createTournament(String commandLine, Map<String, Player> onlinePlayers, Map<String, Tournament> tournamentList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.PLAYER_PARAMETER);
        String tourName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && tourName != null) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                if (tournamentList.get(tourName) == null) {
                    tournamentList.put(tourName, new Tournament(tourName, player));
                    LOG.addAndShowLog("[Tournament] Tournament created with success!");
                } else {
                    LOG.addAndShowLog("[Tournament] Tournament already exists!");
                }
            }
        }
    }

    public static void joinTournament(String commandLine, Map<String, Player> onlinePlayers, Map<String, Tournament> tournamentList, Map<String, Game> gamesList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.PLAYER_PARAMETER);
        String tourName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && tourName != null) {
            Player player = onlinePlayers.get(playerName);
            Tournament tournament = tournamentList.get(tourName);
            if (!playerInGame(gamesList, player)) {
                if (tournament != null && player != null) {
                    tournament.addPlayer(player);
                    LOG.addAndShowLog("[Tournament] Player joined the tournament wait room!");
                } else {
                    LOG.addAndShowLog("[Tournament] Error adding the player!");
                }
            }
        }
    }

    private static boolean playerInGame(Map<String, Game> gamesList, Player player) {
        return gamesList.entrySet().stream().anyMatch(stringGameEntry -> stringGameEntry.getValue().getPlayersList().get(player.getName()) != null);
    }

    public static void startTournament(String commandLine, Map<String, Player> onlinePlayers, Map<String, Tournament> tournamentList, Map<String, Game> gamesList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.PLAYER_PARAMETER);
        String tourName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && tourName != null) {
            Player player = onlinePlayers.get(playerName);
            Tournament tournament = tournamentList.get(tourName);

            if (player != null && tournament != null) {
                tournament.startTournament();
                for (var game : tournament.getGameList()) {
                    gamesList.put(game.getGameName(), game);
                    LOG.addAndShowLog("[Tournament] The tournament has started!! Good Luck!!!!");
                }
            } else {
                LOG.addAndShowLog("[Tournament] Error starting the game!");
            }
        }
    }

    public static void showTournamentsInfo(Map<String, Tournament> tournamentList) {
        StringBuilder str = new StringBuilder();
        str.append("Tournament Name\t\tNumber of Players\n");
        tournamentList.forEach((s, tournament) -> str.append(tournament.getTournamentName())
                .append("\t\t\t\t")
                .append(tournament.getPlayersMap().size())
                .append("\n"));
        System.out.println(str);
    }

    public static void startFinalGame(String commandLine, Map<String, Tournament> tournamentList, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String tourName = command.get(Constants.NAME_PARAMETER);

        if (tourName != null) {
            Tournament tournament = tournamentList.get(tourName);
            if (tournament != null) {
                var gamesList = tournament.getGameList();
                Map<String, Player> winners = new HashMap<>();
                boolean goToTheFinal = true;
                for (var game : gamesList) {
                    if (game.getState() == null) {
                        Player player = onlinePlayers.get(game.getLastGameWinner());
                        winners.put(player.getName(), player);
                    } else {
                        goToTheFinal = false;
                    }
                }

                if (goToTheFinal) {
                    tournament.createFinal(winners);
                    LOG.addAndShowLog("[Tournament] Final game created!");
                }
            } else {
                LOG.addAndShowLog("[Tournament] Can't find the tournament!");
            }
        }
    }

    public static void listPlayersDetails(Map<String, Player> onlinePlayers) {
        StringBuilder str = new StringBuilder();

        onlinePlayers.forEach((s, player) -> {
            str.append("######\n")
                    .append("Player Name: ").append(s).append("\n")
                    .append("Wallet: ").append(player.getWallet().toString()).append("\n");
        });

        System.out.println(str);
    }

    public static void transferMoney(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);
        double amount = Double.parseDouble(command.get(Constants.VALUE_PARAMETER));

        Player player = onlinePlayers.get(playerName);
        ServiceAdapter serviceAdapter = new ServiceAdapter(EServices.PAYPAL);
        if (player != null && serviceAdapter.transferMoney(player.getWallet(), amount)) {
            player.getWallet().addAmount(amount);
            try {
                DatabaseUtils.updateWallet(playerName, player.getWallet());
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOG.addAndShowLog("[Game] The player " + playerName + " received " + amount + "€ on his in-game account");
            return;
        }
        LOG.addAndShowLog("[Game] Please login with the player " + playerName + " to add in-game cash!");
    }

    public static void kickFromGame(String commandLine, ApplicationData applicationData) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        LOG.addLog(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);
        String gameName = command.get(Constants.GAME_PARAMETER);

        Player player = applicationData.getOnlinePlayers().get(playerName);
        Game game = applicationData.getGame(gameName);
        if (player != null && game != null) {
            if (game.removePlayer(playerName)) {
                LOG.addAndShowLog("[Game] The player " + playerName + " has been removed from the game " + gameName);
                return;
            }
        }
        LOG.addAndShowLog("[Game] The name of the " + (player == null ? "player" : "game") + " are invalid!");
    }

    public static void setTableCards(String commandLine, Map<String, Game> gamesList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.GAME_PARAMETER);
        String cardOne = command.get(Constants.CARD_ONE_PARAMETER);
        String cardTwo = command.get(Constants.CARD_TWO_PARAMETER);
        String cardThree = command.get(Constants.CARD_THR_PARAMETER);
        String cardFour = command.get(Constants.CARD_FOU_PARAMETER);
        String cardFive = command.get(Constants.CARD_FIV_PARAMETER);

        Game game = gamesList.get(gameName);
        String lastCardKey = (String) command.keySet().toArray()[command.size() - 2];

        int numberOfCards = Integer.parseInt(lastCardKey.replace("c", ""));
        if (game != null && numberOfCards == game.getTableCards().size() && numberOfCards >= 3) {
            List<ICard> deck = game.getDeck();
            List<ICard> newCards = new ArrayList<>();
            switch (lastCardKey) {
                case Constants.CARD_FIV_PARAMETER: newCards.add(CardsUtils.convertCardFromString(cardFive));
                case Constants.CARD_FOU_PARAMETER: newCards.add(CardsUtils.convertCardFromString(cardFour));
                case Constants.CARD_THR_PARAMETER:
                    newCards.add(CardsUtils.convertCardFromString(cardThree));
                    newCards.add(CardsUtils.convertCardFromString(cardTwo));
                    newCards.add(CardsUtils.convertCardFromString(cardOne));
            }

            // Validate received cards
            for (ICard iCard : newCards) {
                if (iCard == null) {
                    LOG.addAndShowLog("[Game] Some card are invalid!");
                    return;
                }
            }

            // Get player's cards
            List<ICard> cardsInPlayersHands = new ArrayList<>();
            game.getPlayersList().forEach((s, player) -> cardsInPlayersHands.addAll(List.of(player.getGameCards())));

            // Check if any of the cards are in the player's hands
            for (ICard playerCard : cardsInPlayersHands) {
                for (ICard newCard : newCards) {
                    if (playerCard.getStringCardValue().equals(newCard.getStringCardValue())) {
                        LOG.addAndShowLog("[Game] You can't set cards to the table that are in the players hand!");
                        return;
                    }
                }
            }
            deck.addAll(game.getTableCards());

            // Remove cards from deck and set them on the table
            List<ICard> toBeRemoveFromDeck = new ArrayList<>();
            deck.forEach(deckCard -> {
                newCards.forEach(newCard -> {
                    if (deckCard.getStringCardValue().equals(newCard.getStringCardValue())) {
                        toBeRemoveFromDeck.add(deckCard);
                    }
                });
            });
            deck.removeAll(toBeRemoveFromDeck);
            game.setTableCards(toBeRemoveFromDeck);

            LOG.addAndShowLog("[Game] The game " + gameName + " now have a new set of cards in the table!");
        } else {
            LOG.addAndShowLog("[Game] The number of cards in the table and in the input don't match!");
        }
    }

    // leaveGame name=game1 player=ana
    public static void leaveGame(String commandLine, Map<String, Game> gamesList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER);
        String playerName = command.get(Constants.PLAYER_PARAMETER);

        Game game = gamesList.get(gameName);
        if (game != null && game.playerInGame(playerName) && !game.isNull()) {
            if (game.leaveGame(playerName)) {
                LOG.addAndShowLog("[Game] The player " + playerName + " left the game " + gameName + "!");
            }
        } else {
            LOG.addAndShowLog("[Game] " + (game == null ?
                    "The game name are invalid!" :
                    !game.playerInGame(playerName) ?
                            "The player " + playerName + " aren't in this game!" :
                            game.isNull() ?
                                    "The selected game is over!" :
                                    "Something went wrong! Please validate the input!"));
        }
    }
}
