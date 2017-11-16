package com.codecool.krk.lucidmotors.queststore.controllers;
//
//import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
//import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
//import com.codecool.krk.lucidmotors.queststore.models.ExperienceLevels;
//import com.codecool.krk.lucidmotors.queststore.enums.ExperienceLevelsMenuOptions;
//import com.codecool.krk.lucidmotors.queststore.models.Mentor;
//
//import java.util.ArrayList;
//
//public class ExperienceLevelsController extends AbstractUserController<Mentor> {
//
//    private ExperienceLevelsDao experienceLevelsDao = new ExperienceLevelsDao();
//    //private final ManagerView managerView = new ManagerView();
//
//    public ExperienceLevelsController() throws DaoException {
//
//    }
//
//    /**
//     * Switches between menu options.
//     *
//     * @param choice
//     */
//    protected void handleUserRequest(String userChoice) throws DaoException {
//        ExperienceLevelsMenuOptions chosenOption = getEnumValue(userChoice);
//        switch (chosenOption) {
//
//            case CREATE_NEW_LEVEL:
//                createNewLevel();
//                break;
//
//            case UPDATE_LEVEL:
//                updateLevel();
//                break;
//
//            case SHOW_LEVELS:
//                showLevels();
//                break;
//
//            case EXIT:
//                break;
//
//            case DEFAULT:
//                handleNoSuchCommand();
//                break;
//        }
//
//        //this.//userInterface.pause();
//    }
//
//    protected void showMenu() {
//        this.managerView.printExperienceLevelsMenu();
//    }
//
//    private ExperienceLevelsMenuOptions getEnumValue(String userChoice) {
//        ExperienceLevelsMenuOptions chosenOption;
//
//        try {
//            chosenOption = ExperienceLevelsMenuOptions.values()[Integer.parseInt(userChoice)];
//        } catch (IndexOutOfBoundsException | NumberFormatException e) {
//            chosenOption = ExperienceLevelsMenuOptions.DEFAULT;
//        }
//
//        return chosenOption;
//    }
//
//    /**
//     * Gathers data about new level and insert it into database.
//     */
//    private void createNewLevel() throws DaoException {
//        this.showLevels();
//
//        ExperienceLevels experienceLevels = this.experienceLevelsDao
//                                   .getExperienceLevels();
//
//
//        Integer nextLevel = experienceLevels.getLevels().size() + 1;
//
//        this.userInterface.println(String.format("Provide coins amount for level %d%n", nextLevel));
//        String[] questions = {"needed coins: "};
//        String[] types = {"integer"};
//        Integer coins = Integer.parseInt(this.userInterface.inputs.getValidatedInputs(questions, types).get(0));
//
//        experienceLevels.addLevel(coins, nextLevel);
//        if(experienceLevels.getLevels().size() == nextLevel) {
//
//            experienceLevels.updateExperienceLevels();
//            this.userInterface.println("Level added");
//        } else {
//            this.userInterface.println("Level addition failure!");
//        }
//
//
//    }
//
//    /**
//     * Gathers new data about level and update it in database.
//     */
//    private void updateLevel() throws DaoException {
//        ExperienceLevels experienceLevels = this.experienceLevelsDao.getExperienceLevels();
//
//        this.showLevels();
//
//        String[] questions = {"which level you want to update: ", "needed coins: "};
//        String[] types = {"integer", "integer"};
//        ArrayList<String> answers = this.userInterface.inputs.getValidatedInputs(questions, types);
//
//        Integer level = Integer.parseInt(answers.get(0));
//        Integer coins = Integer.parseInt(answers.get(1));
//
//
//        experienceLevels.updateLevel(coins, level);
//
//        if (experienceLevels.getLevels().get(level) == coins) {
//            experienceLevels.updateExperienceLevels();
//            this.userInterface.println("Level updated");
//        } else {
//            this.userInterface.println("Level update failure!");
//        }
//
//    }
//
//    private void showLevels() throws DaoException {
//
//        this.userInterface.println(this.experienceLevelsDao.getExperienceLevels().toString());
//
//    }
//}

import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ExperienceLevels;

import java.util.Map;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;

public class ExperienceLevelsController {
    private final ExperienceLevelsDao experienceLevelsDao;

    public ExperienceLevelsController() throws DaoException {
        this.experienceLevelsDao = new ExperienceLevelsDao();
    }

    public TreeMap<Integer, Integer> getLevels() throws DaoException {
        ExperienceLevels experienceLevels = this.experienceLevelsDao.getExperienceLevels();
        return experienceLevels.getLevels();
    }

    public boolean updateLevel(Map<String, String> formData, String coinsKey, String levelsKey) throws DaoException {
        ExperienceLevels experienceLevels = this.experienceLevelsDao.getExperienceLevels();

        int coins = parseInt(formData.get(coinsKey));
        int level = parseInt(formData.get(levelsKey));

        boolean wasSuccesfullyUpdated = experienceLevels.updateLevel(coins, level);

        if (experienceLevels.getLevels().get(level) == coins) {
            experienceLevels.updateExperienceLevels();
        } else {
            // handle wrong
        }

        return wasSuccesfullyUpdated;
    }

        public boolean createNewLevel(Map<String, String> formData, String coinsKey) throws DaoException {

        ExperienceLevels experienceLevels = this.experienceLevelsDao.getExperienceLevels();
        int coins = parseInt(formData.get(coinsKey));

        Integer nextLevel = experienceLevels.getLevels().size() + 1;

        boolean wasAddSuccesful = experienceLevels.addLevel(coins, nextLevel);

        if (experienceLevels.getLevels().size() == nextLevel) {
            experienceLevels.updateExperienceLevels();
        } else {
            // handle failure
        }

        return wasAddSuccesful;
    }
}
