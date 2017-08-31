package queststore.controllers;

import java.util.ArrayList;

import queststore.interfaces.UserController;
import queststore.models.User;
import queststore.models.School;
import queststore.models.Mentor;
import queststore.views.UserInterface;
import queststore.models.Class;
import queststore.models.Manager;
import queststore.exceptions.LoginInUseException;


public class ManagerController implements UserController {

    private Manager user;
    private School school;
    private UserInterface userInterface = new UserInterface();

    public void startController(User user, School school) {

        this.user = (Manager) user;
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
        String[] questions = {"Name", "Login", "Password", "Email"};
        String[] expectedTypes = {"String", "String", "String", "String"};

        ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
        String name = basicUserData.get(0);
        String login = basicUserData.get(1);
        String password = basicUserData.get(2);
        String email = basicUserData.get(3);
        Class choosenClass = chooseProperClass();

        try {
            this.school.addUser(new Mentor(name, login, password, email, choosenClass));
        } catch (LoginInUseException e) {
            userInterface.print(e.getMessage());
        }
    }

    private Class chooseProperClass() {
        ArrayList<Class> allClasses = this.school.getAllClasses();
        int userChoice;

        do {
            showAvailableClasses(allClasses);
            userChoice = getUserChoice() - 1;

        } while (userChoice > (allClasses.size() - 1) || userChoice < 0);

        return allClasses.get(userChoice);
    }

    private Integer getUserChoice() {
        String[] questions = {"Please choose class"};
        String[] expectedTypes = {"integer"};
        ArrayList<String> userInput = userInterface.inputs.getValidatedInputs(questions, expectedTypes);

        return Integer.parseInt(userInput.get(0));
    }

    private void showAvailableClasses(ArrayList<Class> allClasses) {
        userInterface.print("");

        for (int i = 0; i < allClasses.size(); i++) {
            String index = Integer.toString(i+1);
            System.out.println(index + ". " + allClasses.get(i));
        }

        userInterface.print("");
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
