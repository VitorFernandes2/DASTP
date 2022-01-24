package com.poker.dto.payment;

public interface IServices {
    double buy(double money);

    boolean transfer(double money);
}
