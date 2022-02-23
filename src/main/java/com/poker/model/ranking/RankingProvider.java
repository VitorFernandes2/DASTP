package com.poker.model.ranking;

import com.poker.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

public class RankingProvider implements UnitOfWork<RankingLine> {
    private static RankingProvider provider;
    private List<RankingLine> updateList;
    private List<RankingLine> deleteList;
    private List<RankingLine> insertList;

    private RankingProvider() {
    }

    @Override
    public void registerNew(RankingLine entity) {
        if (insertList == null) {
            insertList = new ArrayList<>();
        }
        insertList.add(entity);
    }

    @Override
    public void registerUpdate(RankingLine entity) {
        if (updateList == null) {
            updateList = new ArrayList<>();
        }
        updateList.add(entity);
    }

    @Override
    public void registerDelete(RankingLine entity) {
        if (deleteList == null) {
            deleteList = new ArrayList<>();
        }
        deleteList.add(entity);
    }

    @Override
    public void commit() {
        if (insertList != null && insertList.size() > 0) {
            commitInsert();
        }
        if (updateList != null && updateList.size() > 0) {
            commitUpdate();
        }
        if (deleteList != null && deleteList.size() > 0) {
            commitDelete();
        }
    }

    private void commitDelete() {
        for (var element: deleteList) {
            try {
                DatabaseUtils.deleteRanking(element);
            } catch (Exception e) {
                System.out.println("Error deleting ranking in database! " + e.getMessage());
            }
        }
    }

    private void commitUpdate() {
        for (var element: updateList) {
            try {
                DatabaseUtils.updateRanking(element);
            } catch (Exception e) {
                System.out.println("Error updating ranking in database! " + e.getMessage());
            }
        }
    }

    private void commitInsert() {
        for (var element: insertList) {
            try {
                DatabaseUtils.insertRanking(element);
            } catch (Exception e) {
                System.out.println("Error inserting ranking in database! " + e.getMessage());
            }
        }
    }

    public static RankingProvider getInstance() {
        if (provider == null) {
            provider = new RankingProvider();
        }
        return provider;
    }
}
