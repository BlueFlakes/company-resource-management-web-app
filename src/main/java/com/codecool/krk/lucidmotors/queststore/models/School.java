package com.codecool.krk.lucidmotors.queststore.models;

import java.util.ArrayList;

import com.codecool.krk.lucidmotors.queststore.models.User;

import com.codecool.krk.lucidmotors.queststore.dao.ClassDao;
import com.codecool.krk.lucidmotors.queststore.dao.ManagerDao;
import com.codecool.krk.lucidmotors.queststore.dao.MentorDao;
import com.codecool.krk.lucidmotors.queststore.dao.StudentDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.LoginInUseException;

public class School {

    private String name;
    private ClassDao classDao;
    private ManagerDao managerDao;
    private MentorDao mentorDao;
    private StudentDao studentDao;

    public School(String name) {
        this.name = name;
        this.classDao = new ClassDao();
        this.managerDao = new ManagerDao();
        this.mentorDao = new MentorDao(this.classDao);
        this.studentDao = new StudentDao(this.classDao);
    }

    public User getUser(String login) {

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

    public Mentor getMentor(Integer id) {
        return this.mentorDao.getMentor(id);
    }

    public void isLoginAvailable(String login) throws LoginInUseException {
        if (this.getUser(login) != null) {
            throw new LoginInUseException();
        }
    }

    public void save() {
        
    }

    public ArrayList<SchoolClass> getAllClasses() {
        return this.classDao.getAllClasses();
    }

    public ArrayList<Mentor> getAllMentors() {
        return this.mentorDao.getAllMentors();
    }

}
