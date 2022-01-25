package com.poker.dto.payment;

import com.poker.constants.Constants;
import com.poker.tests.WalletServiceTest;

public class ServiceAdapter implements IServiceAdapter {

    private IService service;

    /**
     * Class for adapting in-game currency conversion and payment services.
     *
     * @param service {@link EServices} - Payment Service type.
     */
    public ServiceAdapter(EServices service) {
        switch (service) {
            case PAYPAL:
                this.service = new Paypal();
                break;
        }
    }

    @Override
    public void buy(double money, WalletServiceTest walletServiceTest) {
        double pokerChipsMoney;

        if ((pokerChipsMoney = service.buy(money)) < 0.0) {
            return;
        }

        walletServiceTest.money -= money;

        walletServiceTest.pokerChips += moneyToPokerChips(pokerChipsMoney);
    }

    @Override
    public void transfer(WalletServiceTest walletServiceTest) {
        int returnedPokerChips = returnedPokerChips(walletServiceTest);

        if (!service.transfer(pokerChipsToMoney(returnedPokerChips))) {
            return;
        }

        walletServiceTest.pokerChips -= returnedPokerChips;

        walletServiceTest.money += pokerChipsToMoney(returnedPokerChips);
    }

    /**
     * Function to return the value of game coins to be withdrawn from the Player's game wallet.
     *
     * @param walletServiceTest {@link WalletServiceTest} - Player's wallet.
     * @return int - Amount in game coins to be withdrawn from the wallet.
     */
    private int returnedPokerChips(WalletServiceTest walletServiceTest) {
        if (walletServiceTest.pokerChips < Constants.MONEY_CONVERSION_RATE) {
            return 0;
        }

        return ((walletServiceTest.pokerChips / Constants.MONEY_CONVERSION_RATE) * Constants.MONEY_CONVERSION_RATE);
    }

    /**
     * Money to game currency conversion rate function.
     *
     * @param money double - Monetary value to be converted into game coins.
     * @return int - Amount of game coins converted.
     */
    private int moneyToPokerChips(double money) {
        return (int) (money * Constants.MONEY_CONVERSION_RATE);
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
