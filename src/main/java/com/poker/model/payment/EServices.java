package com.poker.model.payment;

public enum EServices {
    PAYPAL("Paypal");

    private final String name;

    EServices(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
