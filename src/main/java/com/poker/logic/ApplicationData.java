package com.poker.logic;

import com.poker.logic.game.Game;
import com.poker.logic.game.logic.GameEngine;
import com.poker.model.player.Player;
import com.poker.model.ranking.RankingLine;
import com.poker.model.tournament.Tournament;
import com.poker.utils.CollectionUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ApplicationData {
    private static final ApplicationData applicationData = new ApplicationData();
    private final Map<String, Player> onlinePlayers;
    private final Map<String, Player> registeredPlayers;
    private final Map<String, RankingLine> rankings;
    private Map<String, Game> gamesList;
    private final Map<String, Tournament> tournamentList;

    private ApplicationData() {
        this.rankings = new HashMap<>();
        this.onlinePlayers = new HashMap<>();
        this.gamesList = new HashMap<>();
        registeredPlayers = new HashMap<>();
        tournamentList = new HashMap<>();
    }

    public static ApplicationData getInstance() {
        return applicationData;
    }

    public Map<String, Player> getOnlinePlayers() {
        return onlinePlayers;
    }

    public Map<String, Player> getRegisteredPlayers() {
        return registeredPlayers;
    }

    public Map<String, Game> getGamesList() {
        return gamesList;
    }

    public Map<String, RankingLine> getRankings() {
        return rankings;
    }

    private boolean gameExists(String gameName) {
        return !Objects.isNull(gamesList.get(gameName));
    }

    public boolean addGame(Game game) {
        String gameName = game.getGameName();
        boolean exists = gameExists(gameName);

        if (!exists) {
            gamesList.put(gameName, game);
        }

        return !exists;
    }

    public Game getGame(String gameName) {
        return gamesList.get(gameName);
    }

    public void saveGames(String propertiesFilename) throws IOException {
        try {
            //test
            FileOutputStream f = new FileOutputStream(new File(propertiesFilename));
            ObjectOutputStream o = new ObjectOutputStream(f);
            // Write objects to file
            o.writeObject(this.gamesList);
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Tournament> getTournamentList() {
        return tournamentList;
    }

    public void loadGames(String gameName) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File(gameName));
        ObjectInputStream oi = new ObjectInputStream(fi);
        this.gamesList = (Map<String, Game>) oi.readObject();
    }

    public void updatePlayerName(String oldPlayerName, String newPlayerName) {
        Player player = getOnlinePlayers().get(oldPlayerName);
        if (player != null) {
            if (!player.setName(newPlayerName)) {
                return;
            }
        }

        // Update lists of friends and blocked players
        getRegisteredPlayers().forEach((name, p) -> {
            if (p.getFriends().contains(oldPlayerName)) {
                p.removeFriend(oldPlayerName);
                p.addFriend(newPlayerName);
            }
            if (p.getPlayersBlocked().contains(oldPlayerName)) {
                p.removeBlockedPlayer(oldPlayerName);
                p.blockPlayer(newPlayerName);
            }
        });

        // Update ranks
        RankingLine rankingLine = getRankings().get(oldPlayerName);
        if(rankingLine != null) {
            rankings.put(newPlayerName, rankings.remove(oldPlayerName));
            rankingLine.setPlayerName(newPlayerName);
        }

        // Update games
        updateNameInMapOfGames(getGamesList(), oldPlayerName, newPlayerName);

        // Update games in the tournaments
        getTournamentList().forEach((s, tournament) -> updateNameInListOfGames(tournament.getGameList(), oldPlayerName, newPlayerName));
        System.out.println("[System] The player " + oldPlayerName + " now has a new name: " + newPlayerName);
    }

    private void updateNameInListOfGames(List<Game> listOfGames, String oldName, String newName) {
        listOfGames.forEach((game) -> updateNameInGame(game, oldName, newName));
    }

    private void updateNameInMapOfGames(Map<String, Game> mapOfGames, String oldName, String newName) {
        mapOfGames.forEach((gameName, game) -> updateNameInGame(game, oldName, newName));
    }

    public void updateNameInGame(Game game, String oldName, String newName) {
        GameEngine gameEngine = game.getGameEngine();
        if (game.getPlayersList().get(oldName) != null) {
            game.getPlayersList().put(newName, game.getPlayersList().remove(oldName));
            CollectionUtils.changeNameInCollection(gameEngine.getQueueDealerOrder(), oldName, newName);
            CollectionUtils.changeNameInCollection(gameEngine.getQueuePlayOrder(), oldName, newName);
            CollectionUtils.changeNameInCollection(gameEngine.getPlayerFoldList(), oldName, newName);
            CollectionUtils.changeNameInCollection(gameEngine.getPlayerAllInList(), oldName, newName);
            Map<String, Integer> bets = gameEngine.getPlayerBetsList();
            if (bets.get(oldName) != null) {
                bets.put(newName, bets.remove(oldName));
            }
        }
    }
}
