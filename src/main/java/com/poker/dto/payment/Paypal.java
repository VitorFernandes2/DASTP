package com.poker.dto.payment;

import com.poker.constants.Constants;

public class Paypal implements IService {
    private double companyGains = 0;

    public Paypal() {
    }

    public double getCompanyGains() {
        return companyGains;
    }

    @Override
    public double buy(double money) {
        double gained = money * Constants.PAYPAL_INTEREST_RATE;
        money -= gained;
        companyGains = getCompanyGains() + gained;
        return money;
    }

    @Override
    public boolean transfer(double money) {
        return true;
    }
}
