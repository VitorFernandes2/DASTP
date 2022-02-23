package com.poker.model.payment;

public interface IService {
    /**
     * Function to only simulate a purchase with money from a Service.
     *
     * @param amount double - Purchase monetary value.
     * @return double - Monetary value after implementation of the purchase fine.
     */
    double buy(double amount);

    /**
     * Function to only simulate a money transfer from a Service.
     *
     * @param amount double - Transfer monetary value.
     * @return boolean - It always returns a true value.
     */
    boolean transfer(double amount);

    /**
     * Function to only simulate a money transfer from an external Service.
     *
     * @param amount
     * @return
     */
    boolean transferMoney(double amount);
}
