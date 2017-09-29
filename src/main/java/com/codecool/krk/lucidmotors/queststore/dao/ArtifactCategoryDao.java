package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArtifactCategoryDao {

    private final Connection connection;
    private PreparedStatement stmt = null;

    public ArtifactCategoryDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
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

    public ArrayList<ArtifactCategory> getAllArtifactCategories() throws DaoException {

        ArrayList<ArtifactCategory> artifactCategories = new ArrayList<>();
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

//    public ArrayList<Mentor> getAllMentorsFromClass(SchoolClass schoolClass) throws DaoException {
//
//        ArrayList<Mentor> foundMentors = new ArrayList<>();
//        Integer classId = schoolClass.getId();
//
//        String sqlQuery = "SELECT * FROM mentors WHERE class_id = ?;";
//
//        try {
//            stmt = connection.prepareStatement(sqlQuery);
//            stmt.setInt(1, classId);
//
//            ResultSet result = stmt.executeQuery();
//
//            while (result.next()) {
//                Integer id = result.getInt("id");
//                String name = result.getString("name");
//                String login = result.getString("login");
//                String password = result.getString("password");
//                String email = result.getString("email");
//
//                Mentor mentor = new Mentor(name, login, password, email, schoolClass, id);
//                foundMentors.add(mentor);
//            }
//
//            result.close();
//            stmt.close();
//        } catch (SQLException e) {
//            throw new DaoException(this.getClass().getName() + " class caused a problem!");
//        }
//
//        return foundMentors;
//    }

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

}
