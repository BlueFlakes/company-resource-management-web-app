package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;
import com.codecool.krk.lucidmotors.queststore.models.BoughtArtifact;
import com.codecool.krk.lucidmotors.queststore.models.Student;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class BoughtArtifactDao {
    private static BoughtArtifactDao dao;
    private final Connection connection;
    private PreparedStatement stmt = null;
    private ArtifactCategoryDao artifactCategoryDao = ArtifactCategoryDao.getDao();

    private BoughtArtifactDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public static BoughtArtifactDao getDao() throws DaoException {
        if (dao == null) {

            synchronized (BoughtArtifactDao.class) {

                if(dao == null) {
                    dao = new BoughtArtifactDao();
                }
            }
        }

        return dao;
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        Date purchaseDate = dateFormatter.parse(dateString);

        return purchaseDate;
    }

    public BoughtArtifact getArtifact(Integer id) throws DaoException {

        BoughtArtifact boughtArtifact = null;
        String sqlQuery = "SELECT * FROM bought_artifacts WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                BigInteger price = new BigInteger(result.getString("price"));
                Integer categoryId = result.getInt("category_id");
                String description = result.getString("description");
                Integer isUsedInteger = result.getInt("is_used");
                String purchaseDateString = result.getString("purchase_date");

                ArtifactCategory artifactCategory = artifactCategoryDao.getArtifactCategory(categoryId);
                Date purchaseDate = this.parseDate(purchaseDateString);

                boolean isUsed = (isUsedInteger == 1);
                boughtArtifact = new BoughtArtifact(name, price, artifactCategory, description, id, purchaseDate, isUsed);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        } catch (ParseException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem! Wrong database date data!");
        }

        return boughtArtifact;

    }

    public void updateArtifact(BoughtArtifact boughtArtifact) throws DaoException {

        String name = boughtArtifact.getName();
        BigInteger price = boughtArtifact.getPrice();
        Integer categoryId = boughtArtifact.getArtifactCategory().getId();
        String description = boughtArtifact.getDescription();
        Integer artifactId = boughtArtifact.getId();

        boolean isUsed = boughtArtifact.isUsed();
        Integer isUsedInteger = (isUsed) ? 1 : 0;

        String purchaseDateString = boughtArtifact.getDate();

        String sqlQuery = "UPDATE bought_artifacts "
                + "SET name = ?, price = ?, category_id = ?, description = ?, purchase_date = ?, is_used = ? "
                + "WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            stmt.setString(1, name);
            stmt.setString(2, price.toString());
            stmt.setInt(3, categoryId);
            stmt.setString(4, description);
            stmt.setString(5, purchaseDateString);
            stmt.setInt(6, isUsedInteger);
            stmt.setInt(7, artifactId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public List<BoughtArtifact> getAllArtifacts() throws DaoException {

        List<BoughtArtifact> boughtArtifacts = null;
        String sqlQuery = "SELECT * FROM bought_artifacts;";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                BigInteger price = new BigInteger(result.getString("price"));
                Integer categoryId = result.getInt("category_id");
                String description = result.getString("description");
                Integer isUsedInteger = result.getInt("is_used");
                String purchaseDateString = result.getString("purchase_date");

                ArtifactCategory artifactCategory = artifactCategoryDao.getArtifactCategory(categoryId);
                Date purchaseDate = this.parseDate(purchaseDateString);

                boolean isUsed = (isUsedInteger == 1);

                BoughtArtifact boughtArtifact = new BoughtArtifact(name, price, artifactCategory,
                                                                   description, id, purchaseDate, isUsed);
                boughtArtifacts.add(boughtArtifact);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        } catch (ParseException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem! Wrong database date data!");
        }

        return boughtArtifacts;
    }

    public void save(BoughtArtifact boughtArtifact, List<Student> owners) throws DaoException {
        this.saveArtifact(boughtArtifact);
        Integer artifactId = this.getArtifactId();
        ArtifactOwnersDao.getDao().saveArtifactOwners(artifactId, owners);
    }

    private void saveArtifact(BoughtArtifact boughtArtifact) throws DaoException {
        String name = boughtArtifact.getName();
        BigInteger price = boughtArtifact.getPrice();
        Integer categoryId = boughtArtifact.getArtifactCategory().getId();
        String description = boughtArtifact.getDescription();

        boolean isUsed = boughtArtifact.isUsed();
        Integer isUsedInteger = (isUsed) ? 1 : 0;

        String purchaseDateString = boughtArtifact.getDate();

        String sqlQuery = "INSERT INTO bought_artifacts "
                + "(name, price, category_id, purchase_date, is_used, description) "
                + "VALUES (?, ?, ?, ?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            stmt.setString(1, name);
            stmt.setString(2, price.toString());
            stmt.setInt(3, categoryId);
            stmt.setString(6, description);
            stmt.setString(4, purchaseDateString);
            stmt.setInt(5, isUsedInteger);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }
    }

    private Integer getArtifactId() throws DaoException {
        Integer artifactID = null;

        String sqlQuery = "SELECT MAX(id) FROM bought_artifacts;";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                artifactID = result.getInt(1);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return artifactID;

    }

}
