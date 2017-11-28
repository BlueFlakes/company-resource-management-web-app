package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Mentor;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;
import com.codecool.krk.lucidmotors.queststore.models.Student;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class ClassDao {

    private static ClassDao dao;
    private final Connection connection;
    private PreparedStatement stmt = null;

    private ClassDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public static ClassDao getDao() throws DaoException {
        if (dao == null) {

            synchronized (ClassDao.class) {

                if(dao == null) {
                    dao = new ClassDao();
                }
            }
        }

        return dao;
    }

    public SchoolClass getSchoolClass(Integer id) throws DaoException {

        SchoolClass schoolClass = null;
        String sqlQuery = "SELECT * FROM classes WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                schoolClass = new SchoolClass(name, id);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return schoolClass;
    }

    public SchoolClass getSchoolClass(String name) throws DaoException {

        SchoolClass schoolClass = null;

        String sqlQuery = "SELECT * FROM classes WHERE name = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Integer id = result.getInt("id");
                schoolClass = new SchoolClass(name, id);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return schoolClass;
    }

    public List<SchoolClass> getAllClasses() throws DaoException {

        List<SchoolClass> schoolClasses = new ArrayList<>();
        String sqlQuery = "SELECT * FROM classes";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                SchoolClass schoolClass = new SchoolClass(name, id);
                schoolClasses.add(schoolClass);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return schoolClasses;
    }

    public List<Student> getAllStudentsFromClass(SchoolClass schoolClass) throws DaoException {

        List<Student> foundStudents = new ArrayList<>();
        Integer classId = schoolClass.getId();

        String sqlQuery = "SELECT * FROM students WHERE class_id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, classId);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                String login = result.getString("login");
                String password = result.getString("password");
                String email = result.getString("email");
                BigInteger earnedCoins = new BigInteger(result.getString("earned_coins"));
                BigInteger possessedCoins = new BigInteger(result.getString("possesed_coins"));

                Student student = new Student(name, login, password, email, schoolClass, id, earnedCoins, possessedCoins);
                foundStudents.add(student);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return foundStudents;
    }

    public List<Mentor> getAllMentorsFromClass(SchoolClass schoolClass) throws DaoException {

        List<Mentor> foundMentors = new ArrayList<>();
        Integer classId = schoolClass.getId();

        String sqlQuery = "SELECT * FROM mentors WHERE class_id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, classId);

            ResultSet result = stmt.executeQuery();

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
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return foundMentors;
    }

    public void save(SchoolClass schoolClass) throws DaoException {

        String name = schoolClass.getName();
        String sqlQuery = "INSERT INTO classes (name) VALUES (?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public boolean isGivenClassNameUnique(String name) throws DaoException {
        String sqlQuery = "SELECT * FROM classes WHERE name LIKE ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return false;
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return true;
    }
}
