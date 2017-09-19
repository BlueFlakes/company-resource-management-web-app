package queststore.dao;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Formatter;

import queststore.models.Student;
import queststore.models.SchoolClass;

public class StudentDao {

    private ArrayList<Student> students;

    public StudentDao(ClassDao classDao) {
        this.students = readStudentsData(classDao);
    }

    private ArrayList<Student> readStudentsData(ClassDao classDao) {
        ArrayList<Student> loadedStudents= new ArrayList<>();
        String[] studentData;

        try (Scanner fileScan = new Scanner(new File("bin/queststore/csv/student.csv"))) {

            while(fileScan.hasNextLine()) {
                studentData = fileScan.nextLine().split("\\|");
                String name = studentData[1];
                Integer id = Integer.parseInt(studentData[0]);
                String login = studentData[2];
                String password = studentData[3];
                String email = studentData[4];

                Integer classId = Integer.parseInt(studentData[5]);
                SchoolClass clas = classDao.getSchoolClass(classId);

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
        SchoolClass clas = student.getClas();
        clas.addStudent(student);
    }

    public void save() {
        try (Formatter writer = new Formatter("bin/queststore/csv/student.csv")) {

            for(Student student: this.students) {
                String lineToSave = student.getStudentSaveString();
                writer.format(lineToSave);
            }

        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
