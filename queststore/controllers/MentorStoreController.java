package queststore.controllers;

import queststore.interfaces.UserController;
import queststore.views.UserInterface;

import queststore.models.School;
import queststore.models.User;
import queststore.models.Mentor;

public class MentorStoreController implements UserController {
    private UserInterface userInterface = new UserInterface();
    private User user;
    private School school;

    public void startController(User user, School school) {

        this.user = (Mentor) user;
        this.school = school;
        String userChoice = "";

        while (!userChoice.equals("0")) {
            this.userInterface.printMentorMenu();
            userChoice = userInterface.inputs.getInput("What do you want to do");
            handleUserRequest(userChoice);
            userInterface.lockActualState();
            school.save();
        }
    }

    private void handleUserRequest(String userChoice) {

        switch(userChoice) {
            case "1":
                showAvailableArtifacts();
                break;

            case "2":
                addArtifact();
                break;

            case "3":
                updateArtifact();
                break;

            case "4":
                addArtifactCategory();
                break;

            case "0":
                break;

            default:
                handleNoSuchCommand();
        }
    }

    private void showAvailableArtifacts() {
        System.out.println("Available Artifacts.");
    }

    private void addArtifact() {
        System.out.println("Add artifact.");
    }

    private void updateArtifact() {
        System.out.println("update artifact");
    }

    private void addArtifactCategory() {
        System.out.println("Add artifact category");
    }

    private void handleNoSuchCommand() {
        userInterface.print("Wrong command!");
    }
}
