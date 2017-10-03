package com.codecool.krk.lucidmotors.queststore.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.TreeMap;

class ExperienceLevelsTest {
    ExperienceLevels experienceLevels;

    @BeforeEach
    public void prepareObject() {
        this.experienceLevels = new ExperienceLevels();
    }

    @Test
    public void testAddTestLevel() {
        this.experienceLevels.addLevel(10, 1);
        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();
        expectedLevels.put(10, 1);
        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }

    @Test
    public void testCannotAddTheSameLevel() {
        this.experienceLevels.addLevel(10, 1);
        this.experienceLevels.addLevel(12, 1);

        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();
        expectedLevels.put(10, 1);
        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }

    @Test
    public void testTwoLevelsWithSameCoins() {
        this.experienceLevels.addLevel(10, 1);
        this.experienceLevels.addLevel(10, 2);

        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();
        expectedLevels.put(10, 1);
        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }

    @Test
    public void updateNonExistingLevel() {
        this.experienceLevels.updateLevel(10, 1);

        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();

        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }

    @Test
    public void updateExistingLevel() {
        this.experienceLevels.addLevel(5, 1);
        this.experienceLevels.updateLevel(10, 1);

        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();
        expectedLevels.put(10, 1);

        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }
}