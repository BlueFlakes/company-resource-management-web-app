package queststore.controllers;

import queststore.interfaces.UserController;
import queststore.models.User;
import queststore.models.School;
import queststore.views.UserInterface;

public class StudentController {
    User user;
    School school;
    UserInterface userInterface = new UserInterface();

    public void startController(User user, School school) {
        this.user = user;
        this.school = school;

        String userChoice = "";
        while(!userChoice.equals("0")){
            this.userInterface.printStudentMenu();
            userChoice = this.userInterface.getInput("Provide options");
            handleUserRequest(userChoice);
            this.userInterface.getInput("Press enter to continue...");
            school.save();
        }
    }

    private void showWallet() {
    }

    private void showLevel() {
    }
}
