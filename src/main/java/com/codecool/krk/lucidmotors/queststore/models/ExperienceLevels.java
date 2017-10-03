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

    public void addLevel(Integer coins, Integer level) {
        this.levels.put(coins, level);
    }

    public TreeMap<Integer, Integer> getLevels() {
        return levels;
    }
}