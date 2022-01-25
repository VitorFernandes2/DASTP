package com.poker.dto.payment;

import com.poker.tests.WalletServiceTest;

public interface IServiceAdapter {
    /**
     * Function to adapt the simulation of the purchase of in-game currency with money from a Service.
     *
     * @param money             double - Purchase monetary value.
     * @param walletServiceTest {@link WalletServiceTest} - Player's wallet.
     */
    void buy(double money, WalletServiceTest walletServiceTest);

    /**
     * Function to simulate the transfer and transformation of in-game currency into cash from a Service.
     *
     * @param walletServiceTest {@link WalletServiceTest} - Player's wallet.
     */
    void transfer(WalletServiceTest walletServiceTest);

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
