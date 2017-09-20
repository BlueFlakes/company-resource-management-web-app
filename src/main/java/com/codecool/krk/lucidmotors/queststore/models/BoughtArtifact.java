package com.codecool.krk.lucidmotors.queststore.models;

import java.util.Date;
import java.util.ArrayList;

public class BoughtArtifact extends AbstractArtifact {

    private Date date;
    private boolean isUsed;

    public ArrayList<Student> getOwnersList() {
        return this.ownersList;
    }

    public boolean getIsUsed() {
        return this.isUsed;
    }

    public void markAsUsed() {
        if (!this.isUsed) {
            this.isUsed = true;
        }
    }
}
