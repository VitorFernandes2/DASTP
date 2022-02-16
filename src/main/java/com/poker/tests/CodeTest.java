package com.poker.tests;

public class CodeTest {
    public static void main(String[] args) {
        String val = "hello";
        String val2 = val;
        String val3 = new String(val);
        String val4 = new StringBuilder().append(val).toString();
        System.out.println("Break");
    }
}
