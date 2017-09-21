package com.codecool.krk.lucidmotors.queststore.dao;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Formatter;

import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.Mentor;


public class ClassDao {
    private Connection connection;
    Statement stmt = null;

    public ClassDao() {
        this.connection = DatabaseConnection.getConnection();
    }

    private ResultSet executeSqlQuery(String sqlQuery) {
        ResultSet result = null;

        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery(sqlQuery);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return result;
    }

    private void executeSqlUpdate(String sqlQuery) {

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sqlQuery);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

    public SchoolClass getSchoolClass(Integer id) {
        SchoolClass schoolClass = null;

        try {
            String sqlQuery = "SELECT * FROM classes "
                   + "WHERE id = " + id + ";";
            ResultSet result = this.executeSqlQuery(sqlQuery);

            if (result.next()) {
                String name = result.getString("name");
                schoolClass = new SchoolClass(name, id);
            }

            result.close();
            stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return schoolClass;
    }

    public SchoolClass getSchoolClass(String name) {
        SchoolClass schoolClass = null;

        try {
            String sqlQuery = "SELECT * FROM classes "
                   + "WHERE name = '" + name + "';";
            ResultSet result = this.executeSqlQuery(sqlQuery);

            if (result.next()) {
                Integer id = result.getInt("id");
                schoolClass = new SchoolClass(name, id);
            }

            result.close();
            stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return schoolClass;
    }

    public ArrayList<SchoolClass> getAllClasses() {
        ArrayList<SchoolClass> schoolClasses = new ArrayList<>();

        try {
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

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return schoolClasses;
    }

    public ArrayList<Student> getAllStudentsFromClass(SchoolClass schoolClass) {
        ArrayList<Student> foundStudents = new ArrayList<>();
        Integer classId = schoolClass.getId();

        try {
            String sqlQuery = "SELECT * FROM students "
                    + "WHERE class_id = " + classId + ";";
            ResultSet result = this.executeSqlQuery(sqlQuery);

            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                String login = result.getString("login");
                String password = result.getString("password");
                String email = result.getString("email");

                Student student = new Student(name, login, password, email, schoolClass, id);
                foundStudents.add(student);
            }

            result.close();
            stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return foundStudents;
    }

    public ArrayList<Mentor> getAllMentorsFromClass(SchoolClass schoolClass) {
        ArrayList<Mentor> foundMentors = new ArrayList<>();
        Integer classId = schoolClass.getId();

        try {
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

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return foundMentors;
    }

    public void save(SchoolClass schoolClass) {
        try {
            String name = schoolClass.getName();
            String sqlQuery = "INSERT INTO classes "
                    + "(name) "
                    + "VALUES ('" + name + "');";
            this.executeSqlUpdate(sqlQuery);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
