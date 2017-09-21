package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ArtifactOwnersDao;
import com.codecool.krk.lucidmotors.queststore.dao.BoughtArtifactDao;
import com.codecool.krk.lucidmotors.queststore.dao.ShopArtifactDao;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

public class StudentStoreController implements UserController {

    private final ShopArtifactController shopArtifactController = new ShopArtifactController();
    private final UserInterface userInterface = new UserInterface();
    private Student user;
    private School school;

    public void startController(User user, School school) {

        this.user = (Student) user;
        this.school = school;
        String userChoice = "";

        while (!userChoice.equals("0")) {

            this.userInterface.printStudentStoreMenu();
            userChoice = this.userInterface.inputs.getInput("Provide options: ");
            handleUserRequest(userChoice);

            school.save();
        }
    }

    private void handleUserRequest(String choice) {

        switch (choice) {

            case "1":
                shopArtifactController.showAvailableArtifacts();
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

    private void buyArtifact( ) {
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

        this.userInterface.lockActualState();
    }

    private ShopArtifact getShopArtifact( ) throws NumberFormatException {

        this.userInterface.println("Provide artifact id");
        String input = this.userInterface.inputs.getInput("artifact id:");
        Integer artifact_id = Integer.parseInt(input);

        return new ShopArtifactDao().getArtifact(artifact_id);
    }

    private void makePurchase(ShopArtifact shopArtifact) {

        BoughtArtifact boughtArtifact = new BoughtArtifact(shopArtifact);
        new BoughtArtifactDao().updateArtifact(boughtArtifact);

        this.user.substractCoins(boughtArtifact.getPrice());
        this.userInterface.println(String.format("Bought artifact: %s", boughtArtifact.getName()));

        new ArtifactOwnersDao().update(this.user, boughtArtifact);
    }

    private void handleNoSuchCommand( ) {

        userInterface.println("No such option.");
    }
}
