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
        Boolean isAdded = true;
        Integer id = Integer.parseInt(formData.get("artifact_id"));
        Integer artifactCategoryId = Integer.parseInt(formData.get("category_id"));
        ArtifactCategory artifactCategory = artifactCategoryDao.getArtifactCategory(artifactCategoryId);
        String name = formData.get("name");
        String description = formData.get("description");

        try {
            BigInteger price = new BigInteger(formData.get("price"));
            ShopArtifact artifact = shopArtifactDao.getArtifact(id);
            artifact.setName(name);
            artifact.setArtifactCategory(artifactCategory);
            artifact.setDescription(description);
            artifact.setPrice(price);
            artifact.update();

        } catch (NumberFormatException e) {
            isAdded = false;
        }
        return isAdded;
    }

    public boolean addArtifact(Map<String, String> formData) throws DaoException {
        Boolean isAdded = true;
        Integer artifactCategoryId = Integer.parseInt(formData.get("category_id"));
        ArtifactCategory artifactCategory = artifactCategoryDao.getArtifactCategory(artifactCategoryId);
        String name = formData.get("name");
        String description = formData.get("description");

        try {
            BigInteger price = new BigInteger(formData.get("price"));
            ShopArtifact shopArtifact = new ShopArtifact(name, price, artifactCategory, description);
            shopArtifact.save();
            String message = String.format("New artifact available: %s for only %d cc.", shopArtifact.getName(), shopArtifact.getPrice());
            new ChatMessage("system", message).save();

        } catch (NumberFormatException e) {
            isAdded = false;
        }
        return isAdded;
    }

    public boolean addArtifactCategory(Map<String, String> formData) throws DaoException {
        String name = formData.get("name");

        ArtifactCategory artifactCategory = new ArtifactCategory(name);
        artifactCategoryDao.save(artifactCategory);

        return true;
    }

    public List<ShopArtifact> getAvailableArtifacts() throws DaoException {
        return ShopArtifactDao.getDao().getAllArtifacts();
    }
}