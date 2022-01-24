package com.poker.dto.payment;

import com.poker.constants.Constants;
import com.poker.tests.WalletServiceTest;

public class ServicesAdapter implements IServicesAdapter {

    private final IServices paypal;

    public ServicesAdapter(IServices paypal) {
        this.paypal = paypal;
    }

    @Override
    public void buy(double money, WalletServiceTest wt) {
        double pokerChipsMoney;

        if ((pokerChipsMoney = paypal.buy(money)) < 0.0) {
            return;
        }

        wt.money -= money;

        wt.pokerChips += moneyToPokerChips(pokerChipsMoney);
    }

    @Override
    public void transfer(WalletServiceTest wt) {
        int returnedPokerChips = returnedPokerChips(wt);

        if (!paypal.transfer(pokerChipsToMoney(returnedPokerChips))) {
            return;
        }

        wt.pokerChips -= returnedPokerChips;

        wt.money += pokerChipsToMoney(returnedPokerChips);
    }

    private int returnedPokerChips(WalletServiceTest wt) {
        if (wt.pokerChips < Constants.MONEY_CONVERSION_RATE) {
            return 0;
        }

        return ((wt.pokerChips / Constants.MONEY_CONVERSION_RATE) * Constants.MONEY_CONVERSION_RATE);
    }

    private int moneyToPokerChips(double money) {
        return (int) (money * Constants.MONEY_CONVERSION_RATE);
    }

    private int pokerChipsToMoney(int pokerChips) {
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
