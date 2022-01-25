package com.poker.model.payment;

import com.poker.model.constants.Constants;
import com.poker.model.wallet.Wallet;

/**
 * Class for adapting in-game currency conversion and payment services.
 */
public class ServiceAdapter implements IServiceAdapter {

    private IService service;

    public ServiceAdapter(EServices service) {
        switch (service) {
            case PAYPAL:
                this.service = new Paypal();
                break;
        }
    }

    @Override
    public void buy(double amount, Wallet wallet) {
        double pokerChipsMoney;

        if ((pokerChipsMoney = service.buy(amount)) < 0.0) {
            return;
        }

        wallet.removeAmount(amount);

        wallet.addPokerChips(moneyToPokerChips(pokerChipsMoney));
    }

    @Override
    public void transfer(Wallet wallet) {
        int returnedPokerChips = returnedPokerChips(wallet);

        if (!service.transfer(pokerChipsToMoney(returnedPokerChips))) {
            return;
        }

        wallet.removePokerChips(returnedPokerChips);

        wallet.addAmount(pokerChipsToMoney(returnedPokerChips));
    }

    /**
     * Function to return the value of game coins to be withdrawn from the Player's game wallet.
     *
     * @param wallet {@link Wallet} - Player's wallet.
     * @return int - Amount in game coins to be withdrawn from the wallet.
     */
    private int returnedPokerChips(Wallet wallet) {
        if (wallet.getPokerChips() < Constants.MONEY_CONVERSION_RATE) {
            return 0;
        }

        return ((wallet.getPokerChips() / Constants.MONEY_CONVERSION_RATE) * Constants.MONEY_CONVERSION_RATE);
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

    @Override
    public int chipsToGame(int pokerChips) {
        return pokerChips * Constants.PC_CONVERSION_RATE;
    }

    @Override
    public int chipsToPocket(int pokerChipsGame) {
        return pokerChipsGame / Constants.PC_CONVERSION_RATE;
    }
}