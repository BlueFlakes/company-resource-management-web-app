package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.models.Mentor;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;
import com.codecool.krk.lucidmotors.queststore.models.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

public class ClassDao {

    private final Connection connection;
    private Statement stmt = null;

    public ClassDao() throws SQLException {

        this.connection = DatabaseConnection.getConnection();
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

    public SchoolClass getSchoolClass(Integer id) throws SQLException {

        SchoolClass schoolClass = null;

        String sqlQuery = "SELECT * FROM classes "
                + "WHERE id = " + id + ";";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        if (result.next()) {
            String name = result.getString("name");
            schoolClass = new SchoolClass(name, id);
        }

        result.close();
        stmt.close();

        return schoolClass;
    }

    public SchoolClass getSchoolClass(String name) throws SQLException {

        SchoolClass schoolClass = null;

        String sqlQuery = "SELECT * FROM classes "
                + "WHERE name = '" + name + "';";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        if (result.next()) {
            Integer id = result.getInt("id");
            schoolClass = new SchoolClass(name, id);
        }

        result.close();
        stmt.close();

        return schoolClass;
    }

    public ArrayList<SchoolClass> getAllClasses() throws SQLException {

        ArrayList<SchoolClass> schoolClasses = new ArrayList<>();

        String sqlQuery = "SELECT * FROM classes";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        while (result.next()) {
            Integer id = result.getInt("id");
            String name = result.getString("name");
            SchoolClass schoolClass = new SchoolClass(name, id);
            schoolClasses.add(schoolClass);
        }

        result.close();
        stmt.close();

        return schoolClasses;
    }

    public ArrayList<Student> getAllStudentsFromClass(SchoolClass schoolClass) throws SQLException {

        ArrayList<Student> foundStudents = new ArrayList<>();
        Integer classId = schoolClass.getId();

        String sqlQuery = "SELECT * FROM students "
                + "WHERE class_id = " + classId + ";";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        while (result.next()) {
            Integer id = result.getInt("id");
            String name = result.getString("name");
            String login = result.getString("login");
            String password = result.getString("password");
            String email = result.getString("email");
            Integer earnedCoins = result.getInt("earned_coins");
            Integer possessedCoins = result.getInt("possesed_coins");

            Student student = new Student(name, login, password, email, schoolClass, id, earnedCoins, possessedCoins);
            foundStudents.add(student);
        }

        result.close();
        stmt.close();

        return foundStudents;
    }

    public ArrayList<Mentor> getAllMentorsFromClass(SchoolClass schoolClass) throws SQLException {

        ArrayList<Mentor> foundMentors = new ArrayList<>();
        Integer classId = schoolClass.getId();

        String sqlQuery = "SELECT * FROM mentors "
                + "WHERE class_id = " + classId + ";";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        while (result.next()) {
            Integer id = result.getInt("id");
            String name = result.getString("name");
            String login = result.getString("login");
            String password = result.getString("password");
            String email = result.getString("email");

            Mentor mentor = new Mentor(name, login, password, email, schoolClass, id);
            foundMentors.add(mentor);
        }

        result.close();
        stmt.close();

        return foundMentors;
    }

    public void save(SchoolClass schoolClass) throws SQLException {

        String name = schoolClass.getName();
        String sqlQuery = "INSERT INTO classes "
                + "(name) "
                + "VALUES ('" + name + "');";
        this.executeSqlUpdate(sqlQuery);

    }
}
