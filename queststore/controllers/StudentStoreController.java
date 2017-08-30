package queststore.controllers;

import queststore.interfaces.UserController;
import queststore.models.User;
import queststore.models.School;
import queststore.views.UserInterface;

public class StudentStoreController implements UserController {
    User user;
    School school;
    UserInterface userInterface = new UserInterface();
    
    public void startController(User user, School school) {
        this.user = user;
        this.school = school;

        String userChoice = "";
        while(!userChoice.equals("0")){
            this.userInterface.printStudentStoreMenu();
            userChoice = this.userInterface.getInput("Provide options");
            handleUserRequest(userChoice);
            if(!userChoice.equals("0")){
                this.userInterface.getInput("Press enter to continue...");
            }
            school.save();
        }
    }

     private void handleUserRequest(String choice){
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
