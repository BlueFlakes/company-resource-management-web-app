package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManagerDao {

    private final Connection connection;
    private PreparedStatement stmt = null;

    public ManagerDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public Manager getManager(Integer id) throws DaoException {

        Manager manager = null;
        String sqlQuery = "SELECT * FROM managers WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                manager = createManager(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return manager;
    }

    public Manager getManager(String login) throws DaoException {

        Manager manager = null;
        String sqlQuery = "SELECT * FROM managers WHERE login = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, login);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                manager = createManager(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return manager;
    }

    public Manager getManager(String login, String password) throws DaoException {

        Manager manager = null;
        String sqlQuery = "SELECT * FROM managers WHERE login = ? AND password = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                manager = createManager(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return manager;
    }

    private Manager createManager(ResultSet result) throws SQLException {
        String name = result.getString("name");
        String login = result.getString("login");
        String password = result.getString("password");
        String email = result.getString("email");
        Integer id = result.getInt("id");

        return new Manager(name, login, password, email, id);
    }

}
