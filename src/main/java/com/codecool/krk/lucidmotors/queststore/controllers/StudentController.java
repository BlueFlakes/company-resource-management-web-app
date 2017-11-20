//package com.codecool.krk.lucidmotors.queststore.controllers;
//
//import com.codecool.krk.lucidmotors.queststore.dao.AchievedQuestDao;
//import com.codecool.krk.lucidmotors.queststore.dao.ArtifactOwnersDao;
//import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
//import com.codecool.krk.lucidmotors.queststore.enums.StudentControllerMenuOptions;
//import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
//import com.codecool.krk.lucidmotors.queststore.models.Student;
//
//
//public class StudentController extends AbstractUserController<Student> {
//
//    //private final StudentView studentView = null;
//
//    protected void handleUserRequest(String userChoice) throws DaoException {
//
//        StudentControllerMenuOptions chosenOption = getEnumValue(userChoice);
//
//        switch (chosenOption) {
//
//            case START_STORE_CONTROLLER:
//                startStoreController();
//                break;
//
//            case SHOW_LEVEL:
//                showLevel();
//                break;
//
//            case SHOW_WALLET:
//                showWallet();
//                break;
//
//            case EXIT:
//                break;
//
//            case DEFAULT:
//                handleNoSuchCommand();
//                break;
//        }
//    }
//
//    private StudentControllerMenuOptions getEnumValue(String userChoice) {
//        StudentControllerMenuOptions chosenOption;
//
//        try {
//            chosenOption = StudentControllerMenuOptions.values()[Integer.parseInt(userChoice)];
//        } catch (IndexOutOfBoundsException | NumberFormatException e) {
//            chosenOption = StudentControllerMenuOptions.DEFAULT;
//        }
//
//        return chosenOption;
//    }
//
//    protected void showMenu() {
//        this.studentView.printStudentMenu();
//    }

//
//    private void showLevel() throws DaoException {
//
//        Integer level = new ExperienceLevelsDao().getExperienceLevels().computeStudentLevel(this.user.getEarnedCoins());
//        this.userInterface.println(String.format("Your level: %d", level));
//        this.userInterface.pause();
//    }
//
//    private void startStoreController() throws DaoException {
//
//        new StudentStoreController().startController(this.user, this.school);
//    }
//}


package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ArtifactOwnersDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.BoughtArtifact;
import com.codecool.krk.lucidmotors.queststore.models.User;

import java.util.List;

public class StudentController {

    private ArtifactOwnersDao artifactOwnersDao;

    public StudentController() throws DaoException {
        this.artifactOwnersDao = new ArtifactOwnersDao();
    }

    public List<BoughtArtifact> getWallet(User student) throws DaoException {
        return this.artifactOwnersDao.getArtifacts(student);
    }
}