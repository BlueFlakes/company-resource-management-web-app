package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.enums.ExperienceLevelsMenuOptions;
import com.codecool.krk.lucidmotors.queststore.models.Mentor;

import java.util.ArrayList;

public class ExperienceLevelsController extends AbstractUserController<Mentor> {


    /**
     * Switches between menu options.
     *
     * @param choice
     */
    protected void handleUserRequest(String userChoice) {
        ExperienceLevelsMenuOptions chosenOption = getEnumValue(userChoice);

        switch (chosenOption) {

            case CREATE_NEW_LEVEL:
                createNewLevel();
                break;

            case UPDATE_LEVEL:
                updateLevel();
                break;

            case EXIT:
                break;

            case DEFAULT:
                handleNoSuchCommand();
                break;
        }
    }

    protected void showMenu() {
        userInterface.printExperienceLevelsMenu();
    }

    private ExperienceLevelsMenuOptions getEnumValue(String userChoice) {
        ExperienceLevelsMenuOptions chosenOption;

        try {
            chosenOption = ExperienceLevelsMenuOptions.values()[Integer.parseInt(userChoice)];
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            chosenOption = ExperienceLevelsMenuOptions.DEFAULT;
        }

        return chosenOption;
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
