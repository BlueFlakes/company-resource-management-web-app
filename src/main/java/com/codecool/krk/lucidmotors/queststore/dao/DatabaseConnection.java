package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static final String dbUrl = "jdbc:sqlite:data/Codecool.db";


    private static Connection connection = null;

    public static Connection getConnection() throws DaoException {

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
            connection = DriverManager.getConnection(DatabaseConnection.dbUrl);
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

    public static void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(DatabaseConnection.dbUrl, "sa", null);
        flyway.migrate();
    }

}
