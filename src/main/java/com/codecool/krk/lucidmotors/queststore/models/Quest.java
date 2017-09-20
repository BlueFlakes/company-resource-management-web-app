package com.codecool.krk.lucidmotors.queststore.models;

public class Quest implements QuestInterface {

    private String name;
    private QuestCategory questCategory;
    private String description;
    private Integer value;

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestCategory(QuestCategory questCategory) {
        this.questCategory = questCategory;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public void setValue (Integer value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public QuestCategory getQuestCategory() {
        return this.questCategory;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getValue() {
        return this.value;
    }

}
