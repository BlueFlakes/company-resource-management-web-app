package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ExperienceLevels;
import com.codecool.krk.lucidmotors.queststore.models.Mentor;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ExperienceLevelsController extends AbstractUserController<Mentor> {

    private ExperienceLevelsDao experienceLevelsDao = new ExperienceLevelsDao();

    public ExperienceLevelsController() throws DaoException {

    }

    /**
     * Switches between menu options.
     *
     * @param choice
     */
    protected void handleUserRequest(String choice) throws DaoException {


        switch (choice) {

            case "1":
                createNewLevel();
                break;

            case "2":
                updateLevel();
                break;

            case "3":
                showLevels();
                break;

            case "0":
                break;

            default:
                userInterface.println("No such option.");
                break;
        }
    }

    protected void showMenu() {
        userInterface.printExperienceLevelsMenu();
    }

    /**
     * Gathers data about new level and insert it into database.
     */
    private void createNewLevel() throws DaoException {
        ExperienceLevels experienceLevels = this.experienceLevelsDao
                                   .getExperienceLevels();


        Integer nextLevel = experienceLevels.getLevels().size() + 1;

        this.userInterface.println(String.format("Provide coins amount for level %d%n", nextLevel));
        String[] questions = {"needed coins: "};
        String[] types = {"integer"};
        Integer coins = Integer.parseInt(this.userInterface.inputs.getValidatedInputs(questions, types).get(0));

        if(experienceLevels.getMaxCoins() < coins) {
            experienceLevels.addLevel(coins, nextLevel);
            experienceLevels.updateExperienceLevels();
            this.userInterface.println("Level added");
        } else {
            this.userInterface.println("Level addition failure! Needed coins of new level must be greater than any other levels.");
        }


        this.userInterface.pause();
    }

    /**
     * Gathers new data about level and update it in database.
     */
    private void updateLevel() throws DaoException {
        ExperienceLevels experienceLevels = this.experienceLevelsDao.getExperienceLevels();

        String[] questions = {"level: ", "needed coins: "};
        String[] types = {"integer", "integer"};
        ArrayList<String> answers = this.userInterface.inputs.getValidatedInputs(questions, types);

        Integer level = Integer.parseInt(answers.get(0));
        Integer coins = Integer.parseInt(answers.get(1));

        Map<Integer, Integer> levels = experienceLevels.getLevels();

        Integer previousLevelCoins = findPreviousLevelCoins(levels, level, coins);
        Integer nextLevelCoins = findNextLevelCoins(levels, level, coins);

        if (levels.values().contains(level) &&
                previousLevelCoins < coins && nextLevelCoins > coins) {
            experienceLevels.updateLevel(coins, level);
            experienceLevels.updateExperienceLevels();
            this.userInterface.println("Level updated");
        } else {
            this.userInterface.println("Level update failure!");
        }
        this.userInterface.pause();
    }

    private Integer findPreviousLevelCoins(Map<Integer, Integer> levels, Integer level, Integer coins) {
        Integer previousLevelCoins;
        try {
            previousLevelCoins = levels.entrySet().stream()
                    .filter(entry -> (entry.getValue() == level - 1))
                    .map(entry -> entry.getKey())
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            previousLevelCoins = coins - 1;
        }

        return previousLevelCoins;
    }

    private Integer findNextLevelCoins(Map<Integer, Integer> levels, Integer level, Integer coins) {
        Integer nextLevelCoins;
        try {
            nextLevelCoins = levels.entrySet().stream()
                    .filter(entry -> (entry.getValue() == level + 1))
                    .map(entry -> entry.getKey())
                    .findFirst().get();

        } catch (NoSuchElementException e) {
            nextLevelCoins = coins + 1;
        }

        return nextLevelCoins;
    }

    private void showLevels() throws DaoException {

        this.userInterface.println(this.experienceLevelsDao.getExperienceLevels().toString());
        this.userInterface.pause();
    }
}
