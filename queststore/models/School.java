package queststore.models;

import java.util.ArrayList;

import queststore.models.User;

import queststore.dao.ClassDao;
import queststore.dao.ManagerDao;
import queststore.dao.MentorDao;
import queststore.dao.StudentDao;
import queststore.exceptions.LoginInUseException;

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

    public void addUser(User user) throws LoginInUseException {
        String login = user.getLogin();

        if (this.getUser(login) != null) {
            throw new LoginInUseException();
        }

        if (user instanceof Manager) {
            this.managerDao.addManager((Manager) user);
        } else if (user instanceof Mentor) {
            this.mentorDao.addMentor((Mentor) user);
        } else if (user instanceof Student) {
            this.studentDao.addStudent((Student) user);
        }
    }

    public void save() {
        this.managerDao.save();
        this.mentorDao.save();
        this.studentDao.save();
        this.classDao.save();
    }

    public ArrayList<Class> getAllClasses() {
        return this.classDao.getAllClasses();
    }

}
