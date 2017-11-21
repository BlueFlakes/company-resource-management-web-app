package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ShopArtifactDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;


public class ShopArtifact extends AbstractArtifact {

    public ShopArtifact(String name, Integer price, ArtifactCategory artifactCategory,
                        String description) throws IllegalArgumentException {

        super(name, price, artifactCategory, description);
        validateArtifactArguments(this);
    }

    public ShopArtifact(String name, Integer price, ArtifactCategory artifactCategory,
                        String description, Integer id) {

        super(name, price, artifactCategory, description, id);
    }

    private void validateArtifactArguments(ShopArtifact shopArtifact) throws IllegalArgumentException {
        if(shopArtifact.getPrice() < 0) {
            throw new IllegalArgumentException("Price should be at least 0.");
        } else if(shopArtifact.getArtifactCategory() == null) {
            throw new IllegalArgumentException("No such category");
        }
    }

    public void save() throws DaoException {
        ShopArtifactDao.getDao().save(this);
    }


}
