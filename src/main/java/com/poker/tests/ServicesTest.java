package com.poker.tests;

import com.poker.dto.payment.ServicesAdapter;
import com.poker.dto.payment.Paypal;

public class ServicesTest {
    public static void main(String[] args) {
        ServicesAdapter Services = new ServicesAdapter(new Paypal());
        WalletServiceTest wt = new WalletServiceTest(245.34, 0);
        try {
            System.out.println("Tenho " + wt.money + " EUR na carteira.");
            System.out.println("Comprar 132 EUR em pokerChips.");
            Services.buy(132.0, wt);
            System.out.println("pokerChips compradas: " + wt.pokerChips);
            Services.transfer(wt);
            System.out.println("Fiquei com " + wt.money + " EUR na carteira!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
