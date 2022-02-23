package com.poker.model.tournament;

import com.poker.logic.factory.EFactory;
import com.poker.logic.factory.FactoryProvider;
import com.poker.logic.factory.game.GameCreationData;
import com.poker.logic.factory.game.GameFactory;
import com.poker.logic.game.ETypeOfGame;
import com.poker.model.constants.Constants;
import com.poker.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tournament extends TournamentTemplate {

    public Tournament(String tournamentName, Player creator) {
        super(tournamentName, creator);
    }

    @Override
    public void startTournament() {
        if (super.playersMap.size() >= Constants.TOURNAMENT_MINIMUM_PLAYERS) {
            GameFactory factory = (GameFactory) FactoryProvider.getFactory(EFactory.GAMES);
            if (factory == null) {
                return;
            }
            List<GameCreationData> gameCreationDataList = new ArrayList<>();
            gameCreationDataList.add(new GameCreationData("game1" + tournamentName,
                    Constants.GAME_MINIMUM_PLAYERS,
                    Constants.GAME_MINIMUM_AMOUNT,
                    ETypeOfGame.COMPETITIVE,
                    creator));
            gameCreationDataList.add(new GameCreationData("game2" + tournamentName,
                    Constants.GAME_MINIMUM_PLAYERS,
                    Constants.GAME_MINIMUM_AMOUNT,
                    ETypeOfGame.COMPETITIVE,
                    creator));
            gameCreationDataList.add(new GameCreationData("game3" + tournamentName,
                    Constants.GAME_MINIMUM_PLAYERS,
                    Constants.GAME_MINIMUM_AMOUNT,
                    ETypeOfGame.COMPETITIVE,
                    creator));

            addPlayersToGames(gameCreationDataList, playersMap);

            for (var gameCreationData: gameCreationDataList) {
                gameList.add(factory.createObject(gameCreationData));
                gameList.get(gameList.size()-1).startGame(creator);
            }
        }
    }

    @Override
    public void createFinal(Map<String, Player> winners) {
        GameCreationData gameCreationData = new GameCreationData("game3" + tournamentName,
                Constants.GAME_MINIMUM_PLAYERS,
                Constants.GAME_MINIMUM_AMOUNT,
                ETypeOfGame.COMPETITIVE,
                creator);

        gameCreationData.getPlayers().putAll(winners);

        GameFactory factory = (GameFactory) FactoryProvider.getFactory(EFactory.GAMES);
        if (factory == null) {
            return;
        }
        gameList.add(factory.createObject(gameCreationData));
        gameList.get(gameList.size()-1).startGame(creator);
    }

    private void addPlayersToGames(List<GameCreationData> gameCreationDataList, Map<String, Player> playersMap) {
        int k = 0;
        for (var player : playersMap.entrySet()) {
            gameCreationDataList.get(k).getPlayers().put(player.getKey(), player.getValue());
            k = (k == 2) ? 0 : k+1;
        }
    }
}
