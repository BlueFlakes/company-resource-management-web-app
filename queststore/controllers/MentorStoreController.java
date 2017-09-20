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
            this.userInterface.printMentorStoreMenu();
            userChoice = userInterface.inputs.getInput("What do you want to do: ");
            handleUserRequest(userChoice);

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
        this.userInterface.printStoreArtifacts();

        this.userInterface.lockActualState();
    }

    private void addArtifact() {
        String[] questions = {"Name: ", "Price: ", "Artifact category: "};
        String[] types = {"string", "integer", "string"};
        this.userInterface.inputs.getValidatedInputs(questions, types);

        this.userInterface.lockActualState();
    }

    private void updateArtifact() {
        this.getArtifactId();
        String[] questions = {"New name: ", "New price: ", "New artifact category: "};
        String[] types = {"string", "integer", "string"};
        this.userInterface.inputs.getValidatedInputs(questions, types);


        this.userInterface.lockActualState();
    }

    private Integer getArtifactId() {
        String[] question = {"Provide artifact id: "};
        String[] type = {"integer"};

        Integer id = Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
        return id;
    }

    private void addArtifactCategory() {
        System.out.println("Add artifact category");

        this.userInterface.lockActualState();
    }

    private void handleNoSuchCommand() {
        userInterface.println("Wrong command!");

        this.userInterface.lockActualState();
    }
}
