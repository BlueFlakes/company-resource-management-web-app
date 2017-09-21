package com.codecool.krk.lucidmotors.queststore.models;

import java.util.Date;

public class BoughtArtifact extends AbstractArtifact {

    private Date date;
    private boolean isUsed;

    public BoughtArtifact(ShopArtifact shopArtifact) {

        super(shopArtifact.getName(), shopArtifact.getPrice(), shopArtifact.getArtifactCategory(),
                shopArtifact.getDescription(), null);
        this.date = new Date();
        this.isUsed = false;
    }

    public BoughtArtifact(String name, Integer price, ArtifactCategory artifactCategory, String description,
                          Integer id, Date date, boolean isUsed) {

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        if (this.isUsed) {
            return String.format("name: %s,  date: %s, %s %n", this.getName(), this.date.toString(), "is used");
            
        } else {
            return String.format("name: %s,  date: %s, %s %n", this.getName(), this.date.toString(), "isn't used");
        }
    }

}
