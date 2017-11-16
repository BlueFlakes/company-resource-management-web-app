package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Treemap<Integer, Integer> levels:
 * key - level
 * value - coins
 */
public class ExperienceLevels {

    private TreeMap<Integer, Integer> levels;

    public ExperienceLevels() {
        this.levels = new TreeMap<>();
    }

    public ExperienceLevels(TreeMap<Integer, Integer> levels) {
        this.levels = levels;
    }

    public Integer computeStudentLevel(Integer userCoins) {

        Integer level;

        if (this.levels.isEmpty()) {

            level = 0;

        } else {

            try {
                level = this.levels.entrySet().stream()
                        .filter(entry -> (userCoins >= entry.getValue()))
                        .map(entry -> entry.getKey())
                        .max(Integer::compareTo)
                        .get();
            } catch (NoSuchElementException e) {
                level = 0;
            }
        }

        return level;
    }

    /**
     * Creates new level
     *
     * @param coins
     * @param level
     */
    public boolean addLevel(Integer neededCoins, Integer newLevel) {
        Integer previousLevelCoins = this.levels.get(newLevel - 1);
        previousLevelCoins = (previousLevelCoins == null) ? neededCoins - 1 : previousLevelCoins;

        if (!this.levels.containsKey(newLevel) && previousLevelCoins < neededCoins) {
            this.levels.put(newLevel, neededCoins);
            return true;
        }

        return false;
    }

    /**
     * Sets new data to existing level
     *
     * @param coins
     * @param level
     */
    public boolean updateLevel(Integer neededCoins, Integer updatedLevel) {
        Integer previousLevelCoins = this.levels.get(updatedLevel - 1);
        previousLevelCoins = (previousLevelCoins == null) ? neededCoins - 1 : previousLevelCoins;

        Integer nextLevelCoins = this.levels.get(updatedLevel + 1);
        nextLevelCoins = (nextLevelCoins == null) ? neededCoins + 1 : nextLevelCoins;

        if(this.levels.containsKey(updatedLevel) &&
                previousLevelCoins < neededCoins &&
                nextLevelCoins > neededCoins) {
            this.levels.put(updatedLevel, neededCoins);

            return true;
        }

        return false;
    }

    public TreeMap<Integer, Integer> getLevels() {
        return levels;
    }

    /**
     * Saves data into database
     *
     * @throws DaoException
     */
    public void updateExperienceLevels() throws DaoException {
        new ExperienceLevelsDao().updateExperienceLevels(this);
    }

    @Override
    public String toString() {
        return this.levels.entrySet().stream()
                                     .map(entry -> String.format("level: %d -> %d", entry.getKey(), entry.getValue()))
                                     .collect(Collectors.joining("\n"));
    }

}
