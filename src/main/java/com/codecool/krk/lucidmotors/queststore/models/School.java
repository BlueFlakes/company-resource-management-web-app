package com.codecool.krk.lucidmotors.queststore.models;

import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.User;
import com.codecool.krk.lucidmotors.queststore.dao.ClassDao;
import com.codecool.krk.lucidmotors.queststore.dao.ManagerDao;
import com.codecool.krk.lucidmotors.queststore.dao.MentorDao;
import com.codecool.krk.lucidmotors.queststore.dao.StudentDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.LoginInUseException;

public class School {

    private final String name;
    private final ClassDao classDao;
    private final ManagerDao managerDao;
    private final MentorDao mentorDao;
    private final StudentDao studentDao;

    public School(String name) throws DaoException {

        this.name = name;
        this.classDao = new ClassDao();
        this.managerDao = new ManagerDao();
        this.mentorDao = new MentorDao(this.classDao);
        this.studentDao = new StudentDao(this.classDao);
    }

    public User getUser(String login) throws DaoException {

        User foundUser = null;

        foundUser = managerDao.getManager(login);
        if (foundUser != null) {
            return foundUser;
        }

        foundUser = mentorDao.getMentor(login);
        if (foundUser != null) {
            return foundUser;
        }

        foundUser = studentDao.getStudent(login);

        return foundUser;
    }

    public User getUser(String login, String password) throws DaoException {

        User foundUser = null;

        foundUser = managerDao.getManager(login, password);
        if (foundUser != null) {
            return foundUser;
        }

        foundUser = mentorDao.getMentor(login, password);
        if (foundUser != null) {
            return foundUser;
        }

        foundUser = studentDao.getStudent(login, password);

        return foundUser;
    }

    public Mentor getMentor(Integer id) throws DaoException {
        return this.mentorDao.getMentor(id);
    }

    public boolean isLoginAvailable(String login) throws DaoException {

        return this.getUser(login) == null;

    }

    public List<SchoolClass> getAllClasses() throws DaoException {

        return this.classDao.getAllClasses();
    }

    public List<Mentor> getAllMentors() throws DaoException {
        return this.mentorDao.getAllMentors();
    }

}
