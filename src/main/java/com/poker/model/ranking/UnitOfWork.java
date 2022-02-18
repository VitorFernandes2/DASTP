package com.poker.model.ranking;

public interface UnitOfWork<T> {
    void registerNew(T entity);
    void registerUpdate(T entity);
    void registerDelete(T entity);
    void commit();
}
