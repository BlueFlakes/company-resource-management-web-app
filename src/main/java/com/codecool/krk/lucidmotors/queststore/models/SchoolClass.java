package com.codecool.krk.lucidmotors.queststore.models;

import java.util.ArrayList;
import java.sql.SQLException;
import com.codecool.krk.lucidmotors.queststore.dao.ClassDao;

public class SchoolClass {

    private String name;
    private Integer id;
    private final ClassDao classDao = new ClassDao();


    public SchoolClass(String name) throws SQLException {
        this.name = name;
        this.id = null;
    }

    public SchoolClass(String name, Integer id) throws SQLException {
        this.name = name;
        this.id = id;
    }

    public ArrayList<Student> getAllStudents() throws SQLException {
        return classDao.getAllStudentsFromClass(this);
    }

    public ArrayList<Mentor> getAllMentors() throws SQLException {
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

    public void save() throws SQLException {
        classDao.save(this);
    }

}
