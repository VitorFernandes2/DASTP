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
     * Function to simulate the transfer from external payment entity into in-game cash from a Service.
     *
     * @param wallet {@link Wallet} - Player's wallet.
     */
    boolean transferMoney(Wallet wallet, double amount);

    /**
     * Function to turn game coins into poker chips.
     *
     * @param pokerChips int - Value of game coins to be converted into poker chips.
     * @return int - Number of converted poker chips.
     */
    int chipsToGame(int pokerChips);

    /**
     * Function to turn game coins into poker chips.
     *
     * @param pokerChips int - Value of game coins to be converted into poker chips.
     * @param conversionRate int - Conversion rate value.
     * @return int - Number of converted poker chips.
     */
    int chipsToGame(int pokerChips, int conversionRate);

    /**
     * Function to turn poker chips into game coins.
     *
     * @param pokerChipsGame int - Value of poker chips to be converted into game coins.
     * @return int - Amount of game coins converted.
     */
    int chipsToPocket(int pokerChipsGame);

    /**
     * Function to turn poker chips into game coins.
     *
     * @param pokerChipsGame int - Value of poker chips to be converted into game coins.
     * @param conversionRate int - Conversion rate value.
     * @return int - Amount of game coins converted.
     */
    int chipsToPocket(int pokerChipsGame, int conversionRate);
}
