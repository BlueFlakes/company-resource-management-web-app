package com.codecool.krk.lucidmotors.queststore.menu_enums;

import java.lang.NullPointerException;

public enum MentorMenuOptions {
    ADD_STUDENT("1"),
    ADD_QUEST("2"),
    ADD_QUEST_CATEGORY("3"),
    UPDATE_QUEST("4"),
    MARK_BOUGHT_ARTIFACT_AS_USED("5"),
    START_MENTOR_STORE_CONTROLLER("6"),
    EXIT("0");

    private final String option;

    MentorMenuOptions(String s) {
        this.option = s;
    }

    public String getOptionNumber() {
        return this.option;
    }

    public static MentorMenuOptions getChosenOption(String userChoice) {
        for (MentorMenuOptions option : MentorMenuOptions.values()) {
            try {
                if (option.getOptionNumber().equals(userChoice)) {
                    return option;
                }
            } catch (NullPointerException e) {
            }
        }
        return null;
    }
}