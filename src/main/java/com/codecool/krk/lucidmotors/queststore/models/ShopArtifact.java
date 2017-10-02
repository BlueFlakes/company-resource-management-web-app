package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ShopArtifactDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

public class ShopArtifact extends AbstractArtifact {

    public ShopArtifact(String name, Integer price, ArtifactCategory artifactCategory, String description) {
        super(name, price, artifactCategory, description);
    }

    public ShopArtifact(String name, Integer price, ArtifactCategory artifactCategory, String description, Integer id) {
        super(name, price, artifactCategory, description, id);
    }

    public void save() throws DaoException {
        new ShopArtifactDao().save(this);
    }


}
