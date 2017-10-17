package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ContributionDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

public class Contribution {

    private Integer id;
    private String name;
    private Student creator;
    private ShopArtifact shopArtifact;
    private Integer givenCoins;
    private String status;

    private final ContributionDao contributionDao = new ContributionDao();

    public Contribution(String name, Student creator, ShopArtifact shopArtifact) throws DaoException {
        this.name = name;
        this.creator = creator;
        this.shopArtifact = shopArtifact;
        this.givenCoins = 0;
        this.status = "open";
    }

    public Contribution(String name, Student creator, ShopArtifact shopArtifact,
                        Integer givenCoins, Integer id, String status) throws DaoException {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.shopArtifact = shopArtifact;
        this.givenCoins = givenCoins;
        this.status = status;
    }

    public void save() throws DaoException {
        contributionDao.save(this);
    }

    public void update() throws DaoException {
        contributionDao.update(this);
    }

    public String toString() {
        String toReturn = String.format("%d. %s, creator: %s, goal: %s, %s/%s",
                                        this.id, this.name, this.creator.getId(), this.shopArtifact.getName(),
                                        this.givenCoins, this.shopArtifact.getPrice());
        return toReturn;
    }

    public void addCoins(Integer coins) {
        this.givenCoins += coins;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Student getCreator() {
        return creator;
    }

    public Integer getGivenCoins() {
        return givenCoins;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String s) { this.status = s; }

    public ShopArtifact getShopArtifact() {
        return shopArtifact;
    }
}
