package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ClassDao;
import com.codecool.krk.lucidmotors.queststore.dao.MentorDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Mentor;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;
import com.codecool.krk.lucidmotors.queststore.models.Student;

import java.util.List;
import java.util.Map;

public class ManagerController {
    School school;

    public ManagerController(School school) {
        this.school = school;
    }

    public List<Student> getMentorClass(Integer mentorId) throws DaoException {
        Mentor mentor = school.getMentor(mentorId);

        if (mentor == null) {
            return null;
        } else {
            return mentor.getClas().getAllStudents();
        }
    }

    public boolean editMentor(Map<String, String> formData) throws DaoException {
        Boolean isUpdated = false;

        Integer mentorId = Integer.valueOf(formData.get("mentor_id"));
        Mentor mentor = MentorDao.getDao().getMentor(mentorId);

        if (mentor != null &&
                (this.school.isLoginAvailable(formData.get("login")) || formData.get("login").equals(mentor.getLogin()))) {
            mentor.setName(formData.get("name"));
            mentor.setLogin(formData.get("login"));
            mentor.setPassword(formData.get("password"));
            mentor.setEmail(formData.get("email"));
            Integer classID = Integer.valueOf(formData.get("class_id"));
            mentor.setSchoolClass(ClassDao.getDao().getSchoolClass(classID));
            mentor.update();
            isUpdated = true;
        }

        return isUpdated;
    }

    private SchoolClass chooseProperClass(Integer classId) throws DaoException {

       SchoolClass schoolClass = ClassDao.getDao().getSchoolClass(classId);

        return schoolClass;
    }

    public boolean addMentor(Map<String, String> formData) throws DaoException {
        Boolean isAdded = false;

        if (this.school.isLoginAvailable(formData.get("login"))) {
            String name = formData.get("name");
            String login = formData.get("login");
            String password = formData.get("password");
            String email = formData.get("email");
            Integer classId = Integer.valueOf(formData.get("class_id"));
            SchoolClass chosenClass = chooseProperClass(classId);
            Mentor mentor = new Mentor(name, login, password, email, chosenClass);
            mentor.save();
            isAdded = true;
        }

        return isAdded;

    }

    public boolean createClass(Map<String, String> formData) throws DaoException {
        String name = formData.get("classname");
        ClassDao dao = ClassDao.getDao();

        if (dao.isGivenClassNameUnique(name)) {
            SchoolClass schoolClass = new SchoolClass(name);
            schoolClass.save();
            return true;
        }

        return false;
    }
}