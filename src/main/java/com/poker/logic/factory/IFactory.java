package com.poker.logic.factory;

public interface IFactory<T, S> {
    T createObject(S object);
}
