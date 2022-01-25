package com.poker.model.payment;

import com.poker.model.constants.Constants;

public class Paypal implements IService {
    private double companyGains = 0;

    public Paypal() {
    }

    public double getCompanyGains() {
        return companyGains;
    }

    @Override
    public double buy(double amount) {
        double gained = amount * Constants.PAYPAL_INTEREST_RATE;
        amount -= gained;
        companyGains = getCompanyGains() + gained;
        return amount;
    }

    @Override
    public boolean transfer(double amount) {
        return true;
    }
}
