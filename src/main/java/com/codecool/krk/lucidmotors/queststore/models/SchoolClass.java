package com.codecool.krk.lucidmotors.queststore.models;

import java.util.ArrayList;

public class SchoolClass {

    private String name;
    private ArrayList<Student> studentsList;
    private ArrayList<Mentor> mentorsList;
    private Integer id;
    private static Integer index = 0;

    public SchoolClass(String name) {
        this.name = name;
        this.studentsList = new ArrayList<>();
        this.mentorsList = new ArrayList<>();
        this.id = index++;
    }

    public SchoolClass(String name, Integer id) {
        this.name = name;
        this.studentsList = new ArrayList<>();
        this.mentorsList = new ArrayList<>();
        this.id = id;

        if (id > index) {
            index = id++;
        }
    }

    public void addStudent(Student student) {
        this.studentsList.add(student);
    }

    public void removeStudent(Student student) {
        this.studentsList.remove(student);
    }

    public ArrayList<Student> getAllStudents() {
        return this.studentsList;
    }

    public void addMentor(Mentor mentor) {
        this.mentorsList.add(mentor);
    }

    public void removeMentor(Mentor mentor) {
        this.studentsList.remove(mentor);
    }

    public ArrayList<Mentor> getAllMentors() {
        return this.mentorsList;
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
