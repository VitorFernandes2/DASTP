package com.poker.utils;

import java.sql.*;

public class DatabaseUtils {
    static final String DB_URL = "jdbc:h2:~/poker";
    static final String USER = "sa";
    static final String PASS = "";

    public static void createDatabase() throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            String createUserSQL = "CREATE TABLE PLAYER " + "(name VARCHAR(255) not NULL UNIQUE)";
            statement.execute(createUserSQL);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static boolean checkIfDatabaseExists(String tableName) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            String checkTableSQL = "SELECT COUNT(*) AS total FROM information_schema.tables" + " WHERE table_schema = 'poker'" + " AND table_name = '" + tableName + "'" + " LIMIT 1";
            ResultSet result = statement.executeQuery(checkTableSQL);
            result.next();
            int total = result.getInt("total");
            result.close();
            statement.close();
            connection.close();
            return total > 0;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void startDatabases() throws Exception {
        boolean exists = DatabaseUtils.checkIfDatabaseExists("PLAYER");

        if (exists) {
            DatabaseUtils.createDatabase();
        }
    }

    public static boolean createPlayer(String name) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            String createUserSQL = "INSERT INTO PLAYER(name) VALUES ('" + name + "')";
            statement.executeUpdate(createUserSQL);
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static boolean getPlayerByName(String name) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            String findUserSQL = "SELECT COUNT(*) AS total FROM PLAYER WHERE name='" + name + "'";
            ResultSet rs = statement.executeQuery(findUserSQL);

            rs.next();
            int total = rs.getInt("total");

            rs.close();
            statement.close();
            connection.close();
            return total > 0;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
}
