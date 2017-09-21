package com.codecool.krk.lucidmotors.queststore.dao;

import java.sql.*;

import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;

public class StudentDao {

    private final Connection connection;
    private Statement stmt = null;
    private ClassDao classDao;

    public StudentDao(ClassDao classDao) throws SQLException {

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

    public Student getStudent(Integer id) throws SQLException {

        Student student = null;

        String sqlQuery = "SELECT * FROM students "
               + "WHERE id = " + id + ";";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        if (result.next()) {
            String name = result.getString("name");
            String password = result.getString("password");
            String email = result.getString("email");
            String login = result.getString("login");
            Integer classId = result.getInt("class_id");
            Integer earnedCoins = result.getInt("earned_coins");
            Integer possessedCoins = result.getInt("possesed_coins");
            SchoolClass schoolClass = this.classDao.getSchoolClass(classId);
            student = new Student(name, login, password, email, schoolClass, id, earnedCoins, possessedCoins);
        }

        result.close();
        stmt.close();

        return student;
    }

    public Student getStudent(String login) throws SQLException {

        Student student = null;

        String sqlQuery = "SELECT * FROM students "
               + "WHERE login = '" + login + "';";
        ResultSet result = this.executeSqlQuery(sqlQuery);

        if (result.next()) {
            String name = result.getString("name");
            String password = result.getString("password");
            String email = result.getString("email");
            Integer id = result.getInt("id");
            Integer classId = result.getInt("class_id");
            Integer earnedCoins = result.getInt("earned_coins");
            Integer possessedCoins = result.getInt("possesed_coins");
            SchoolClass schoolClass = this.classDao.getSchoolClass(classId);
            student = new Student(name, login, password, email, schoolClass, id, earnedCoins, possessedCoins);
        }

        result.close();
        stmt.close();

        return student;
    }

    public void save(Student student) throws SQLException {

        String name = student.getName();
        String login = student.getLogin();
        String password = student.getPassword();
        String email = student.getEmail();
        Integer classId = student.getClas().getId();
        Integer earnedCoins = student.getEarnedCoins();
        Integer possessedCoins = student.getPossesedCoins();

        String sqlQuery = "INSERT INTO students "
                + "(name, login, password, email, class_id, earned_coins, possesed_coins) "
                + "VALUES ('" + name + "', '" + login + "', '" + password + "', '" + email + "', " + classId
                + ", " + earnedCoins + ", " + possessedCoins + ");";
        this.executeSqlUpdate(sqlQuery);

    }
}
