package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;
import com.codecool.krk.lucidmotors.queststore.models.Contribution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactCategoryDao {
    private static ArtifactCategoryDao dao = null;
    private final Connection connection;
    private PreparedStatement stmt = null;

    private ArtifactCategoryDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public static ArtifactCategoryDao getDao() throws DaoException{
        if (dao == null) {

            synchronized (ArtifactCategoryDao.class) {

                if(dao == null) {
                    dao = new ArtifactCategoryDao();
                }
            }
        }

        return dao;
    }

    public ArtifactCategory getArtifactCategory(Integer id) throws DaoException {

        ArtifactCategory artifactCategory = null;
        String sqlQuery = "SELECT * FROM artifact_categories WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                artifactCategory = new ArtifactCategory(name, id);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return artifactCategory;
    }

    public ArtifactCategory getArtifactCategory(String name) throws DaoException {

        ArtifactCategory artifactCategory = null;
        String sqlQuery = "SELECT * FROM artifact_categories WHERE name = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Integer id = result.getInt("id");
                artifactCategory = new ArtifactCategory(name, id);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return artifactCategory;
    }

    public List<ArtifactCategory> getAllArtifactCategories() throws DaoException {

        List<ArtifactCategory> artifactCategories = new ArrayList<>();
        String sqlQuery = "SELECT * FROM artifact_categories";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                ArtifactCategory artifactCategory = new ArtifactCategory(name, id);
                artifactCategories.add(artifactCategory);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return artifactCategories;
    }

    public void save(ArtifactCategory artifactCategory) throws DaoException {

        String name = artifactCategory.getName();
        String sqlQuery = "INSERT INTO artifact_categories (name) VALUES (?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public ArtifactCategory getArtifactByName(String name) throws DaoException {
        String sqlQuery = "SELECT * FROM artifact_categories WHERE name LIKE ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Integer id = result.getInt("id");
                return new ArtifactCategory(name, id);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return null;
    }
}
