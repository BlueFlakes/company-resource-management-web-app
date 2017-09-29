package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ManagerDao {

    private final Connection connection;
    private Statement stmt = null;

    public ManagerDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public Manager getManager(Integer id) throws DaoException {

        Manager manager = null;
        String sqlQuery = "SELECT * FROM managers "
                + "WHERE id = " + id + ";";

        try {
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
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return manager;
    }

    private ResultSet executeSqlQuery(String sqlQuery) throws DaoException {
        ResultSet result = null;

        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return result;
    }

    public Manager getManager(String login) throws DaoException {

        Manager manager = null;
        String sqlQuery = "SELECT * FROM managers "
                + "WHERE login = '" + login + "';";

        try {
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
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return manager;
    }

}
