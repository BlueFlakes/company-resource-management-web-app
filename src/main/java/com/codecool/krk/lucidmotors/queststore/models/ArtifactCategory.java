package com.codecool.krk.lucidmotors.queststore.models;

public class ArtifactCategory {

    private String name;
    private Integer id;

    public ArtifactCategory(String name) {
        this.name = name;
    }

    public ArtifactCategory(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
