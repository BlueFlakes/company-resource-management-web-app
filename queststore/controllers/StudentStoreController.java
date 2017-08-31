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

        this.userInterface.lockActualState();
    }

    private void showAvailableArtifacts() {

        this.userInterface.lockActualState();
    }

    private void handleNoSuchCommand() {
        userInterface.println("No such option.");
    }
}
