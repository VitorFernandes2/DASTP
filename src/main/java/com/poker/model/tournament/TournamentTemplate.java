package com.poker.model.tournament;

import com.poker.logic.game.Game;
import com.poker.model.player.Player;
import com.poker.model.ranking.RankingLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TournamentTemplate {
    protected final String tournamentName;
    protected final List<Game> gameList;
    protected final Map<String, Player> playersMap;
    protected final Player creator;

    protected TournamentTemplate(String tournamentName, Player creator) {
        this.tournamentName = tournamentName;
        this.creator = creator;
        gameList = new ArrayList<>();
        playersMap = new HashMap<>();
    }

    public void addPlayer(Player player) {
        playersMap.put(player.getName(), player);
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public Map<String, Player> getPlayersMap() {
        return playersMap;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public abstract void startTournament(Map<String, RankingLine> rankings);
    public abstract void createFinal(Map<String, Player> winners, Map<String, RankingLine> rankings);
}
