package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;
import com.codecool.krk.lucidmotors.queststore.models.ShopArtifact;

import java.util.ArrayList;

public class ShopArtifactDao {

    public ShopArtifact getArtifact(Integer id){
        /* #TODO database request*/
        if(id.equals(10)) {
            return new ShopArtifact("Combat training", 50, new ArtifactCategory(), "Private mentoring", 10);

        } else if(id.equals(20)) {
            return new ShopArtifact("Temple", 500, new ArtifactCategory(), "Private mentoring", 20);

        } else {
            return null;
        }

    }

    public void updateArtifact(ShopArtifact shopArtifact) {
        /* #TODO database request*/

    }

    public ArrayList<ShopArtifact> getAllArtifacts() {
        /* #TODO database request*/
        ArrayList<ShopArtifact> list = new ArrayList<>();
        list.add(new ShopArtifact("Combat training", 50, new ArtifactCategory(), "Private mentoring", 10));
        list.add(new ShopArtifact("Combat training", 50, new ArtifactCategory(), "Private mentoring", 20));

        return list;
    }

}
