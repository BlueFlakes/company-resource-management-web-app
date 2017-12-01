package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.controllers.AfineEnigma;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Mentor;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MentorDao {

    private static MentorDao dao = null;
    private final Connection connection;
    private PreparedStatement stmt = null;
    private final ClassDao classDao;

    private MentorDao(ClassDao classDao) throws DaoException {

        this.connection = DatabaseConnection.getConnection();
        this.classDao = classDao;
    }

    public static MentorDao getDao() throws DaoException {

        if (dao == null) {

            synchronized (MentorDao.class) {

                if (dao == null) {
                    dao = new MentorDao(ClassDao.getDao());
                }
            }
        }

        return dao;
    }

    public Mentor getMentor(Integer id) throws DaoException {

        Mentor mentor = null;
        String sqlQuery = "SELECT * FROM mentors WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                mentor = createMentor(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return mentor;
    }

    public Mentor getMentor(String login) throws DaoException {

        Mentor mentor = null;
        String sqlQuery = "SELECT * FROM mentors WHERE login = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, login);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                mentor = createMentor(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return mentor;
    }

    public Mentor getMentor(String login, String password) throws DaoException {

        Mentor mentor = null;
        String sqlQuery = "SELECT * FROM mentors WHERE login = ? AND password = ?;";

        password = AfineEnigma.getAfineEnigma().encipher(password);

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                mentor = createMentor(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return mentor;
    }

    private Mentor createMentor(ResultSet result) throws SQLException, DaoException {
        String name = result.getString("name");
        String login = result.getString("login");
        String password = AfineEnigma.getAfineEnigma().decipher(result.getString("password"));
        String email = result.getString("email");
        Integer id = result.getInt("id");
        Integer classId = result.getInt("class_id");
        SchoolClass schoolClass = this.classDao.getSchoolClass(classId);

        return new Mentor(name, login, password, email, schoolClass, id);
    }

    public List<Mentor> getAllMentors() throws DaoException {

        List<Mentor> foundMentors = new ArrayList<>();
        String sqlQuery = "SELECT * FROM mentors";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                String login = result.getString("login");
                String password = AfineEnigma.getAfineEnigma().decipher(result.getString("password"));
                String email = result.getString("email");
                Integer classId = result.getInt("class_id");
                SchoolClass schoolClass = this.classDao.getSchoolClass(classId);

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

    public void save(Mentor mentor) throws DaoException {

        String name = mentor.getName();
        String login = mentor.getLogin();
        String password = AfineEnigma.getAfineEnigma().encipher(mentor.getPassword());
        String email = mentor.getEmail();
        Integer classId = mentor.getClas().getId();

        String sqlQuery = "INSERT INTO mentors "
                + "(name, login, password, email, class_id) "
                + "VALUES (?, ?, ?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setInt(5, classId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public void update(Mentor mentor) throws DaoException {

        String name = mentor.getName();
        String login = mentor.getLogin();
        String password = AfineEnigma.getAfineEnigma().encipher(mentor.getPassword());
        String email = mentor.getEmail();
        Integer classId = mentor.getClas().getId();
        Integer mentorId = mentor.getId();

        String sqlQuery = "UPDATE mentors "
                + "SET name = ?, login = ?, password = ?, email = ?, class_id = ? "
                + "WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setInt(5, classId);
            stmt.setInt(6, mentorId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

}
