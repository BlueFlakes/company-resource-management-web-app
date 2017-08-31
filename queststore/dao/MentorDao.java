package queststore.dao;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;

import queststore.models.Mentor;
import queststore.models.Class;

public class MentorDao {

    private ArrayList<Mentor> mentors;

    public MentorDao(ClassDao classDao) {
        this.mentors = readMentorsData(classDao);
    }

    private ArrayList<Mentor> readMentorsData(ClassDao classDao) {
        ArrayList<Mentor> loadedMentors= new ArrayList<>();
        String[] mentorData;
        Scanner fileScan;

        try {
            fileScan = new Scanner(new File("queststore/csv/mentor.csv"));

            while(fileScan.hasNextLine()) {
                mentorData = fileScan.nextLine().split("\\|");
                String name = mentorData[1];
                Integer id = Integer.parseInt(mentorData[0]);
                String login = mentorData[2];
                String password = mentorData[3];
                String email = mentorData[4];
                Integer classId = Integer.parseInt(mentorData[5]);
                Class clas = classDao.getClass(classId);

                Mentor mentor = new Mentor(name, login, password, email, clas, id);
                loadedMentors.add(mentor);
                clas.addMentor(mentor);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File mentor.csv not found!");
        }

        return loadedMentors;
    }

    public Mentor getMentor(Integer id) {

        for (Mentor mentor : this.mentors) {
            if (mentor.getId() == id) {
                return mentor;
            }
        }

        return null;
    }

    public Mentor getMentor(String login) {

        for(Mentor mentor : this.mentors) {
            if (mentor.getLogin().equals(login)) {
                return mentor;
            }
        }

        return null;
    }

    public void addMentor(Mentor mentor) {
        this.mentors.add(mentor);
        Class clas = mentor.getClas();
        clas.addMentor(mentor);
    }

    public void save() {
    }

    public static void main(String[] args) {
        ClassDao classDao = new ClassDao();
        MentorDao mentorDao = new MentorDao(classDao);
        Mentor mentor = mentorDao.getMentor("sharp");
        System.out.println(mentor.getName());
        Class clas = classDao.getClass("klasa druga");
        System.out.println(clas.getAllMentors().get(2).getName());
        System.out.println(clas.getAllMentors());
        Class clas1 = classDao.getClass("klasa pierwsza");
        System.out.println(clas1.getAllMentors().get(1).getName());

        //System.out.println(classDao.classes.get(1).getId());
    }
}
