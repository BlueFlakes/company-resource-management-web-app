package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;
import com.codecool.krk.lucidmotors.queststore.models.BoughtArtifact;

import java.util.ArrayList;
import java.util.Date;

public class BoughtArtifactDao {
    public BoughtArtifact getArtifact(Integer id){
        /* #TODO database request*/
        return new BoughtArtifact("AA", 10, new ArtifactCategory(), "description", id, new Date(), false);
    }

    public void updateArtifact(BoughtArtifact boughtArtifact) {
        /* #TODO database request*/

    }

    public ArrayList<BoughtArtifact> getAllArtifacts() {
        /* #TODO database request*/
        ArrayList<BoughtArtifact> list = new ArrayList<>();
        list.add(new BoughtArtifact("Temple", 20, new ArtifactCategory(), "description", 1, new Date(), false));
        list.add(new BoughtArtifact("ABB", 10, new ArtifactCategory(), "description", 2, new Date(), false));
        return list;
    }
}
