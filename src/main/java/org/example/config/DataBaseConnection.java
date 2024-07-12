package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private final String USER = "postgres";
    private final String PASSWORD = "12345";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
