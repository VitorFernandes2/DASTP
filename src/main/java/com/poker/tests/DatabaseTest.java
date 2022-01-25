package com.poker.tests;

import com.poker.utils.DatabaseUtils;

public class DatabaseTest {
    public static void main(String[] args) throws Exception {
        boolean exists = DatabaseUtils.checkIfDatabaseExists("PLAYER");

        if (exists) {
            DatabaseUtils.createDatabase();
        }

        String msg = exists ? "Doesn't " : "";
        System.out.println(msg + "exist");
    }
}
