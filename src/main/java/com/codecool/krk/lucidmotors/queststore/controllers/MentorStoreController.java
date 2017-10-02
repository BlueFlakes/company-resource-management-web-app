package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ArtifactCategoryDao;
import com.codecool.krk.lucidmotors.queststore.dao.ArtifactOwnersDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;

import java.util.ArrayList;


public class MentorStoreController implements UserController {

    private final ShopArtifactController shopArtifactController = new ShopArtifactController();
    private final UserInterface userInterface = new UserInterface();
    private User user;
    private School school;

    public void startController(User user, School school) throws DaoException {

        this.user = (Mentor) user;
        this.school = school;
        String userChoice = "";

        while (!userChoice.equals("0")) {

            this.userInterface.printMentorStoreMenu();
            userChoice = userInterface.inputs.getInput("What do you want to do: ");
            handleUserRequest(userChoice);

        }
    }

    private void handleUserRequest(String userChoice) throws DaoException {

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

    private void addArtifact() throws DaoException {

        this.printAllArtifacts();

        String[] questions = {"Artifact category id: ", "Name: ", "Description: ", "Price: "};
        String[] types = {"integer", "string", "string", "integer"};

        ArrayList<String> givenValues = this.userInterface.inputs.getValidatedInputs(questions, types);

        Integer artifactCategoryId = Integer.parseInt(givenValues.get(0));
        ArtifactCategory artifactCategory = new ArtifactCategoryDao().getArtifactCategory(artifactCategoryId);
        String name = givenValues.get(1);
        String description = givenValues.get(2);
        Integer price = Integer.parseInt(givenValues.get(3));

        this.createArtifact(artifactCategory, name, description, price);

        this.userInterface.lockActualState();
    }

    private void printAllArtifacts() throws DaoException {

        ArrayList<ArtifactCategory> artifactCategories = new ArtifactCategoryDao().getAllArtifactCategories();
        this.userInterface.printArtifactsCategories(artifactCategories);
    }

    private void createArtifact(ArtifactCategory artifactCategory, String name, String description, Integer price) throws DaoException{

        if(artifactCategory == null) {
            this.userInterface.println("Artifact creation failure: No such artifact category.");
        } else if(price < 0) {
            this.userInterface.println("Artifact creation failure: Price should be at least 0.");
        } else {
            ShopArtifact shopArtifact = new ShopArtifact(name, price, artifactCategory, description);
            shopArtifact.save();
            this.userInterface.println("Artifact created.");
        }
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

        return Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
    }

    private void addArtifactCategory() {

        String[] question = {"Provide new artifact category name: "};
        String[] type = {"string"};
        userInterface.inputs.getValidatedInputs(question, type).get(0);

        this.userInterface.lockActualState();
    }

    private void handleNoSuchCommand() {

        userInterface.println("Wrong command!");
        this.userInterface.lockActualState();
    }
}
