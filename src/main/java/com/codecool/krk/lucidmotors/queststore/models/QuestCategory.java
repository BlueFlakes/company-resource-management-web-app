package com.codecool.krk.lucidmotors.queststore.models;

public class QuestCategory {

    private Integer id;
    private String name;

    public QuestCategory(String name) {
        this.name = name;
    }

    public QuestCategory(String name, Integer id) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ("\t" + this.id + ". " + this.name);
    }
}
