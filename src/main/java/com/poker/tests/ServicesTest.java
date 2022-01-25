package com.poker.tests;

import com.poker.dto.payment.EServices;
import com.poker.dto.payment.ServiceAdapter;

public class ServicesTest {
    public static void main(String[] args) {
        ServiceAdapter serviceAdapter = new ServiceAdapter(EServices.PAYPAL);
        WalletServiceTest walletServiceTest = new WalletServiceTest(245.34, 0);
        System.out.println("I have " + walletServiceTest.money + " EUR on my wallet.");
        System.out.println("I have " + walletServiceTest.pokerChips + " in game currency (PC)");
        System.out.println("I will buy 132.00 EUR in game currency (PC).");
        serviceAdapter.buy(132.00, walletServiceTest);
        System.out.println("I now have " + walletServiceTest.money + " EUR on my wallet.");
        System.out.println("I now have " + walletServiceTest.pokerChips + " in game currency (PC)");
        serviceAdapter.transfer(walletServiceTest);
        System.out.println("After converting my game currency back into money, I end up with " + walletServiceTest.money + " EUR on my wallet!");
        System.out.println("After converting my game currency back into money, I end up with " + walletServiceTest.pokerChips + " in game currency (PC) on my wallet!");
    }
}
