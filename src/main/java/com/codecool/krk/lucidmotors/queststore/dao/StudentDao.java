package com.codecool.krk.lucidmotors.queststore.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;

public class StudentDao {

    private static StudentDao dao = null;
    private final Connection connection;
    private PreparedStatement stmt = null;
    private final ClassDao classDao;

    private StudentDao() throws DaoException {
        this.connection = DatabaseConnection.getConnection();
        this.classDao = ClassDao.getDao();
    }

    public static StudentDao getDao() throws DaoException {

        if (dao == null) {

            synchronized (StudentDao.class) {

                if (dao == null) {
                    dao = new StudentDao();
                }
            }
        }

        return dao;
    }

    public Student getStudent(Integer id) throws DaoException {

        Student student = null;
        String sqlQuery = "SELECT * FROM students WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                student = getStudentFromResultset(result);
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
                student = getStudentFromResultset(result);
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
                student = getStudentFromResultset(result);
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

    private Student getStudentFromResultset(ResultSet result) throws SQLException, DaoException {
        String name = result.getString("name");
        String password = result.getString("password");
        String email = result.getString("email");
        String login = result.getString("login");
        Integer classId = result.getInt("class_id");
        Integer earnedCoins = result.getInt("earned_coins");
        Integer possessedCoins = result.getInt("possesed_coins");
        SchoolClass schoolClass = this.classDao.getSchoolClass(classId);
        Integer id = result.getInt("id");
        Student student = new Student(name, login, password, email, schoolClass, id, earnedCoins, possessedCoins);

        return student;
    }

    public List<Student> getAllStudents() throws DaoException {

        List<Student> students = new ArrayList<>();
        String sqlQuery = "SELECT * FROM students;";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {

                Student student = this.getStudentFromResultset(result);
                students.add(student);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return students;
    }

}
