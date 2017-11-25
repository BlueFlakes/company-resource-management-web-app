package com.codecool.krk.lucidmotors.queststore.models;

import java.math.BigInteger;

public abstract class AbstractQuest {

    private Integer id;
    private String name;
    private QuestCategory questCategory;
    private String description;
    private BigInteger value;

    public AbstractQuest(String name, QuestCategory questCategory, String description, BigInteger value) {
        this.name = name;
        this.questCategory = questCategory;
        this.description = description;
        this.value = value;
    }

    public AbstractQuest(String name, QuestCategory questCategory, String description, BigInteger value, Integer id) {
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

    public BigInteger getValue() {
        return this.value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
