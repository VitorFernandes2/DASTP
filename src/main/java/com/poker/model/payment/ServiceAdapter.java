package com.poker.model.payment;

import com.poker.model.constants.Constants;
import com.poker.model.wallet.Wallet;

import java.io.Serializable;

/**
 * Class for adapting in-game currency conversion and payment services.
 */
public class ServiceAdapter implements IServiceAdapter, Serializable {

    private static final long serialVersionUID = -1853709015333080685L;
    private IService service;

    public ServiceAdapter(EServices service) {
        switch (service) {
            case PAYPAL:
                this.service = new Paypal();
                break;
        }
    }

    /**
     * Function to use money from the player wallet as PCs.
     *
     * @param amount double - Purchase monetary value.
     * @param wallet {@link Wallet} - Player's wallet.
     */
    @Override
    public void buy(double amount, Wallet wallet) {
        double pokerChipsMoney;

        if ((pokerChipsMoney = service.buy(amount)) < 0.0) {
            return;
        }

        wallet.removeAmount(amount);

        wallet.addPokerChips(moneyToPokerChips(pokerChipsMoney));
    }

    /**
     * Function to transfer PCs to the player wallet as money.
     *
     * @param wallet {@link Wallet} - Player's wallet.
     */
    @Override
    public void transfer(Wallet wallet) {
        int returnedPokerChips = wallet.getPokerChips();
        double returnedMoney = pokerChipsToMoney(returnedPokerChips);

        if (!service.transfer(returnedMoney)) {
            return;
        }

        wallet.removePokerChips(returnedPokerChips);

        wallet.addAmount(returnedMoney);
    }

    @Override
    public boolean transferMoney(Wallet wallet, double amount) {
        return service.transferMoney(amount);
    }

    /**
     * Money to game currency conversion rate function.
     *
     * @param amount double - Monetary value to be converted into game coins.
     * @return int - Amount of game coins converted.
     */
    private int moneyToPokerChips(double amount) {
        return (int) (amount * Constants.MONEY_CONVERSION_RATE);
    }

    /**
     * Game currency to cash conversion rate function.
     *
     * @param pokerChips int - Amount of game coins to be converted into cash.
     * @return double - Transformed monetary value.
     */
    private double pokerChipsToMoney(int pokerChips) {
        return pokerChips / Constants.MONEY_CONVERSION_RATE;
    }

    /**
     * Function to convert PCs to PCJs.
     *
     * @param pokerChips int - Value of game coins to be converted into poker chips.
     * @return int - PCJs.
     */
    @Override
    public int chipsToGame(int pokerChips) {
        return chipsToGame(pokerChips, Constants.PC_CONVERSION_RATE);
    }

    /**
     * Function to convert PCs to PCJs.
     *
     * @param pokerChips     int - Value of game coins to be converted into poker chips.
     * @param conversionRate int - Conversion rate value.
     * @return int - PCJs.
     */
    @Override
    public int chipsToGame(int pokerChips, int conversionRate) {
        return pokerChips * conversionRate;
    }

    /**
     * Function to convert PCJs to PCs.
     *
     * @param pokerChipsGame int - Value of poker chips to be converted into game coins.
     * @return int - PCs.
     */
    @Override
    public int chipsToPocket(int pokerChipsGame) {
        return chipsToPocket(pokerChipsGame, Constants.PC_CONVERSION_RATE);
    }

    /**
     * Function to convert PCJs to PCs.
     *
     * @param pokerChipsGame int - Value of poker chips to be converted into game coins.
     * @param conversionRate int - Conversion rate value.
     * @return int - PCs.
     */
    @Override
    public int chipsToPocket(int pokerChipsGame, int conversionRate) {
        return pokerChipsGame / conversionRate;
    }
}
