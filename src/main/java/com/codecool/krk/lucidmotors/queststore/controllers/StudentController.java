package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.models.User;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.Student;

import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;

public class StudentController implements UserController {

    private Student user;
    School school;
    UserInterface userInterface = new UserInterface();

    public void startController(User user, School school) {

        this.user = (Student) user;
        this.school = school;

        String userChoice = "";
        while (!userChoice.equals("0")) {
            this.userInterface.printStudentMenu();
            userChoice = this.userInterface.inputs.getInput("Provide options: ");
            handleUserRequest(userChoice);

            school.save();
        }
    }

    private void handleUserRequest(String choice) {
        switch (choice) {
            case "1":
                startStoreController();
                break;

            case "2":
                showLevel();
                break;

            case "3":
                showWallet();
                break;

            case "0":
                break;

            default:
                handleNoSuchCommand();
                break;
        }
    }

    private void showWallet() {
        String accountBalance = Integer.toString(this.user.getPossesedCoins());
        userInterface.println("Balance: " + accountBalance);
        userInterface.printBoughtArtifacts(this.user);
        this.userInterface.lockActualState();
    }

    private void showLevel() {
        userInterface.println("Your level: 0");

        this.userInterface.lockActualState();
    }

    private void handleNoSuchCommand() {
        userInterface.println("No such option.");
    }

    private void startStoreController() {
        new StudentStoreController().startController(this.user, this.school);
    }
}
