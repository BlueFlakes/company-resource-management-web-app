package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.models.Mentor;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MentorDao {

    private final Connection connection;
    private Statement stmt = null;
    private ClassDao classDao;

    public MentorDao(ClassDao classDao) {

        this.connection = DatabaseConnection.getConnection();
        this.classDao = classDao;
    }

    private ResultSet executeSqlQuery(String sqlQuery) {

        ResultSet result = null;

        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery(sqlQuery);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return result;
    }

    private void executeSqlUpdate(String sqlQuery) {

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sqlQuery);
            stmt.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Mentor getMentor(Integer id) {

        Mentor mentor = null;

        try {
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

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return mentor;
    }

    public Mentor getMentor(String login) {

        Mentor mentor = null;

        try {
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

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return mentor;
    }

    public ArrayList<Mentor> getAllMentors( ) {

        ArrayList<Mentor> foundMentors = new ArrayList<>();

        try {
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

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return foundMentors;
    }

    public void addMentor(Mentor mentor) {

    }

    public void save(Mentor mentor) {

        try {
            String name = mentor.getName();
            String login = mentor.getLogin();
            String password = mentor.getPassword();
            String email = mentor.getEmail();
            Integer classId = mentor.getClas().getId();

            String sqlQuery = "INSERT INTO mentors "
                    + "(name, login, password, email, class_id) "
                    + "VALUES ('" + name + "', '" + login + "', '" + password + "', '" + email + "', '" + classId + "');";
            this.executeSqlUpdate(sqlQuery);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
