package queststore.controllers;

import java.util.ArrayList;

import queststore.models.User;
import queststore.models.School;
import queststore.models.Class;
import queststore.models.Student;
import queststore.views.UserInterface;


public class MentorController {
    private UserInterface userInterface = new UserInterface();
    private User user;
    private School school;

    public void startController(User user, School school) {

        this.user = user;
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
                addStudent();
                break;

            case "2":
                addQuest();
                break;

            case "3":
                addQuestCategory();
                break;

            case "4":
                updateQuest();
                break;

            case "5":
                markBoughtArtifactsAsUsed();
                break;

            case "6":
                runMentorStoreController();

            case "0":
                break;

            default:
                handleNoSuchCommand();
        }
    }

    private void addStudent() {
        String[] questions = {"Name", "Login", "Password", "Email"};
        String[] expectedTypes = {"String", "String", "String", "String"};

        ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
        String name = basicUserData.get(0);
        String login = basicUserData.get(1);
        String password = basicUserData.get(2);
        String email = basicUserData.get(3);
        Class choosenClass = chooseProperClass();

        this.school.addUser(new Student(name, login, password, email, choosenClass));
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

    private void addQuest() {
        userInterface.print("Here you will create new quests");
    }

    private void addQuestCategory() {
        userInterface.print("Here you will create new quest category");
    }

    private void updateQuest() {
        userInterface.print("Here you will change quest details");
    }

    private void markBoughtArtifactsAsUsed() {
        userInterface.print("Here you will mark students artifacts as used");
    }

    private void runMentorStoreController() {
        new MentorStoreController().startController(this.user, this.school);
    }

    private void handleNoSuchCommand() {
        userInterface.print("Wrong command!");
    }

}
