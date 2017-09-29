package com.codecool.krk.lucidmotors.queststore.dao;

import java.sql.*;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;

public class StudentDao {

    private final Connection connection;
    private Statement stmt = null;
    private final ClassDao classDao;

    public StudentDao(ClassDao classDao) throws DaoException {

        this.connection = DatabaseConnection.getConnection();
        this.classDao = classDao;
    }

    private ResultSet executeSqlQuery(String sqlQuery) throws DaoException {
        ResultSet result = null;

        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return result;
    }

    private void executeSqlUpdate(String sqlQuery) throws DaoException {

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sqlQuery);
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }


    }

    public Student getStudent(Integer id) throws DaoException {

        Student student = null;
        String sqlQuery = "SELECT * FROM students "
                + "WHERE id = " + id + ";";

        try {
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
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return student;
    }

    public Student getStudent(String login) throws DaoException {

        Student student = null;
        String sqlQuery = "SELECT * FROM students "
               + "WHERE login = '" + login + "';";

        try {
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
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return student;
    }

    public void save(Student student) throws DaoException {

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
