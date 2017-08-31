package queststore.controllers;

import queststore.interfaces.UserController;

import queststore.models.User;
import queststore.models.School;

import queststore.views.UserInterface;
import queststore.models.Student;

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
            userChoice = this.userInterface.inputs.getInput("Provide options");
            handleUserRequest(userChoice);

            if (!userChoice.equals("0")) {
                this.userInterface.inputs.getInput("Press enter to continue...");
            }
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
                userInterface.print("No such option.");
                break;

        }
     }

    private void buyArtifact() {
    }

    private void showAvailableArtifacts() {
    }
}
