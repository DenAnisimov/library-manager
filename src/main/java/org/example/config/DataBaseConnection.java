package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private String url;
    private String user;
    private String password;

    public DataBaseConnection() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dbconfig.properties")) {
            Properties properties = new Properties();
            if (inputStream != null) {
                System.out.println("Loading failed properties from " + inputStream);
            }
            properties.load(inputStream);
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
