package queststore.dao;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Formatter;

import queststore.models.Mentor;
import queststore.models.SchoolClass;

public class MentorDao {

    private ArrayList<Mentor> mentors;

    public MentorDao(ClassDao classDao) {
        this.mentors = readMentorsData(classDao);
    }

    private ArrayList<Mentor> readMentorsData(ClassDao classDao) {
        ArrayList<Mentor> loadedMentors= new ArrayList<>();
        String[] mentorData;

        try (Scanner fileScan = new Scanner(new File("bin/queststore/csv/mentor.csv"))) {

            while(fileScan.hasNextLine()) {
                mentorData = fileScan.nextLine().split("\\|");
                String name = mentorData[1];
                Integer id = Integer.parseInt(mentorData[0]);
                String login = mentorData[2];
                String password = mentorData[3];
                String email = mentorData[4];
                Integer classId = Integer.parseInt(mentorData[5]);
                SchoolClass clas = classDao.getSchoolClass(classId);

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

    public ArrayList<Mentor> getAllMentors() {
        return this.mentors;
    }

    public void addMentor(Mentor mentor) {
        this.mentors.add(mentor);
        SchoolClass clas = mentor.getClas();
        clas.addMentor(mentor);
    }

    public void save() {
        try (Formatter writer = new Formatter("bin/queststore/csv/mentor.csv")) {

            for(Mentor mentor: this.mentors) {
                String lineToSave = mentor.getMentorSaveString();
                writer.format(lineToSave);
            }

        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
