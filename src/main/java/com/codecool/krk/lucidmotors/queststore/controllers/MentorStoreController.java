package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ArtifactCategoryDao;
import com.codecool.krk.lucidmotors.queststore.dao.AvailableQuestDao;
import com.codecool.krk.lucidmotors.queststore.dao.QuestCategoryDao;
import com.codecool.krk.lucidmotors.queststore.dao.ShopArtifactDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.ShopArtifact;

import java.util.List;
import java.util.Map;

public class MentorStoreController {
    private ArtifactCategoryDao artifactCategoryDao = new ArtifactCategoryDao();
    private ShopArtifactDao shopArtifactDao = new ShopArtifactDao();
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
            Integer price = Integer.parseInt(formData.get("price"));
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
            Integer price = Integer.parseInt(formData.get("price"));
            ShopArtifact shopArtifact = new ShopArtifact(name, price, artifactCategory, description);
            shopArtifact.save();

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
        return new ShopArtifactDao().getAllArtifacts();
    }
}