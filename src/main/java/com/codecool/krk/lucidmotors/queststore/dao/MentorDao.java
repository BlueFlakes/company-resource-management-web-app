package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.models.Mentor;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

public class MentorDao {

    private final Connection connection;
    private Statement stmt = null;
    private ClassDao classDao;

    public MentorDao(ClassDao classDao) throws SQLException {

        this.connection = DatabaseConnection.getConnection();
        this.classDao = classDao;
    }

    private ResultSet executeSqlQuery(String sqlQuery) throws SQLException {

        stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sqlQuery);

        return result;
    }

    private void executeSqlUpdate(String sqlQuery) throws SQLException {

        stmt = connection.createStatement();
        stmt.executeUpdate(sqlQuery);
        stmt.close();

    }

    public Mentor getMentor(Integer id) throws SQLException {

        Mentor mentor = null;
        String sqlQuery = "SELECT * FROM mentors "
                + "WHERE id = " + id + ";";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        if (result.next()) {
            String name = result.getString("name");
            String password = result.getString("password");
            String email = result.getString("email");
            String login = result.getString("login");
            Integer classId = result.getInt("class_id");
            SchoolClass schoolClass = this.classDao.getSchoolClass(classId);
            mentor = new Mentor(name, login, password, email, schoolClass, id);
        }

        result.close();
        stmt.close();

        return mentor;
    }

    public Mentor getMentor(String login) throws SQLException {

        Mentor mentor = null;

        String sqlQuery = "SELECT * FROM mentors "
                + "WHERE login = '" + login + "';";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        if (result.next()) {
            String name = result.getString("name");
            String password = result.getString("password");
            String email = result.getString("email");
            Integer id = result.getInt("id");
            Integer classId = result.getInt("class_id");
            SchoolClass schoolClass = this.classDao.getSchoolClass(classId);
            mentor = new Mentor(name, login, password, email, schoolClass, id);
        }

        result.close();
        stmt.close();

        return mentor;
    }

    public ArrayList<Mentor> getAllMentors() throws SQLException {

        ArrayList<Mentor> foundMentors = new ArrayList<>();

        String sqlQuery = "SELECT * FROM mentors";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        while (result.next()) {
            Integer id = result.getInt("id");
            String name = result.getString("name");
            String login = result.getString("login");
            String password = result.getString("password");
            String email = result.getString("email");
            Integer classId = result.getInt("class_id");
            SchoolClass schoolClass = this.classDao.getSchoolClass(classId);

            Mentor mentor = new Mentor(name, login, password, email, schoolClass, id);
            foundMentors.add(mentor);
        }

        result.close();
        stmt.close();

        return foundMentors;
    }

    public void save(Mentor mentor) throws SQLException {

        String name = mentor.getName();
        String login = mentor.getLogin();
        String password = mentor.getPassword();
        String email = mentor.getEmail();
        Integer classId = mentor.getClas().getId();

        String sqlQuery = "INSERT INTO mentors "
                + "(name, login, password, email, class_id) "
                + "VALUES ('" + name + "', '" + login + "', '" + password + "', '" + email + "', '" + classId + "');";
        this.executeSqlUpdate(sqlQuery);
    }

}
