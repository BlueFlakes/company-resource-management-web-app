package queststore.controllers;

import queststore.interfaces.UserController;
import queststore.models.User;
import queststore.models.School;
import queststore.views.UserInterface;

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
            if(!userChoice.equals("0")) {
                this.userInterface.inputs.getInput("Press enter to continue...");
            }
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
    }

    private void updateLevel() {
    }
}
