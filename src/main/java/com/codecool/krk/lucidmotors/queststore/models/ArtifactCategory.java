package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ArtifactCategoryDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

public class ArtifactCategory {
    private String name;
    private Integer id;
    ArtifactCategoryDao artifactCategoryDao = ArtifactCategoryDao.getDao();

    public ArtifactCategory(String name) throws DaoException {
        this.name = name;
    }

    public ArtifactCategory(String name, Integer id) throws DaoException {
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

    public void save() throws DaoException {
        artifactCategoryDao.save(this);
    }

}
