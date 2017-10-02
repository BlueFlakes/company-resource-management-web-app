package com.codecool.krk.lucidmotors.queststore.models;

import java.util.Date;

public class AchievedQuest extends AbstractQuest {

    private Student owner;
    private Date date;

    public AchievedQuest(AvailableQuest availableQuest, Student owner) {
        super(availableQuest.getName(), availableQuest.getQuestCategory(),
              availableQuest.getDescription(), availableQuest.getValue());
        this.date = new Date();
        this.owner = owner;
    }

    public AchievedQuest(String name, QuestCategory questCategory, String description, Integer value, Integer id,
                         Date date, Student owner) {
        super(name, questCategory, description, value, id);
        this.date = date;
        this.owner = owner;
    }

}
