package com.codecool.krk.lucidmotors.queststore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    public static Connection getConnection() {

        try {

            if (connection == null) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:data/Codecool.db");
            }

        } catch (SQLException e) {
            System.out.println("Database connection failure!");

        } catch (ClassNotFoundException e1) {
            System.out.println(e1.getMessage());
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {

        try {

            connection.close();

        } catch (SQLException e) {
            System.out.println("Database connection closing failure!");
        }

    }
}
