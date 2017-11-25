package com.codecool.krk.lucidmotors.queststore.controllers;

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
