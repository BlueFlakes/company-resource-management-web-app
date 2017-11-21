package com.codecool.krk.lucidmotors.queststore.models;

import java.util.ArrayList;
import com.codecool.krk.lucidmotors.queststore.dao.ClassDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

public class SchoolClass {

    private String name;
    private Integer id;
    private final ClassDao classDao = ClassDao.getDao();


    public SchoolClass(String name) throws DaoException {
        this.name = name;
        this.id = null;
    }

    public SchoolClass(String name, Integer id) throws DaoException {
        this.name = name;
        this.id = id;
    }

    public ArrayList<Student> getAllStudents() throws DaoException {
        return classDao.getAllStudentsFromClass(this);
    }

    public ArrayList<Mentor> getAllMentors() throws DaoException {
        return classDao.getAllMentorsFromClass(this);
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getClassSaveString() {
        return String.format("%d|%s%n", this.getId(), this.getName());
    }

    public String toString() {
        return this.name;
    }

    public void save() throws DaoException {
        classDao.save(this);
    }

}
