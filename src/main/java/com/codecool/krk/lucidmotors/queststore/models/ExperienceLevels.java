package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

import java.util.TreeMap;
import java.util.Map;

/**
 * Treemap<Integer, Integer> levels:
 * key - coins
 * value - level
 */
public class ExperienceLevels {

    private TreeMap<Integer, Integer> levels;

    public ExperienceLevels() {
        this.levels = new TreeMap<>();
    }

    public ExperienceLevels(TreeMap<Integer, Integer> levels) {
        this.levels = levels;
    }

    public Integer computeStudentLevel(Integer coins) {

        Integer level;

        if (!this.levels.isEmpty()) {
            level = findLevelInMap(coins);
            level = (level != null) ? level : 0;

        } else {
            level = 0;
        }

        return level;
    }

    private Integer findLevelInMap(Integer coins) {
        Integer level = null;

        for (Integer minimalCoinAmount : levels.keySet()) {
            if (minimalCoinAmount <= coins) level = this.levels.get(minimalCoinAmount);
        }

        return level;
    }

    /**
     * Creates new level
     *
     * @param coins
     * @param level
     */
    public void addLevel(Integer coins, Integer level) {

        if (!this.levels.values().contains(level) && !this.levels.keySet().contains(coins)) {
            this.levels.put(coins, level);
        }
    }

    /**
     * Sets new data to existing level
     *
     * @param coins
     * @param level
     */
    public void updateLevel(Integer coins, Integer level) {
        if (this.levels.values().contains(level) && !this.levels.keySet().contains(coins)) {

            Map.Entry<Integer, Integer> entryToRemove = this.levels.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().equals(level))
                    .findFirst()
                    .get();

            this.levels.remove(entryToRemove.getKey());
            this.levels.put(coins, level);
        }
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
                                     .map(entry -> String.format("level: %d -> %d", entry.getValue(), entry.getKey()))
                                     .collect(Collectors.joining("\n"));
    }
}