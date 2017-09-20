package queststore.controllers;

import queststore.interfaces.UserController;

import queststore.models.ExperienceLevels;
import queststore.models.User;
import queststore.models.School;

import queststore.views.UserInterface;

import java.util.ArrayList;

public class ExperienceLevelsController implements UserController {

    User user;
    School school;
    UserInterface userInterface = new UserInterface();

    public void startController(User user, School school) {

        this.user = user;
        this.school = school;

        String userChoice = "";
        while(!userChoice.equals("0")) {
            this.userInterface.printExperienceLevelsMenu();
            userChoice = this.userInterface.inputs.getInput("Provide options: ");
            handleUserRequest(userChoice);

            school.save();
        }
    }

    private void handleUserRequest(String choice) {

        switch(choice) {
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

    private void createNewLevel() {
        String[] questions = {"level: ", "needed coins: "};
        String[] types = {"integer", "integer"};
        ArrayList<String> answers = this.userInterface.inputs.getValidatedInputs(questions, types);

        this.userInterface.println("Level added.");
        this.userInterface.lockActualState();
    }

    private void updateLevel() {
        String[] questions = {"level: ", "needed coins: "};
        String[] types = {"integer", "integer"};
        ArrayList<String> answers = this.userInterface.inputs.getValidatedInputs(questions, types);

        this.userInterface.println("Level Updated.");
        this.userInterface.lockActualState();
    }
}
