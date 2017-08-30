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

    private void handleUserRequest(String choice){
        switch(choice){
            case "1":
                startStoreController();
            break;

            case "2":
                showLevel();
            break;
            
            case "3":
                showWallet();
            break;

            case "0":
            break;

            default:
                userInterface.print("No such option.");
            break;
        }
    }

    private void showWallet() {
        userInterface.print("Here you will see your CC balance and yours artifacts");
    }

    private void showLevel() {
        userInterface.print("Here will be see your level");
    }

    private void startStoreController(){

    }
}
