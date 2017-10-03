package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ClassDao;
import com.codecool.krk.lucidmotors.queststore.dao.MentorDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.exceptions.LoginInUseException;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import java.util.ArrayList;

public class ManagerController extends AbstractUserController<Manager> {

    protected void handleUserRequest(String choice) throws DaoException {

        switch (choice) {

            case "1":
                addMentor();
                break;

            case "2":
                createClass();
                break;

            case "3":
                editMentor();
                break;

            case "4":
                showMentorsClass();
                break;

            case "5":
                startExperienceLevelController();
                break;

            case "0":
                break;

            default:
                handleNoSuchCommand();
                break;
        }
    }

    protected void showMenu() {
        userInterface.printManagerMenu();
    }

    private void addMentor() throws DaoException {

        String[] questions = {"Name: ", "Login: ", "Password: ", "Email: "};
        String[] expectedTypes = {"String", "String", "String", "String"};

        ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
        String name = basicUserData.get(0);
        String login = basicUserData.get(1);
        String password = basicUserData.get(2);
        String email = basicUserData.get(3);
        SchoolClass choosenClass = chooseProperClass();

        try {
            this.school.isLoginAvailable(login);
            Mentor mentor = new Mentor(name, login, password, email, choosenClass);
            mentor.save();

        } catch (LoginInUseException e) {
            userInterface.println(e.getMessage());
        }

        this.userInterface.pause();
    }

    private SchoolClass chooseProperClass() throws DaoException {

        ArrayList<SchoolClass> allClasses = this.school.getAllClasses();
        int userChoice;

        do {
            showAvailableClasses(allClasses);
            userChoice = getUserChoice() - 1;

        } while (userChoice > (allClasses.size() - 1) || userChoice < 0);

        return allClasses.get(userChoice);
    }

    private Integer getUserChoice() {
        String[] questions = {"Please choose class: "};
        String[] expectedTypes = {"integer"};
        ArrayList<String> userInput = userInterface.inputs.getValidatedInputs(questions, expectedTypes);

        return Integer.parseInt(userInput.get(0));
    }

    private Integer getUserChoiceOfMentor() {
        String[] questions = {"Please choose mentor: "};
        String[] expectedTypes = {"integer"};
        ArrayList<String> userInput = userInterface.inputs.getValidatedInputs(questions, expectedTypes);

        return Integer.parseInt(userInput.get(0));
    }

    private void showAvailableClasses(ArrayList<SchoolClass> allClasses) {
        userInterface.println("");

        for (int i = 0; i < allClasses.size(); i++) {
            String index = Integer.toString(i + 1);
            System.out.println(index + ". " + allClasses.get(i));
        }

        userInterface.println("");
    }

    private void createClass() throws DaoException {

        userInterface.println("Provide name for new class:");
        String name = userInterface.inputs.getInput("name: ");

        SchoolClass schoolClass = new SchoolClass(name);
        schoolClass.save();

        userInterface.println(String.format("Class %s created.", name));

        this.userInterface.pause();
    }

    private void editMentor() throws DaoException {

        this.printAllMentors();

        Integer mentorId = getUserChoiceOfMentor();
        Mentor mentor = new MentorDao(new ClassDao()).getMentor(mentorId);

        String[] questions = {"New name: ", "New login: ", "New password: ", "New email: "};
        String[] expectedTypes = {"String", "String", "String", "String"};

        if (mentor != null) {
            ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
            updateMentorRecord(basicUserData, mentor);
        } else {
            this.userInterface.println("There is no mentor with provided id!");
        }

        userInterface.pause();
    }

    private void updateMentorRecord(ArrayList<String> userData, Mentor mentor) throws DaoException {
        String name = userData.get(0);
        String login = userData.get(1);
        String password = userData.get(2);
        String email = userData.get(3);

        try {
            this.school.isLoginAvailable(login);
            mentor.setName(name);
            mentor.setLogin(login);
            mentor.setPassword(password);
            mentor.setEmail(email);
            mentor.update();

        } catch (LoginInUseException e) {
            userInterface.println(e.getMessage());
        }
    }

    private void showMentorsClass() throws DaoException {

        this.printAllMentors();

        Integer mentorId = this.getUserChoiceOfMentor();
        Mentor mentor = school.getMentor(mentorId);

        if (mentor == null) {
            userInterface.println("There is no user of such id!");
        } else {
            this.printMentorInfo(mentor);
        }

        userInterface.pause();
    }

    private void printAllMentors() throws DaoException {

        userInterface.println("List of existing mentors: ");

        ArrayList<Mentor> mentors = this.school.getAllMentors();
        for (Mentor mentor : mentors) {
            userInterface.println(mentor.toString());
        }
    }

    private void printMentorInfo(Mentor mentor) throws DaoException {

        SchoolClass schoolClass = mentor.getClas();
        ArrayList<Student> students = schoolClass.getAllStudents();

        userInterface.println("Chosen mentor info: ");
        userInterface.println(mentor.getMentorData());
        userInterface.println("\nList of this mentor students: ");

        for (Student student : students) {
            userInterface.println(student.toString());
        }
    }

    private void startExperienceLevelController() throws DaoException {

        new ExperienceLevelsController().startController(this.user, this.school);
    }

    private void handleNoSuchCommand() {

        userInterface.println("No such option.");
    }
}
