package com.codecool.krk.lucidmotors.queststore.models;

public class AbstractQuest {

    private Integer id;
    private String name;
    private QuestCategory questCategory;
    private String description;
    private Integer value;

    public AbstractQuest(String name, QuestCategory questCategory, String description, Integer value) {
        this.name = name;
        this.questCategory = questCategory;
        this.description = description;
        this.value = value;
    }

    public AbstractQuest(String name, QuestCategory questCategory, String description, Integer value, Integer id) {
        this.id = id;
        this.name = name;
        this.questCategory = questCategory;
        this.description = description;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuestCategory getQuestCategory() {
        return this.questCategory;
    }

    public void setQuestCategory(QuestCategory questCategory) {
        this.questCategory = questCategory;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
