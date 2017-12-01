package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;
import com.codecool.krk.lucidmotors.queststore.models.ShopArtifact;
import com.codecool.krk.lucidmotors.queststore.models.Stylesheet;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StylesheetsDao {
    private static StylesheetsDao dao = null;
    private final Connection connection;


    private StylesheetsDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public static StylesheetsDao getDao() throws DaoException {

        if (dao == null) {

            synchronized (StylesheetsDao.class) {

                if (dao == null) {
                    dao = new StylesheetsDao();
                }
            }
        }

        return dao;
    }

    public List<Stylesheet> getAllStylesheets() throws DaoException {
        List<Stylesheet> stylesheets = new ArrayList<>();

        String sqlQuery = "SELECT * FROM stylesheets";

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                String path = result.getString("path");

                Stylesheet newStylesheet = new Stylesheet(name, path);
                stylesheets.add(newStylesheet);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }


        return stylesheets;
    }

    public Stylesheet getStylesheet(String name) throws DaoException {
        Stylesheet stylesheet = null;

        String sqlQuery = "SELECT * FROM stylesheets WHERE name = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String path = result.getString("path");

                stylesheet = new Stylesheet(name, path);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }


        return stylesheet;
    }
}
