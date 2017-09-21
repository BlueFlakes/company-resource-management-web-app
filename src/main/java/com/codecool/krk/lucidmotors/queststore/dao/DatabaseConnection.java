package com.codecool.krk.lucidmotors.queststore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {

        if (connection == null) {

            connection = DriverManager.getConnection("jdbc:sqlite:data/Codecool.db");
        }

        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }

}
