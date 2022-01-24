package com.poker.dto.payment;

import com.poker.tests.WalletServiceTest;

public interface IServicesAdapter {
    void buy(double money, WalletServiceTest wt);

    void transfer(WalletServiceTest wt);

    int chipsToGame(int pokerChips);

    int chipsToPocket(int pokerChipsGame);
}
