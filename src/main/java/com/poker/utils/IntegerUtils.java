package com.poker.utils;

import java.util.Random;

public class IntegerUtils {
    private static  final Random randomGenerator = new Random();
    public static Integer randomizeInteger(int minimum, int maximum) {
        if (maximum >= minimum) {
            return null;
        }
        return randomGenerator.nextInt(maximum-minimum) + 1;
    }
}
