package com.poker.logic.game.state;

import com.poker.logic.game.logic.GameEngine;
import com.poker.model.ranking.RankingLine;
import com.poker.model.ranking.RankingProvider;
import com.poker.utils.DatabaseUtils;

import java.io.Serializable;

public class StateAdapter implements IGameState, Serializable {
    private static final long serialVersionUID = 455139798414883697L;
    private final GameEngine gameEngine;

    public StateAdapter(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public IGameState startGame(String playerName, String creatorName, int minimumAmount) {
        return this;
    }

    @Override
    public IGameState bet(String playerName, Integer amount) {
        return this;
    }

    @Override
    public IGameState check(String playerName) {
        return this;
    }

    @Override
    public IGameState fold(String playerName) {
        return this;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    protected IGameState isGameOver() {
        getGameEngine().triggerShowdown();
        if (getGameEngine().isGameOver()) {
            try {
                String winner = gameEngine.getWinner();
                Integer wins = DatabaseUtils.getPlayerRanking(winner);

                DatabaseUtils.updateWallet(winner, getGameEngine().getPlayers().get(winner).getWallet());

                if (wins == null) {
                    RankingLine newRankingLine = new RankingLine(winner, 1);
                    getGameEngine().getRankings().put(winner, newRankingLine);
                    RankingProvider.getInstance().registerNew(newRankingLine);
                } else {
                    getGameEngine().getRankings().get(winner).incWins();
                    RankingProvider.getInstance().registerUpdate(new RankingLine(winner, wins + 1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            getGameEngine().announceTableWinner();
            return null;
        } else {
            return new SetupState(getGameEngine());
        }
    }
}
