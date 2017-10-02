package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ArtifactCategoryDao;
import com.codecool.krk.lucidmotors.queststore.dao.ShopArtifactDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


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

        String[] questions = {"Name: ", "Description: ", "Price: "};
        String[] types = {"string", "string", "integer"};

        ArrayList<String> givenValues = this.userInterface.inputs.getValidatedInputs(questions, types);

        Integer artifactCategoryId = this.getArtifactCategoryId();
        ArtifactCategory artifactCategory = new ArtifactCategoryDao().getArtifactCategory(artifactCategoryId);
        String name = givenValues.get(0);
        String description = givenValues.get(1);
        Integer price = Integer.parseInt(givenValues.get(2));

        try {
            ShopArtifact shopArtifact = new ShopArtifact(name, price, artifactCategory, description);
            shopArtifact.save();
            this.userInterface.println("Artifact created.");
        } catch (IllegalArgumentException e) {
            this.userInterface.println(e.getMessage());
        }

        this.userInterface.lockActualState();
    }

    private Integer getArtifactCategoryId() throws DaoException {
        this.printAllArtifactsCategories();
        String[] questions = {"Artifact category id: "};
        String[] types = {"integer"};
        return Integer.parseInt(this.userInterface.inputs.getValidatedInputs(questions, types)
                      .get(0));
    }

    private void printAllArtifactsCategories() throws DaoException {

        ArrayList<ArtifactCategory> artifactCategories = new ArtifactCategoryDao().getAllArtifactCategories();
        this.userInterface.printArtifactsCategories(artifactCategories);
    }

    private void updateArtifact() throws DaoException {
        ShopArtifactDao shopArtifactDao = new ShopArtifactDao();

        this.userInterface.printStoreArtifacts(shopArtifactDao.getAllArtifacts());
        ShopArtifact updatedArtifact = shopArtifactDao.getArtifact(this.getArtifactId());

        String[] questions = {"Name: ", "Description: ", "Price: "};
        String[] types = {"string", "string", "integer"};
        ArrayList<String> givenValues = this.userInterface.inputs.getValidatedInputs(questions, types);

        Integer artifactCategoryId = this.getArtifactCategoryId();
        ArtifactCategory artifactCategory = new ArtifactCategoryDao().getArtifactCategory(artifactCategoryId);

        updatedArtifact.setName(givenValues.get(0));
        updatedArtifact.setDescription(givenValues.get(1));
        updatedArtifact.setPrice(Integer.parseInt(givenValues.get(2)));
        updatedArtifact.setArtifactCategory(artifactCategory);

        shopArtifactDao.updateArtifact(updatedArtifact);
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
