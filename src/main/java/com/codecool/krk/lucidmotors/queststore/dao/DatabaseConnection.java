package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    static Connection getConnection() throws DaoException {

        if (connection == null) {
            // synchronized Works with java >= 1.5, lazy loading + thread safety
            synchronized (DatabaseConnection.class) {

                if (connection == null) {
                    connectWithDatabase();
                }
            }
        }

        return connection;
    }

    private static void connectWithDatabase() throws DaoException {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:data/Codecool.db");
        } catch (SQLException e) {
            throw new DaoException("DatabaseConnection class caused a problem!");
        }
    }

    public static void closeConnection() throws DaoException {

        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("DatabaseConnection class caused a problem!");
        }

    }

}
