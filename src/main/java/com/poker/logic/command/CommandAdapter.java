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
import java.util.stream.Collectors;

public class CommandAdapter {
    private static final Log LOG = Log.getInstance();

    public static boolean addUser(String commandLine, Map<String, Player> playerList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String name = command.get(Constants.NAME_PARAMETER);

        if (name != null) {
            boolean userExists;
            try {
                userExists = DatabaseUtils.playerExistsByName(name);
            } catch (Exception e) {
                return false;
            }

            if (!userExists) {
                boolean userCreated;
                try {
                    userCreated = DatabaseUtils.createPlayer(name);
                } catch (Exception e) {
                    return false;
                }

                if (userCreated) {
                    playerList.put(name, new Player(name));
                    LOG.addLog(commandLine);
                } else {
                    return false;
                }
            }
            return !userExists;
        }

        return false;
    }

    public static boolean loginUser(String commandLine, Map<String, Player> playerList) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String name = command.get(Constants.NAME_PARAMETER);
        if (name != null) {
            Player player;
            try {
                player = DatabaseUtils.getPlayerByName(name);
            } catch (Exception e) {
                return false;
            }

            if (!Objects.isNull(player) && playersInArray(playerList, name)) {
                // Notify other players
                notifyNewLogin(playerList, name);

                playerList.put(name, player);
                LOG.addLog(commandLine);
            }
            return !Objects.isNull(player);
        }

        return false;
    }

    public static void logoutUser(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);
        onlinePlayers.remove(playerName);
    }

    private static void notifyNewLogin(Map<String, Player> onlinePlayers, String name) {
        // notify each online player if some of is friends logged in
        onlinePlayers.forEach((s, player) -> player.getFriends().forEach(friendName -> {
            if (name.equals(friendName))
                System.out.println("[System][To:" + player.getName() + "] The player " + name + " logged in!");
        }));
    }

    private static boolean playersInArray(Map<String, Player> playerList, String name) {
        return playerList.get(name) == null;
    }

    public static void buyChips(String commandLine, Map<String, Player> playerList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String username = command.get(Constants.NAME_PARAMETER);
        String valueStr = command.get(Constants.VALUE_PARAMETER);
        String method = command.get(Constants.METHOD_PARAMETER);
        double value;

        if (username != null && valueStr != null && method != null) {
            value = Double.parseDouble(valueStr);

            if (value != 0 && !username.equals("") && !method.equals("")) {
                Player player = playerList.get(username);
                if (player != null) {
                    EServices service = EServices.fromString(method);
                    if (!service.equals(EServices.UNKNOWN)) {
                        ServiceAdapter serviceAdapter = new ServiceAdapter(service);
                        Wallet playerWallet = player.getWallet();
                        serviceAdapter.buy(value, playerWallet);
                        try {
                            DatabaseUtils.updateWallet(username, playerWallet);
                            LOG.addLog(commandLine);
                        } catch (Exception e) {
                            LOG.addLog("Erro ao atualizar a carteira: " + e.getMessage());
                        }
                    }
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
                System.out.println("[System] " + senderName + " doesn't exist!");
                return;
            } else if (!DatabaseUtils.playerExistsByName(receiverName)) {
                System.out.println("[System] " + receiverName + " doesn't exist!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error trying to get players name!");
            return;
        }

        if (playersInArray(onlinePlayers, senderName)) {
            System.out.println("[System] " + senderName + " is offline!");
            return;
        }

        System.out.println(playersInArray(onlinePlayers, receiverName) ?
                "[System] " + receiverName + " is offline!" :
                onlinePlayers.get(receiverName).getPlayersBlocked().contains(senderName) ?
                        "[System] You can't send messages to this user!" :
                        "[From:" + senderName + "][To:" + receiverName + "] "
                                + command.get(Constants.COMMAND_LAST_DIVISION));
    }

    public static boolean createGame(String commandLine, ApplicationData data, ETypeOfGame typeOfGame) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        Map<String, Player> playerList = data.getOnlinePlayers();

        if (command.size() > 3) {
            String gameName = command.get(Constants.NAME_PARAMETER);
            String creator = command.get(Constants.CREATOR_PARAMETER);
            String fee = command.get(Constants.FEE_PARAMETER);
            String bigBlind = command.get(Constants.BIG_BLIND_PARAMETER);
            String increment = command.get(Constants.INCREMENT_BLIND_PARAMETER);

            if (!gameName.equals("") && !creator.equals("")) {
                Player player = playerList.get(creator);
                if (!Objects.isNull(player)) {
                    GameFactory factory = (GameFactory) FactoryProvider.getFactory(EFactory.GAMES);
                    if (factory == null) {
                        return false;
                    }

                    int bigBlindValue = 0;
                    int incrementValue = 0;
                    int feeValue = 0;

                    // TODO: deal with this minimumAmount, custom for competitive games, and the default value from Constants for friendly games
                    GameCreationData gameCreationData = new GameCreationData(gameName, Constants.FRIENDLY_GAME_MINIMUM_PLAYERS, Constants.GAME_MINIMUM_AMOUNT, typeOfGame, player);

                    if (typeOfGame != ETypeOfGame.FRIENDLY) {
                        if (fee != null) {
                            feeValue = Integer.parseInt(fee);
                            if (feeValue < Constants.COMPETITIVE_DEFAULT_FEE) {
                                System.out.println("Fee can't be smaller than " + Constants.COMPETITIVE_DEFAULT_FEE);
                                return false;
                            }
                            gameCreationData.setFee(feeValue);
                        }

                        if (bigBlind != null) {
                            bigBlindValue = Integer.parseInt(bigBlind);
                            if (bigBlindValue < Constants.DEFAULT_BIG_BLIND) {
                                System.out.println("Big blind can't be smaller than " + Constants.DEFAULT_BIG_BLIND);
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
                    game.addPlayer(player, game.getConvertionTax()); // TODO: adapt this fee on the competitive game

                    return data.addGame(game);
                }
            }
        }
        return false;
    }

    public static boolean joinGame(String commandLine, ApplicationData data) {
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
                        return game.addPlayer(player, game.getMinimumAmount());
                    }
                }
            }
        }

        return false;
    }

    public static void startGame(String commandLine, ApplicationData data) {
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
        System.out.println("[Game] Game not started");
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
        System.out.println("Player added with success");
    }

    public static void blockPlayer(String commandLine, Map<String, Player> onlinePlayers) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        Player player = onlinePlayers.get(command.get(Constants.PLAYER_PARAMETER));
        player.blockPlayer(command.get(Constants.BLOCK_PARAMETER));
        player.removeFriend(command.get(Constants.BLOCK_PARAMETER));
        System.out.println("Player blocked with success");
    }

    public static void showGameInfo(String commandLine, Map<String, Game> games) {
        LOG.addLog(commandLine);
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get("game");
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            String name = entry.getKey();
            Game game = entry.getValue();
            if (name.equals(gameName)) {
                System.out.println(game.showGameInfo(name));
                break;
            }
        }
    }

    public static void getGamesToString(Map<String, Game> gamesList, ETypeOfGame typeOfGame) {
        StringBuilder str = new StringBuilder();
        str.append("NAME\t\tCREATOR\n");
        gamesList.forEach((s, game) -> {
            if (game.getTypeOfGame().equals(typeOfGame)) {
                str.append(game.getGameName()).append("\t\t").append(game.getCreatorName()).append("\n");
            }
        });
        System.out.println(str);
    }

    public static void showPlayerRelationList(String commandLine, Map<String, Player> onlinePlayers, EPlayerRelation relation) {
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
                System.out.println(strOut);
            }
        }
    }

    public static void removeGame(String commandLine, Map<String, Game> gamesList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String gameName = command.get(Constants.NAME_PARAMETER);
        if (gameName != null && gameName.length() > 0) {
            Game game = gamesList.remove(gameName);
            game.getPlayersList().forEach((s, player) -> {
                game.getGameEngine().removePlayer(player.getName());
            });
        }
    }

    public static void editUser(String commandLine, Map<String, Player> onlinePlayers) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);
        String playerNewName = command.get(Constants.NEW_NAME_PARAMETER);

        if (playerNewName != null && playerName != null &&
                playerName.length() > 0 && playerNewName.length() > 0) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                //FIXME: update this method to change all the player references
                player.setName(playerNewName);
            }
        }
    }

    public static void kickUser(String commandLine, Map<String, Player> onlinePlayers, Map<String, Game> gamesList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && playerName.length() > 0) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                boolean containsKey = gamesList.entrySet().stream().anyMatch(stringGameEntry ->
                        stringGameEntry.getValue().getPlayersList().containsKey(playerName));

                //FIXME: make possible to remove a player even if he is in a game

                if (!containsKey) {
                    Player removedPlayer = onlinePlayers.remove(playerName);
                    onlinePlayers.forEach((s, p) -> {
                        p.getFriends().remove(removedPlayer.getName());
                        p.getPlayersBlocked().remove(removedPlayer.getName());
                    });

                    try {
                        DatabaseUtils.removePlayerFromDB(playerName);
                    } catch (Exception e) {
                        System.out.println("Error removing the player from the database");
                    }
                }
            }
        }
    }

    public static void checkUserActivities(String commandLine, Map<String, Player> onlinePlayers) {
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
                        System.out.println("Change one of the cards, they can't be the same");
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
                    System.out.println("The player isn't in the game");
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

            //Validate if in the table
            boolean inTable = game.getDeck().stream().anyMatch(iCard -> {
                var value = iCard.getStringCardValue();
                return value.equals(newCards[0].getStringCardValue()) ||
                        value.equals(newCards[1].getStringCardValue());
            });

            return inTable && !inPlayersHand;
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
    }

    public static void getRankings(Map<String, RankingLine> rankings) {
        if (rankings.size() == 0) {
            try {
                DatabaseUtils.setRankings(rankings);
            } catch (Exception e) {
                System.out.println("Error getting rankings from Database! " + e.getMessage());
            }
        }

        if (rankings.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Player name\t\tNumber of wins\n");
            var entry = rankings.entrySet();
            entry.forEach(stringRankingLineEntry -> {
                RankingLine line = stringRankingLineEntry.getValue();
                stringBuilder.append(line.getPlayerName())
                        .append("\t\t\t\t")
                        .append(line.getWins())
                        .append("\n");
            });
            System.out.println(stringBuilder);
        } else {
            System.out.println("There are no rankings available to show");
        }
    }

    public static void removeRanking(String commandLine, Map<String, RankingLine> rankings) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.PLAYER_PARAMETER);

        if (playerName != null && playerName.length() > 0) {
            if (rankings.size() > 0) {
                RankingLine line = rankings.remove(playerName);
                RankingProvider.getInstance().registerDelete(line);
            }
        }
    }

    public static void addCustomRankings(String commandLine, Map<String, RankingLine> rankings, Map<String, Player> onlinePlayers) {
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
                }
            }
        }
    }

    public static void createTournament(String commandLine, Map<String, Player> onlinePlayers, Map<String, Tournament> tournamentList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.PLAYER_PARAMETER);
        String tourName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && tourName != null) {
            Player player = onlinePlayers.get(playerName);
            if (player != null) {
                if (tournamentList.get(tourName) == null) {
                    tournamentList.put(tourName, new Tournament(tourName, player));
                } else {
                    System.out.println("Tournament already exists!");
                }
            }
        }
    }

    public static void joinTournament(String commandLine, Map<String, Player> onlinePlayers, Map<String, Tournament> tournamentList, Map<String, Game> gamesList) {
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String playerName = command.get(Constants.PLAYER_PARAMETER);
        String tourName = command.get(Constants.NAME_PARAMETER);

        if (playerName != null && tourName != null) {
            Player player = onlinePlayers.get(playerName);
            Tournament tournament = tournamentList.get(tourName);
            if (!playerInGame(gamesList, player)) {
                if (tournament != null && player != null) {
                    tournament.addPlayer(player);
                } else {
                    System.out.println("Error adding the player!");
                }
            }
        }
    }

    private static boolean playerInGame(Map<String, Game> gamesList, Player player) {
        return gamesList.entrySet().stream().anyMatch(stringGameEntry -> stringGameEntry.getValue().getPlayersList().get(player.getName()) != null);
    }

    public static void startTournament(String commandLine, Map<String, Player> onlinePlayers, Map<String, Tournament> tournamentList, Map<String, Game> gamesList) {
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
                }
            } else {
                System.out.println("Error starting the game!");
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
        Map<String, String> command = StringUtils.mapCommand(commandLine);
        String tourName = command.get(Constants.NAME_PARAMETER);

        if (tourName != null) {
            Tournament tournament = tournamentList.get(tourName);
            if (tournament != null) {
                var gamesList = tournament.getGameList();
                Map<String, Player> winners = new HashMap<>();
                boolean goToTheFinal = true;
                for (var game: gamesList) {
                    if (game.getState() == null) {
                        Player player = onlinePlayers.get(game.getLastGameWinner());
                        winners.put(player.getName(), player);
                    } else {
                        goToTheFinal = false;
                    }
                }

                if (goToTheFinal) {
                    tournament.createFinal(winners);
                }
            } else {
                System.out.println("Can't find the tournament!");
            }
        }
    }
}
