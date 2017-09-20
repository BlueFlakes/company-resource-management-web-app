package com.codecool.krk.lucidmotors.queststore.models;

import java.util.Date;
import java.util.ArrayList;

public class BoughtArtifact extends AbstractArtifact {

    private Date date;
    private boolean isUsed;

    public BoughtArtifact(ShopArtifact shopArtifact) {
        super(shopArtifact.getName(), shopArtifact.getPrice(), shopArtifact.getArtifactCategory(), shopArtifact.getDescription(), null);
        this.date = new Date();
        this.isUsed = false;
    }

    public BoughtArtifact(String name, Integer price, ArtifactCategory artifactCategory, String description, Integer id, Date date, boolean isUsed) {
        super(name, price, artifactCategory, description, id);
        this.date = date;
        this.isUsed = isUsed;
    }

    public void markAsUsed() {
        if (!this.isUsed) {
            this.isUsed = true;
        }
    }
}
