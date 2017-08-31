package queststore.controllers;

import queststore.interfaces.UserController;

import queststore.models.User;
import queststore.models.School;

import queststore.views.UserInterface;

public class ManagerController implements UserController {

    private User user;
    private School school;
    private UserInterface userInterface = new UserInterface();

    public void startController(User user, School school) {

        this.user = user;
        this.school = school;

        String userChoice = "";
        while (!userChoice.equals("0")) {
            this.userInterface.printManagerMenu();
            userChoice = this.userInterface.inputs.getInput("Provide options");
            handleUserRequest(userChoice);

            this.userInterface.inputs.getInput("Press enter to continue...");
            school.save();
        }
    }

    private void handleUserRequest(String choice) {

        switch(choice) {
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
                userInterface.print("No such option.");
                break;
        }
    }

    private void addMentor() {
        userInterface.print("Here will be adding mentor");
    }

    private void createClass() {
        userInterface.print("Here will be creating class");
    }

    private void editMentor() {
        userInterface.print("Here will be editing mentor");
    }

    private void showMentorsClass() {
        userInterface.print("Here will be showing mentors class");
    }

    private void startExperienceLevelController(){
        new ExperienceLevelsController().startController(this.user, this.school);
    }
}
