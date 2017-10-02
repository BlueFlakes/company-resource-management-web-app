package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.Mentor;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.User;


public class MentorStoreController extends AbstractController<Mentor> {

    private final ShopArtifactController shopArtifactController = new ShopArtifactController();


    protected void handleUserRequest(String userChoice) throws DaoException {

        switch (userChoice) {

            case "1":
                shopArtifactController.showAvailableArtifacts();
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

    protected void showMenu(String title) {
        userInterface.printMentorStoreMenu();
    }

    private void addArtifact() {

        String[] questions = {"Name: ", "Price: ", "Artifact category: "};
        String[] types = {"string", "integer", "string"};
        this.userInterface.inputs.getValidatedInputs(questions, types);

        this.userInterface.pause();
    }

    private void updateArtifact() {

        this.getArtifactId();
        String[] questions = {"New name: ", "New price: ", "New artifact category: "};
        String[] types = {"string", "integer", "string"};
        this.userInterface.inputs.getValidatedInputs(questions, types);

        this.userInterface.pause();
    }


    private Integer getArtifactId() {

        String[] question = {"Provide artifact id: "};
        String[] type = {"integer"};

        return Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
    }

    private void addArtifactCategory() {

        String[] question = {"Provide new artifact category name: "};
        String[] type = {"string"};
        userInterface.inputs.getValidatedInputs(question, type).get(0);

        this.userInterface.pause();
    }

    private void handleNoSuchCommand() {

        userInterface.println("Wrong command!");
        this.userInterface.pause();
    }
}
