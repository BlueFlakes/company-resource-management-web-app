package com.codecool.krk.lucidmotors.queststore.dao;

import java.sql.*;

import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;

public class StudentDao {
    private Connection connection;
    private Statement stmt = null;
    private ClassDao classDao;

    public StudentDao(ClassDao classDao) {
        this.connection = DatabaseConnection.getConnection();
        this.classDao = classDao;
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

    public Student getStudent(Integer id) {
        Student student = null;

        try {
            String sqlQuery = "SELECT * FROM students "
                   + "WHERE id = " + id + ";";
            ResultSet result = this.executeSqlQuery(sqlQuery);

            if (result.next()) {
                String name = result.getString("name");
                String password = result.getString("password");
                String email = result.getString("email");
                String login = result.getString("login");
                Integer classId = result.getInt("class_id");
                SchoolClass schoolClass = this.classDao.getSchoolClass(classId);
                student = new Student(name, login, password, email, schoolClass, id);
            }

            result.close();
            stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return student;
    }

    public Student getStudent(String login) {
        Student student = null;

        try {
            String sqlQuery = "SELECT * FROM students "
                   + "WHERE login = '" + login + "';";
            ResultSet result = this.executeSqlQuery(sqlQuery);

            if (result.next()) {
                String name = result.getString("name");
                String password = result.getString("password");
                String email = result.getString("email");
                Integer id = result.getInt("id");
                Integer classId = result.getInt("class_id");
                SchoolClass schoolClass = this.classDao.getSchoolClass(classId);
                student = new Student(name, login, password, email, schoolClass, id);
            }

            result.close();
            stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return student;
    }

    public void addStudent(Student student) {

    }

    public void save(Student student) {
        try {
            String name = student.getName();
            String login = student.getLogin();
            String password = student.getPassword();
            String email = student.getEmail();
            Integer classId = student.getClas().getId();
            Integer earnedCoins = student.getEarnedCoins();
            Integer possesedCoins = student.getPossesedCoins();

            String sqlQuery = "INSERT INTO students "
                    + "(name, login, password, email, class_id, earned_coins, possesed_coins) "
                    + "VALUES ('" + name + "', '" + login + "', '" + password + "', '" + email + "', " + classId
                    + ", " + earnedCoins + ", " + possesedCoins + ");";
            this.executeSqlUpdate(sqlQuery);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
