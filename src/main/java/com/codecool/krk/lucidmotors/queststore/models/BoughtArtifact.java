package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.BoughtArtifactDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoughtArtifact extends AbstractArtifact {

    private Date date;
    private boolean isUsed;
    private BoughtArtifactDao boughtArtifactDao = BoughtArtifactDao.getDao();

    public BoughtArtifact(ShopArtifact shopArtifact) throws DaoException {

        super(shopArtifact.getName(), shopArtifact.getPrice(), shopArtifact.getArtifactCategory(),
                shopArtifact.getDescription());
        this.date = new Date();
        this.isUsed = false;
    }

    public BoughtArtifact(String name, BigInteger price, ArtifactCategory artifactCategory, String description,
                          Integer id, Date date, boolean isUsed) throws DaoException {

        super(name, price, artifactCategory, description, id);
        this.date = date;
        this.isUsed = isUsed;
    }

    public void markAsUsed() {
        this.isUsed = true;
    }

    public boolean isUsed() {
        return this.isUsed;
    }

    public Date getRealDate() {
        return this.date;
    }

    public String getDate() {
        return convertDateToString(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String dateString = this.getDate();
        if (this.isUsed) {
            return String.format("%s,  date: %s, %s", this.getName(),
                    dateString, "is used");
            
        } else {
            return String.format("%s,  date: %s, %s", this.getName(),
                    dateString, "isn't used");
        }
    }

    public void save(List<Student> owners) throws DaoException {
        boughtArtifactDao.save(this, owners);
    }

    public void update() throws DaoException {
        boughtArtifactDao.updateArtifact(this);
    }

    private String convertDateToString(Date purchaseDate) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String purchaseDateString = dateFormatter.format(purchaseDate);

        return purchaseDateString;
    }

}
