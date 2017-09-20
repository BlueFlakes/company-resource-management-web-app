package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.exceptions.InvalidArgumentException;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;

import com.codecool.krk.lucidmotors.queststore.models.User;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.Student;

import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

public class StudentStoreController implements UserController {

    private User user;
    private School school;
    private UserInterface userInterface = new UserInterface();

    public void startController(User user, School school) {

        this.user = (Student) user;
        this.school = school;

        String userChoice = "";
        while(!userChoice.equals("0")) {
            this.userInterface.printStudentStoreMenu();
            userChoice = this.userInterface.inputs.getInput("Provide options: ");
            handleUserRequest(userChoice);

            school.save();
        }
    }

     private void handleUserRequest(String choice) {

        switch(choice){
            case "1":
                showAvailableArtifacts();
                break;

            case "2":
                buyArtifact();
                break;

            case "0":
                break;

            default:
                handleNoSuchCommand();
                break;

        }
     }

    private void buyArtifact() {
        this.userInterface.println("Provide artifact id");
        String input = this.userInterface.inputs.getInput("artifact id:");
        try {
            Integer artifact_id = Integer.parseInt(input);
            this.userInterface.println("Artifact bought");
        } catch (NumberFormatException e) {
            this.userInterface.println("Wrong artifact id.");
        }

        this.userInterface.lockActualState();
    }

    private void showAvailableArtifacts() {
        this.userInterface.printStoreArtifacts();
        this.userInterface.lockActualState();
    }

    private void handleNoSuchCommand() {
        userInterface.println("No such option.");
    }
}
