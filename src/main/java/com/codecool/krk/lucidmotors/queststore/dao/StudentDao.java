package com.codecool.krk.lucidmotors.queststore.dao;

import java.sql.*;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;

public class StudentDao {

    private final Connection connection;
    private PreparedStatement stmt = null;
    private final ClassDao classDao;

    public StudentDao(ClassDao classDao) throws DaoException {

        this.connection = DatabaseConnection.getConnection();
        this.classDao = classDao;
    }

    public Student getStudent(Integer id) throws DaoException {

        Student student = null;
        String sqlQuery = "SELECT * FROM students WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                student = createStudent(result);
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
        String sqlQuery = "SELECT * FROM students WHERE login = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, login);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                student = createStudent(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return student;
    }

    public Student getStudent(String login, String password) throws DaoException {

        Student student = null;
        String sqlQuery = "SELECT * FROM students WHERE login = ? AND password = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                student = createStudent(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return student;
    }

    private Student createStudent(ResultSet result) throws SQLException, DaoException {

        String name = result.getString("name");
        String login = result.getString("login");
        String password = result.getString("password");
        String email = result.getString("email");
        Integer id = result.getInt("id");
        Integer classId = result.getInt("class_id");
        Integer earnedCoins = result.getInt("earned_coins");
        Integer possessedCoins = result.getInt("possesed_coins");
        SchoolClass schoolClass = this.classDao.getSchoolClass(classId);

        return new Student(name, login, password, email, schoolClass, id, earnedCoins, possessedCoins);
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
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            stmt.setString(1, name);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setInt(5, classId);
            stmt.setInt(6, earnedCoins);
            stmt.setInt(7, possessedCoins);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void update(Student student) throws DaoException {

        Integer studentId = student.getId();
        String name = student.getName();
        String login = student.getLogin();
        String password = student.getPassword();
        String email = student.getEmail();
        Integer classId = student.getClas().getId();
        Integer earnedCoins = student.getEarnedCoins();
        Integer possessedCoins = student.getPossesedCoins();

        String sqlQuery = "UPDATE students "
                + "SET name = ?, login = ?, password = ?, email = ?, class_id = ?, earned_coins = ?, possesed_coins = ? "
                + "WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            stmt.setString(1, name);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setInt(5, classId);
            stmt.setInt(6, earnedCoins);
            stmt.setInt(7, possessedCoins);
            stmt.setInt(8, studentId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

}
