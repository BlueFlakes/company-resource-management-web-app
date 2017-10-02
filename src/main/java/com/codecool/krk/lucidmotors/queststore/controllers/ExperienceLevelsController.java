package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.User;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import java.util.ArrayList;

public class ExperienceLevelsController implements UserController {

    private final UserInterface userInterface = new UserInterface();
    private User user;
    private School school;

    public void startController(User user, School school) {

        this.user = user;
        this.school = school;
        String userChoice = "";

        while (!userChoice.equals("0")) {

            this.userInterface.printExperienceLevelsMenu();
            userChoice = this.userInterface.inputs.getInput("Provide options: ");
            handleUserRequest(userChoice);

        }
    }

    /**
     * Switches between menu options.
     *
     * @param choice
     */
    private void handleUserRequest(String choice) {

        switch (choice) {

            case "1":
                createNewLevel();
                break;

            case "2":
                updateLevel();
                break;

            case "0":
                break;

            default:
                userInterface.println("No such option.");
                break;
        }
    }

    /**
     * Gathers data about new level and insert it into database.
     */
    private void createNewLevel() {

        String[] questions = {"level: ", "needed coins: "};
        String[] types = {"integer", "integer"};
        ArrayList<String> answers = this.userInterface.inputs.getValidatedInputs(questions, types);
        // #TODO implement database connection
        this.userInterface.println("Level added.");
        this.userInterface.pause();
    }

    /**
     * Gathers new data about level and update it in database.
     */
    private void updateLevel() {

        String[] questions = {"level: ", "needed coins: "};
        String[] types = {"integer", "integer"};
        ArrayList<String> answers = this.userInterface.inputs.getValidatedInputs(questions, types);
        // #TODO implement database connection
        this.userInterface.println("Level Updated.");
        this.userInterface.pause();
    }
}
