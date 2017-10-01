package com.codecool.krk.lucidmotors.queststore.models;

public abstract class AbstractArtifact {

    private String name;
    private Integer price;
    private ArtifactCategory artifactCategory;
    private String description;
    private Integer id;

    public AbstractArtifact(String name, Integer price, ArtifactCategory artifactCategory, String description) {

        this.name = name;
        this.price = price;
        this.artifactCategory = artifactCategory;
        this.description = description;
    }

    public AbstractArtifact(String name, Integer price, ArtifactCategory artifactCategory, String description, Integer id) {

        this.name = name;
        this.price = price;
        this.artifactCategory = artifactCategory;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ArtifactCategory getArtifactCategory() {
        return artifactCategory;
    }

    public void setArtifactCategory(ArtifactCategory artifactCategory) {
        this.artifactCategory = artifactCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("id: %d, name: %s, price: %d, artifact category: %s.%n", this.id, this.name, this.price, this.artifactCategory.getName());
    }
}
