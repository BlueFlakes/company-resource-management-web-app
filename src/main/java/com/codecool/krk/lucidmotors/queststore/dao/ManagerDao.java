package com.codecool.krk.lucidmotors.queststore.dao;

import java.sql.*;

import com.codecool.krk.lucidmotors.queststore.models.Manager;

public class ManagerDao {
    private Connection connection;
    private Statement stmt = null;

    public ManagerDao() {
        this.connection = DatabaseConnection.getConnection();
    }

    public Manager getManager(Integer id) {
        Manager manager = null;

        try {
            String sqlQuery = "SELECT * FROM managers "
                   + "WHERE id = " + id + ";";
            ResultSet result = this.executeSqlQuery(sqlQuery);

            if (result.next()) {
                String name = result.getString("name");
                String password = result.getString("password");
                String email = result.getString("email");
                String login = result.getString("login");
                manager = new Manager(name, login, password, email, id);
            }

            result.close();
            stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return manager;
    }

    private ResultSet executeSqlQuery(String sqlQuery) {
        ResultSet result = null;

        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery(sqlQuery);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return result;
    }

    public Manager getManager(String login) {
        Manager manager = null;

        try {
            String sqlQuery = "SELECT * FROM managers "
                   + "WHERE login = '" + login + "';";
            ResultSet result = this.executeSqlQuery(sqlQuery);

            if (result.next()) {
                String name = result.getString("name");
                String password = result.getString("password");
                String email = result.getString("email");
                Integer id = result.getInt("id");
                manager = new Manager(name, login, password, email, id);
            }

            result.close();
            stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return manager;
    }

    public void addManager(Manager manager) {

    }

    public void save() {

    }
}
