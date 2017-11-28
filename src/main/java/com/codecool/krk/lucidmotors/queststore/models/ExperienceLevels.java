package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.matchers.Compare;

import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codecool.krk.lucidmotors.queststore.matchers.Compare.isHigher;
import static com.codecool.krk.lucidmotors.queststore.matchers.Compare.isHigherOrEqual;
import static com.codecool.krk.lucidmotors.queststore.matchers.Compare.isLower;

/**
 * Treemap<Integer, Integer> levels:
 * key - level
 * value - coins
 */
public class ExperienceLevels {

    private TreeMap<Integer, BigInteger> levels;

    public ExperienceLevels() {
        this.levels = new TreeMap<>();
    }

    public ExperienceLevels(TreeMap<Integer, BigInteger> levels) {
        this.levels = levels;
    }

    public Integer computeStudentLevel(BigInteger userCoins) {

        Integer level;

        if (this.levels.isEmpty()) {

            level = 0;

        } else {

            try {
                level = this.levels.entrySet().stream()
                        .filter(entry -> isHigherOrEqual(userCoins, entry.getValue()))
                        .map(Map.Entry::getKey)
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
    public boolean addLevel(BigInteger neededCoins, Integer newLevel) {
        BigInteger previousLevelCoins = this.levels.get(newLevel - 1);
        previousLevelCoins = (previousLevelCoins == null) ? neededCoins.subtract(new BigInteger("1")) : previousLevelCoins;

        if (!this.levels.containsKey(newLevel) && isLower(previousLevelCoins, neededCoins)) {
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
    public boolean updateLevel(BigInteger neededCoins, Integer updatedLevel) {
        BigInteger previousLevelCoins = this.levels.get(updatedLevel - 1);
        previousLevelCoins = (previousLevelCoins == null) ? neededCoins.subtract(new BigInteger("1")) : previousLevelCoins;

        BigInteger nextLevelCoins = this.levels.get(updatedLevel + 1);
        nextLevelCoins = (nextLevelCoins == null) ? neededCoins.add(new BigInteger("1")) : nextLevelCoins;

        if(this.levels.containsKey(updatedLevel) &&
                isLower(previousLevelCoins, neededCoins) &&
                isHigher(nextLevelCoins, neededCoins)) {
            this.levels.put(updatedLevel, neededCoins);

            return true;
        }

        return false;
    }

    public TreeMap<Integer, BigInteger> getLevels() {
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
