package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ExperienceLevels;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ExperienceLevelsDao {

    private static ExperienceLevelsDao dao = null;
    private final Connection connection;
    private PreparedStatement stmt = null;

    public ExperienceLevelsDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public static ExperienceLevelsDao getDao() throws DaoException {

        if (dao == null) {

            synchronized (ExperienceLevelsDao.class) {

                if (dao == null) {
                    dao = new ExperienceLevelsDao();
                }
            }
        }

        return dao;
    }

    private void clearTable() throws DaoException {

        String sqlQuery = "DELETE FROM experience_levels;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void updateExperienceLevels(ExperienceLevels experienceLevels) throws DaoException {

        TreeMap<Integer, BigInteger> levels = experienceLevels.getLevels();

        this.clearTable();

        for (Integer level : levels.keySet()) {

            String sqlQuery = "INSERT INTO experience_levels "
                    + "(id, coins_needed) "
                    + "VALUES (?, ?);";

            try {
                stmt = connection.prepareStatement(sqlQuery);

                stmt.setInt(1, level);
                stmt.setString(2, levels.get(level).toString());

                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(this.getClass().getName() + " class caused a problem!");
            }
        }
    }

    public ExperienceLevels getExperienceLevels() throws DaoException {

        ExperienceLevels experienceLevels = new ExperienceLevels();
        String sqlQuery = "SELECT * FROM experience_levels;";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer level = result.getInt("id");
                BigInteger coinsNeeded = new BigInteger(result.getString("coins_needed"));

                experienceLevels.addLevel(coinsNeeded, level);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return experienceLevels;
    }

    public Integer getHighestLevelCoins() throws DaoException {
        Integer neededCoins = 0;
        String sqlQuery = "SELECT MAX(coins_needed) as `coins` FROM experience_levels;";
        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
                neededCoins = resultSet.getInt("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return neededCoins;
    }

    public Integer getHighestLevelID() throws DaoException {
        Integer id = 0;
        String sqlQuery = "SELECT MAX(id) as `ID` FROM experience_levels;";
        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
                id = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
