package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.BoughtArtifactDao;
import com.codecool.krk.lucidmotors.queststore.dao.ShopArtifactDao;
import com.codecool.krk.lucidmotors.queststore.enums.StudentStoreMenuOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import java.util.ArrayList;

public class StudentStoreController extends AbstractUserController<Student> {

    private final ShopArtifactController shopArtifactController = new ShopArtifactController();

    protected void handleUserRequest(String userChoice) throws DaoException {

        StudentStoreMenuOptions chosenOption = getEnumValue(userChoice);

        switch (chosenOption) {

            case SHOW_AVAILABLE_ARTIFACTS:
                shopArtifactController.showAvailableArtifacts();
                break;

            case BUY_ARTIFACT:
                buyArtifact();
                break;

            case EXIT:
                break;

            case DEFAULT:
                handleNoSuchCommand();
                break;
        }
    }

    private StudentStoreMenuOptions getEnumValue(String userChoice) {
        StudentStoreMenuOptions chosenOption;

        try {
            chosenOption = StudentStoreMenuOptions.values()[Integer.parseInt(userChoice)];
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            chosenOption = StudentStoreMenuOptions.DEFAULT;
        }

        return chosenOption;
    }

    protected void showMenu() {
        userInterface.printStudentStoreMenu();
    }

    private void buyArtifact() throws DaoException {
        /* #TODO refactor */
        this.userInterface.printStoreArtifacts(new ShopArtifactDao().getAllArtifacts());

        try {
            // # TODO check is student have enough cc

            ShopArtifact shopArtifact = this.getShopArtifact();
            Integer studentBalance = this.user.getPossesedCoins();

            if (shopArtifact == null) {
                this.userInterface.println("No such artifact");

            } else if (studentBalance < shopArtifact.getPrice()) {
                this.userInterface.println("Not enough money");

            } else {
                this.makePurchase(shopArtifact);
            }

        } catch (NumberFormatException e) {
            this.userInterface.println("Wrong artifact id.");
        }

        this.userInterface.pause();
    }

    private ShopArtifact getShopArtifact() throws NumberFormatException, DaoException {

        this.userInterface.println("Provide artifact id");
        String input = this.userInterface.inputs.getInput("artifact id:");
        Integer artifact_id = Integer.parseInt(input);

        return new ShopArtifactDao().getArtifact(artifact_id);
    }

    private void makePurchase(ShopArtifact shopArtifact) throws DaoException {

        BoughtArtifact boughtArtifact = new BoughtArtifact(shopArtifact);

        this.user.substractCoins(boughtArtifact.getPrice());
        this.user.update();
        this.userInterface.println(String.format("Bought artifact: %s", boughtArtifact.getName()));

        ArrayList<Student> owners = new ArrayList<>();
        owners.add(this.user);

        new BoughtArtifactDao().save(boughtArtifact, owners);
    }

    private void handleNoSuchCommand() {

        userInterface.println("No such option.");
    }
}
