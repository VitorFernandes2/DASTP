package com.poker.model.payment;

import com.poker.model.wallet.Wallet;

public interface IServiceAdapter {
    /**
     * Function to adapt the simulation of the purchase of in-game currency with money from a Service.
     *
     * @param amount double - Purchase monetary value.
     * @param wallet {@link Wallet} - Player's wallet.
     */
    void buy(double amount, Wallet wallet);

    /**
     * Function to simulate the transfer and transformation of in-game currency into cash from a Service.
     *
     * @param wallet {@link Wallet} - Player's wallet.
     */
    void transfer(Wallet wallet);

    /**
     * Function to turn game coins into poker chips.
     *
     * @param pokerChips int - Value of game coins to be converted into poker chips.
     * @return int - Number of converted poker chips.
     */
    int chipsToGame(int pokerChips);

    /**
     * Function to turn poker chips into game coins.
     *
     * @param pokerChipsGame int - Value of poker chips to be converted into game coins.
     * @return int - Amount of game coins converted.
     */
    int chipsToPocket(int pokerChipsGame);
}
