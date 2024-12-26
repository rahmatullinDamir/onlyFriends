package dev.rahmatullin.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/onlyFriendsDb";

    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        initDB();
    }

    private void initDB() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

     public synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                initDB();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking or reinitializing the connection", e);
        }
        return connection;
    }
}
