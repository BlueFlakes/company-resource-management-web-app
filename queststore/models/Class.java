package queststore.models;

import java.util.ArrayList;

public class Class {
    private String name;
    private ArrayList<Student> studentsList;

    public Class(String name) {
        this.name = name;
        this.studentsList = new ArrayList<>();
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

}
