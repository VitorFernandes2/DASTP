package com.poker.dto.payment;

public interface IService {
    /**
     * Function to only simulate a purchase with money from a Service.
     *
     * @param money double - Purchase monetary value.
     * @return double - Monetary value after implementation of the purchase fine.
     */
    double buy(double money);

    /**
     * Function to only simulate a money transfer from a Service.
     *
     * @param money double - Transfer monetary value.
     * @return boolean - It always returns a true value.
     */
    boolean transfer(double money);
}
