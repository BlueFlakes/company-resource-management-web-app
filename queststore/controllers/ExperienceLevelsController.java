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
            userChoice = this.userInterface.getInput("Provide options");
            handleUserRequest(userChoice);
            if(!userChoice.equals("0")) {
                this.userInterface.getInput("Press enter to continue...");
            }
            school.save();
        }
    }

    public void startController() {
    }

    private void createNewLevel() {
    }

    private void updateLevel() {
    }
}
