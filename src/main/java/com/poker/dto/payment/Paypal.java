package com.poker.dto.payment;

import com.poker.constants.Constants;

public class Paypal implements IServices {
    private double CompanyGains = 0;

    public Paypal() {
    }

    public Paypal(String path) {
    }

    public double profit() {
        return getCompanyGains();
    }

    private double getCompanyGains() {
        return CompanyGains;
    }

    @Override
    public double buy(double money) {
        double gained = money * Constants.PAYPAL_INTEREST_RATE;
        money -= gained;
        CompanyGains = getCompanyGains() + gained;
        return money;
    }

    @Override
    public boolean transfer(double money) {
        return true;
    }
}
