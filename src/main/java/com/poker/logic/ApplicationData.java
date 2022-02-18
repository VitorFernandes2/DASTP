package com.poker.logic;

import com.poker.logic.game.Game;
import com.poker.model.player.Player;
import com.poker.model.ranking.RankingLine;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApplicationData {
    private static final ApplicationData applicationData = new ApplicationData();
    private final Map<String, Player> onlinePlayers;
    private final Map<String, Player> registeredPlayers;
    private final Map<String, RankingLine> rankings;
    private Map<String, Game> gamesList;

    private ApplicationData() {
        this.rankings = new HashMap<>();
        this.onlinePlayers = new HashMap<>();
        this.gamesList = new HashMap<>();
        registeredPlayers = new HashMap<>();
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

    public void loadGames(String gameName) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(new File(gameName));
        ObjectInputStream oi = new ObjectInputStream(fi);
        this.gamesList = (Map<String, Game>) oi.readObject();
    }
}
