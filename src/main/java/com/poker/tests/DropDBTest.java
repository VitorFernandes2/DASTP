package com.poker.tests;

import com.poker.utils.DatabaseUtils;

public class DropDBTest {
    public static void main(String[] args) throws Exception {
        DatabaseUtils.dropDatabase();
    }
}
