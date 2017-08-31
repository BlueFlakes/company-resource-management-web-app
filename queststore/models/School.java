package queststore.models;

import java.util.ArrayList;

import queststore.models.User;

import queststore.dao.ClassDao;
import queststore.dao.ManagerDao;
import queststore.dao.MentorDao;
import queststore.dao.StudentDao;

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

    public void save() {
        this.managerDao.save();
        this.mentorDao.save();
        this.studentDao.save();
        this.classDao.save();
    }
}
