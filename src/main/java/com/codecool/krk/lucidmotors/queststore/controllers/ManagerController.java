package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.exceptions.LoginInUseException;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import java.util.ArrayList;
import java.sql.SQLException;

public class ManagerController implements UserController {

    private final UserInterface userInterface = new UserInterface();
    private Manager user;
    private School school;

    public void startController(User user, School school) throws SQLException {

        this.user = (Manager) user;
        this.school = school;
        String userChoice = "";

        while (!userChoice.equals("0")) {

            this.userInterface.printManagerMenu();
            userChoice = this.userInterface.inputs.getInput("Provide options: ");
            handleUserRequest(userChoice);

        }
    }

    private void handleUserRequest(String choice) throws SQLException {

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

    private void addMentor() throws SQLException {

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
            new Mentor(name, login, password, email, choosenClass);

        } catch (LoginInUseException e) {
            userInterface.println(e.getMessage());
        }

        this.userInterface.lockActualState();
    }

    private SchoolClass chooseProperClass() throws SQLException {

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

    private void createClass() throws SQLException {

        userInterface.println("Provide name for new class:");
        String name = userInterface.inputs.getInput("name: ");

        new SchoolClass(name);
        userInterface.println(String.format("Class %s created.", name));

        this.userInterface.lockActualState();
    }

    private void editMentor() throws SQLException {

        this.printAllMentors();

        Integer mentorId = getUserChoiceOfMentor();
        String[] questions = {"New name: ", "New login: ", "New password: ", "New email: "};
        String[] expectedTypes = {"String", "String", "String", "String"};

        ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
    }

    private void showMentorsClass() throws SQLException {

        this.printAllMentors();

        Integer mentorId = this.getUserChoiceOfMentor();
        Mentor mentor = school.getMentor(mentorId);

        if (mentor == null) {
            userInterface.println("There is no user of such id!");
        } else {
            this.printMentorInfo(mentor);
        }

        userInterface.lockActualState();
    }

    private void printAllMentors() throws SQLException {

        userInterface.println("List of existing mentors: ");

        ArrayList<Mentor> mentors = this.school.getAllMentors();
        for (Mentor mentor : mentors) {
            userInterface.println(mentor.toString());
        }
    }

    private void printMentorInfo(Mentor mentor) throws SQLException {

        SchoolClass schoolClass = mentor.getClas();
        ArrayList<Student> students = schoolClass.getAllStudents();

        userInterface.println("Chosen mentor info: ");
        userInterface.println(mentor.getMentorData());
        userInterface.println("\nList of this mentor students: ");

        for (Student student : students) {
            userInterface.println(student.toString());
        }
    }

    private void startExperienceLevelController() {

        new ExperienceLevelsController().startController(this.user, this.school);
    }

    private void handleNoSuchCommand() {

        userInterface.println("No such option.");
    }
}
