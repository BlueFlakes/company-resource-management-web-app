package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.AchievedQuestDao;
import com.codecool.krk.lucidmotors.queststore.dao.ArtifactOwnersDao;
import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
import com.codecool.krk.lucidmotors.queststore.dao.StudentDao;
import com.codecool.krk.lucidmotors.queststore.enums.StudentControllerMenuOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.ExperienceLevels;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.User;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import java.sql.SQLException;


public class StudentController extends AbstractUserController<Student> {

    protected void handleUserRequest(String userChoice) throws DaoException {

        StudentControllerMenuOptions chosenOption = getEnumValue(userChoice);

        switch (chosenOption) {

            case START_STORE_CONTROLLER:
                startStoreController();
                break;

            case SHOW_LEVEL:
                showLevel();
                break;

            case SHOW_WALLET:
                showWallet();
                break;

            case EXIT:
                break;

            case DEFAULT:
                handleNoSuchCommand();
                break;
        }
    }

    private StudentControllerMenuOptions getEnumValue(String userChoice) {
        StudentControllerMenuOptions chosenOption;

        try {
            chosenOption = StudentControllerMenuOptions.values()[Integer.parseInt(userChoice)];
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            chosenOption = StudentControllerMenuOptions.DEFAULT;
        }

        return chosenOption;
    }

    protected void showMenu() {
        userInterface.printStudentMenu();
    }

    private void showWallet() throws DaoException {

        String accountBalance = Integer.toString(this.user.getPossesedCoins());
        userInterface.println("Balance: " + accountBalance);
        userInterface.printBoughtArtifacts(this.user, new ArtifactOwnersDao().getArtifacts(this.user));
        userInterface.println("Achieved quests: ");

        userInterface.printAchievedQuests(new AchievedQuestDao().getAllQuestsByStudent(this.user));

        this.userInterface.pause();
    }

    private void showLevel() throws DaoException {

        Integer level = new ExperienceLevelsDao().getExperienceLevels().computeStudentLevel(this.user.getEarnedCoins());
        this.userInterface.println(String.format("Your level: %d", level));
        this.userInterface.pause();
    }

    private void startStoreController() throws DaoException {

        new StudentStoreController().startController(this.user, this.school);
    }
}
