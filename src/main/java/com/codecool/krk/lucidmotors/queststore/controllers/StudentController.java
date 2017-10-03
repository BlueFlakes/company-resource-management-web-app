package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.AchievedQuestDao;
import com.codecool.krk.lucidmotors.queststore.dao.ArtifactOwnersDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.User;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import java.sql.SQLException;


public class StudentController extends AbstractUserController<Student> {

    protected void handleUserRequest(String choice) throws DaoException {

        switch (choice) {

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
                handleNoSuchCommand();
                break;
        }
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

    private void showLevel() {

        userInterface.println("Your level: 0");
        this.userInterface.pause();
    }

    private void handleNoSuchCommand() {

        userInterface.println("No such option.");
    }

    private void startStoreController() throws DaoException {

        new StudentStoreController().startController(this.user, this.school);
    }
}
