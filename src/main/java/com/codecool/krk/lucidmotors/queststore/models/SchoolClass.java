package com.codecool.krk.lucidmotors.queststore.models;

import java.util.ArrayList;
import java.sql.SQLException;

import com.codecool.krk.lucidmotors.queststore.dao.ClassDao;

public class SchoolClass {

    private String name;
    private ArrayList<Student> studentsList;
    private ArrayList<Mentor> mentorsList;
    private Integer id;
    private ClassDao classDao = new ClassDao();

    public SchoolClass(String name) throws SQLException {
        this.name = name;
        this.studentsList = new ArrayList<>();
        this.mentorsList = new ArrayList<>();
        this.id = null;
        classDao.save(this);
    }

    public SchoolClass(String name, Integer id) throws SQLException {
        this.name = name;
        this.studentsList = new ArrayList<>();
        this.mentorsList = new ArrayList<>();
        this.id = id;
    }

    public void addStudent(Student student) {

    }

    public void removeStudent(Student student) {

    }

    public ArrayList<Student> getAllStudents() throws SQLException {
        return classDao.getAllStudentsFromClass(this);
    }

    public void addMentor(Mentor mentor) {

    }

    public void removeMentor(Mentor mentor) {

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
}
