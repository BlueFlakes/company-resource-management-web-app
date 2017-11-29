package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.exceptions.IncorrectStateException;
import com.codecool.krk.lucidmotors.queststore.models.ExperienceLevels;
import com.codecool.krk.lucidmotors.queststore.models.OperationScore;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

import static com.codecool.krk.lucidmotors.queststore.matchers.Compare.isHigherOrEqual;
import static com.codecool.krk.lucidmotors.queststore.matchers.Compare.isLowerOrEqual;
import static java.lang.Integer.parseInt;

public class ExperienceLevelsController {
    private final ExperienceLevelsDao experienceLevelsDao;

    public ExperienceLevelsController() throws DaoException {
        this.experienceLevelsDao = new ExperienceLevelsDao();
    }

    public TreeMap<Integer, BigInteger> getLevels() throws DaoException {
        ExperienceLevels experienceLevels = this.experienceLevelsDao.getExperienceLevels();
        return experienceLevels.getLevels();
    }

    public OperationScore<ExperienceLevels.UpdateLevel> updateLevel
            (Map<String, String> formData, String coinsKey, String levelsKey)
            throws DaoException, IncorrectStateException {

        ExperienceLevels experienceLevels = this.experienceLevelsDao.getExperienceLevels();

        BigInteger coins = new BigInteger(formData.get(coinsKey));
        int level = parseInt(formData.get(levelsKey));
        ExperienceLevels.UpdateLevel status = experienceLevels.updateLevel(coins, level);

        return chooseProperState(status, experienceLevels);
    }

    private OperationScore<ExperienceLevels.UpdateLevel> chooseProperState
            (ExperienceLevels.UpdateLevel status, ExperienceLevels experienceLevels)
            throws DaoException, IncorrectStateException {

        if (status.equals(ExperienceLevels.UpdateLevel.SUCCESSFULLY)) {
            experienceLevels.updateExperienceLevels();
            String message = "Succesfully updated level. Great!";
            return new OperationScore<>(ExperienceLevels.UpdateLevel.SUCCESSFULLY, message);

        } else if (status.equals(ExperienceLevels.UpdateLevel.TOO_HIGH)) {
            String message = "Delivered coins number is too big.";
            return new OperationScore<>(ExperienceLevels.UpdateLevel.TOO_HIGH, message);

        } else if (status.equals(ExperienceLevels.UpdateLevel.TOO_LOW)) {
            String message = "Delivered too low amount of coins.";
            return new OperationScore<>(ExperienceLevels.UpdateLevel.TOO_LOW, message);

        } else {
            throw new IncorrectStateException("ERROR ~ Update levels failed.");
        }
    }

    public boolean createNewLevel(Map<String, String> formData, String coinsKey) throws DaoException {

        ExperienceLevels experienceLevels = this.experienceLevelsDao.getExperienceLevels();
        BigInteger coins = new BigInteger(formData.get(coinsKey));

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
