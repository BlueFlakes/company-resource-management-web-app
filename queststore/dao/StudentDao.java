package queststore.dao;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;

import queststore.models.Student;
import queststore.models.Class;

public class StudentDao {

    private ArrayList<Student> students;

    public StudentDao(ClassDao classDao) {
        this.students = readStudentsData(classDao);
    }

    private ArrayList<Student> readStudentsData(ClassDao classDao) {
        ArrayList<Student> loadedStudents= new ArrayList<>();
        String[] studentData;
        Scanner fileScan;

        try {
            fileScan = new Scanner(new File("queststore/csv/student.csv"));

            while(fileScan.hasNextLine()) {
                studentData = fileScan.nextLine().split("\\|");
                String name = studentData[1];
                Integer id = Integer.parseInt(studentData[0]);
                String login = studentData[2];
                String password = studentData[3];
                String email = studentData[4];

                Integer classId = Integer.parseInt(studentData[5]);
                Class clas = classDao.getClass(classId);

                Student student = new Student(name, login, password, email, clas, id);
                loadedStudents.add(student);

                clas.addStudent(student);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File student.csv not found!");
        }

        return loadedStudents;
    }

    public Student getStudent(Integer id) {

        for (Student student : this.students) {
            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    public Student getStudent(String login) {

        for (Student student : this.students) {
            if (student.getLogin().equals(login)) {
                return student;
            }
        }

        return null;
    }

    public void addStudent(Student student) {
        this.students.add(student);
        Class clas = student.getClas();
        clas.addStudent(student);
    }

    public void save() {
    }

    public static void main(String[] args) {
        ClassDao classDao = new ClassDao();
        StudentDao studentDao = new StudentDao(classDao);
        Student student = studentDao.getStudent("mcnowak");
        System.out.println(student.getName());
        Class clas = classDao.getClass("klasa druga");
        System.out.println(clas.getAllStudents().get(1).getName());
        System.out.println(clas.getAllStudents());
        Class clas1 = classDao.getClass("klasa pierwsza");
        System.out.println(clas1.getAllStudents().get(0).getName());
        //System.out.println(classDao.classes.get(1).getId());
    }
}
