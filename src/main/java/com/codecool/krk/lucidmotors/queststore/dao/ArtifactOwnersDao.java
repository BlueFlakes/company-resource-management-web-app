package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.BoughtArtifact;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.User;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArtifactOwnersDao {

    private static ArtifactOwnersDao dao;
    private final Connection connection;
    private PreparedStatement stmt = null;
    //private ArtifactCategoryDao artifactCategoryDao = new ArtifactCategoryDao();

    private ArtifactOwnersDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public static ArtifactOwnersDao getDao() throws DaoException {
        if(dao == null) {
            dao = new ArtifactOwnersDao();
        }
        return dao;
    }

    public ArrayList<Student> getOwners(BoughtArtifact boughtArtifact) throws DaoException {
        ArrayList<Student> owners = new ArrayList<>();

        Integer artifactId = boughtArtifact.getId();
        String sqlQuery = "SELECT * FROM artifact_owners "
                + "WHERE artifact_id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, artifactId);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer studentId = result.getInt("student_id");

                Student owner = new StudentDao().getStudent(studentId);
                owners.add(owner);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return owners;
    }

    public List<BoughtArtifact> getArtifacts(User student) throws DaoException {
        List<BoughtArtifact> ownedArtifacts = new ArrayList<>();

        Integer studentId = student.getId();
        String sqlQuery = "SELECT * FROM artifact_owners "
                + "WHERE student_id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, studentId);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer artifactId = result.getInt("artifact_id");

                BoughtArtifact ownedArtifact = BoughtArtifactDao.getDao().getArtifact(artifactId);
                ownedArtifacts.add(ownedArtifact);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return ownedArtifacts;
    }


    private void saveOwner(Integer artifactId, Student owner) throws DaoException {
        Integer studentId = owner.getId();

        String sqlQuery = "INSERT INTO artifact_owners "
                + "(artifact_id, student_id) "
                + "VALUES (?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            stmt.setInt(1, artifactId);
            stmt.setInt(2, studentId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void saveArtifactOwners(Integer artifactId, List<Student> owners) throws DaoException {

        for (Student owner : owners) {
            this.saveOwner(artifactId, owner);
        }

    }

}
