package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ArtifactCategoryDao;
import com.codecool.krk.lucidmotors.queststore.dao.AvailableQuestDao;
import com.codecool.krk.lucidmotors.queststore.dao.QuestCategoryDao;
import com.codecool.krk.lucidmotors.queststore.dao.ShopArtifactDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;
import com.codecool.krk.lucidmotors.queststore.models.ChatMessage;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.ShopArtifact;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class MentorStoreController {
    private ArtifactCategoryDao artifactCategoryDao = ArtifactCategoryDao.getDao();
    private ShopArtifactDao shopArtifactDao = ShopArtifactDao.getDao();
    School school;

    public MentorStoreController(School school) throws DaoException {
        this.school = school;
    }

    public boolean updateArtifact(Map<String, String> formData) throws DaoException {
        String name = formData.get("name");
        Integer id = Integer.parseInt(formData.get("artifact_id"));
        String chosenArtifactName = shopArtifactDao.getArtifact(id).getName();

        if (shopArtifactDao.getArtifactByName(name) == null || name.equalsIgnoreCase(chosenArtifactName)) {
            Integer artifactCategoryId = Integer.parseInt(formData.get("category_id"));
            ArtifactCategory artifactCategory = artifactCategoryDao.getArtifactCategory(artifactCategoryId);
            String description = formData.get("description");

            BigInteger price = new BigInteger(formData.get("price"));
            ShopArtifact artifact = shopArtifactDao.getArtifact(id);
            artifact.setName(name);
            artifact.setArtifactCategory(artifactCategory);
            artifact.setDescription(description);
            artifact.setPrice(price);
            artifact.update();
            return true;
        }

        return false;
    }

    public boolean addArtifact(Map<String, String> formData) throws DaoException {
        String name = formData.get("name");

        if (shopArtifactDao.getArtifactByName(name) == null) {
            Integer artifactCategoryId = Integer.parseInt(formData.get("category_id"));
            ArtifactCategory artifactCategory = artifactCategoryDao.getArtifactCategory(artifactCategoryId);
            String description = formData.get("description");

            BigInteger price = new BigInteger(formData.get("price"));
            ShopArtifact shopArtifact = new ShopArtifact(name, price, artifactCategory, description);
            shopArtifact.save();
            String message = String.format("New artifact available: %s for only %d cc.", shopArtifact.getName(), shopArtifact.getPrice());
            new ChatMessage("system", message, "System messages").save();

            return true;
        }

        return false;
    }

    public boolean addArtifactCategory(Map<String, String> formData) throws DaoException {
        String name = formData.get("name");

        if (artifactCategoryDao.getArtifactByName(name) == null) {
            ArtifactCategory artifactCategory = new ArtifactCategory(name);
            artifactCategoryDao.save(artifactCategory);
            return true;
        }

        return false;
    }

    public List<ShopArtifact> getAvailableArtifacts() throws DaoException {
        return ShopArtifactDao.getDao().getAllArtifacts();
    }
}