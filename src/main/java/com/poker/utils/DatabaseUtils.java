package com.poker.utils;

import com.poker.model.player.Player;
import com.poker.model.ranking.RankingLine;
import com.poker.model.wallet.Wallet;

import java.sql.*;
import java.util.Map;

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
            String createWalletSQL = "CREATE TABLE WALLET " + "(name VARCHAR(255) not NULL UNIQUE, " + "amount DOUBLE, " + "pokerchips INTEGER, " + "pokergamechips INTEGER, " + "PRIMARY KEY (name))";
            String createRankingSQL = "CREATE TABLE RANKING " + "(name VARCHAR(255) not NULL UNIQUE, " + "wins INTEGER, " + "PRIMARY KEY (name))";
            statement.execute(createUserSQL);
            statement.execute(createWalletSQL);
            statement.execute(createRankingSQL);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void dropDatabase() throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            String dropTablesSQL = "DROP TABLE IF EXISTS PLAYER, WALLET, RANKING";
            statement.execute(dropTablesSQL);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void startDatabases() throws Exception {
        try {
            DatabaseUtils.createDatabase();
        } catch (Exception e) {
            if (!e.getMessage().contains("already exists")) {
                throw new Exception("Error creating Database!");
            }
        }
    }

    public static boolean createPlayer(String name) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

            String createUserSQL = "INSERT INTO PLAYER(name) VALUES ('" + name + "')";
            statement.executeUpdate(createUserSQL);

            String createUserWallet = "INSERT INTO WALLET(name, amount, pokerchips, pokergamechips) " + " VALUES ('" + name + "', 0, 0, 0)";
            statement.executeUpdate(createUserWallet);

            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static boolean playerExistsByName(String name) throws Exception {
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

    public static Player getPlayerByName(String name) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

            Player player = null;
            String username = "";

            String findUserSQL = "SELECT * FROM PLAYER WHERE name='" + name + "'";
            ResultSet rs = statement.executeQuery(findUserSQL);
            while (rs.next()) {
                username = rs.getString("name");
            }
            rs.close();

            if (!username.equals("")) {
                player = new Player(username);
            } else {
                return null;
            }

            Wallet userWallet = null;
            double amount = 0;
            int pokerChips = 0;
            int pokerGameChips = 0;

            String findUserWalletSQL = "SELECT * FROM WALLET WHERE name='" + name + "'";
            ResultSet walletRs = statement.executeQuery(findUserWalletSQL);
            boolean found = false;
            while (walletRs.next()) {
                amount = walletRs.getDouble("amount");
                pokerChips = walletRs.getInt("pokerchips");
                pokerGameChips = walletRs.getInt("pokergamechips");
                found = true;
            }
            walletRs.close();

            if (found) {
                player.setAmount(amount);
                player.setPokerChips(pokerChips);
                player.setPokerGameChips(pokerGameChips);
            }

            statement.close();
            connection.close();
            return player;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static boolean updateWallet(String username, Wallet wallet) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

            String updateUserWalletSQL = "UPDATE WALLET " +
                    "SET amount=" + wallet.getAmount() + ", pokerchips=" + wallet.getPokerChips() + ", pokergamechips=" + wallet.getPokerGameChips() + " " +
                    "WHERE name='" + username + "'";
            statement.executeUpdate(updateUserWalletSQL);

            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void updatePlayerName(String name, String newName) {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

            String updateUserWalletSQL = "UPDATE PLAYER " +
                    "SET name=" + newName + " " +
                    "WHERE name='" + name + "'";
            statement.executeUpdate(updateUserWalletSQL);

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error updating user " + e.getMessage());
        }
    }

    public static void removePlayerFromDB(String playerName) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

            String deleteUserSQL = "DELETE FROM PLAYER WHERE name='" + playerName + "'";
            statement.executeUpdate(deleteUserSQL);

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void insertRanking(RankingLine element) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

            String createRankingSQL = "INSERT INTO RANKING(name, wins) VALUES ('" + element.getPlayerName() + "', " + element.getWins() + ")";
            statement.executeUpdate(createRankingSQL);

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void deleteRanking(RankingLine element) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

            String deleteRankingSQL = "DELETE FROM RANKING WHERE name='" + element.getPlayerName() + "'";
            statement.executeUpdate(deleteRankingSQL);

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void updateRanking(RankingLine element) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

            String updateUserWalletSQL = "UPDATE RANKING " +
                    "SET name=" + element.getPlayerName() + ", " +
                    "wins=" + element.getWins() + " " +
                    "WHERE name='" + element.getPlayerName() + "'";
            statement.executeUpdate(updateUserWalletSQL);

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void setRankings(Map<String, RankingLine> rankings) throws Exception {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();

            String username = "";
            int wins = 0;

            String findUserSQL = "SELECT * FROM RANKING";
            ResultSet rs = statement.executeQuery(findUserSQL);
            while (rs.next()) {
                username = rs.getString("name");
                wins = rs.getInt("wins");

                rankings.put(username, new RankingLine(username, wins));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
}
