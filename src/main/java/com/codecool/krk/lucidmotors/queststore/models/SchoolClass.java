package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ClassDao;

import java.util.ArrayList;

public class SchoolClass {

    private String name;
    private Integer id;
    private final ClassDao classDao = new ClassDao();

    public SchoolClass(String name) {

        this.name = name;
        this.id = null;
        classDao.save(this);
    }

    public SchoolClass(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public void addStudent(Student student) {

    }

    public void removeStudent(Student student) {

    }

    public ArrayList<Student> getAllStudents() {
        return classDao.getAllStudentsFromClass(this);
    }

    public void addMentor(Mentor mentor) {

    }

    public void removeMentor(Mentor mentor) {

    }

    public ArrayList<Mentor> getAllMentors() {
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
