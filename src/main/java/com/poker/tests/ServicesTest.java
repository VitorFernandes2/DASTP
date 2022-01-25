package com.poker.tests;

import com.poker.model.payment.EServices;
import com.poker.model.payment.ServiceAdapter;
import com.poker.model.wallet.Wallet;

public class ServicesTest {
    public static void main(String[] args) {
        ServiceAdapter serviceAdapter = new ServiceAdapter(EServices.PAYPAL);
        Wallet wallet = new Wallet(245.34, 0, 0);
        System.out.println("I have " + wallet.getAmount() + " EUR on my wallet.");
        System.out.println("I have " + wallet.getPokerChips() + " in game currency (PC)");
        System.out.println("I will buy 132.00 EUR in game currency (PC).");
        serviceAdapter.buy(132.00, wallet);
        System.out.println("I now have " + wallet.getAmount() + " EUR on my wallet.");
        System.out.println("I now have " + wallet.getPokerChips() + " in game currency (PC)");
        serviceAdapter.transfer(wallet);
        System.out.println("After converting my game currency back into money, I end up with " + wallet.getAmount() + " EUR on my wallet!");
        System.out.println("After converting my game currency back into money, I end up with " + wallet.getPokerChips() + " in game currency (PC) on my wallet!");
    }
}
