package org.example;

import org.example.config.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        try (Connection connection = dataBaseConnection.getConnection()) {
            System.out.println(connection.getCatalog());
        } catch (SQLException ex) {

        }
    }
}