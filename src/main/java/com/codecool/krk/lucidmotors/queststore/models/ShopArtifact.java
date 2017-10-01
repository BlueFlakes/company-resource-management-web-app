package com.codecool.krk.lucidmotors.queststore.models;

public class ShopArtifact extends AbstractArtifact {

    public ShopArtifact(String name, Integer price, ArtifactCategory artifactCategory, String description) {
        super(name, price, artifactCategory, description);
    }

    public ShopArtifact(String name, Integer price, ArtifactCategory artifactCategory, String description, Integer id) {
        super(name, price, artifactCategory, description, id);
    }

}
