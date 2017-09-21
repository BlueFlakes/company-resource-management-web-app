package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;
import com.codecool.krk.lucidmotors.queststore.models.BoughtArtifact;
import com.codecool.krk.lucidmotors.queststore.models.SchoolClass;
import com.codecool.krk.lucidmotors.queststore.models.Student;

import java.util.ArrayList;
import java.util.Date;

public class ArtifactOwnersDao {
    public Student getStudent(BoughtArtifact boughtArtifact) {
        return new Student("Maciej nowak", "mcnowak", "boczniak", "", new SchoolClass("druga"));
    }

    public ArrayList<BoughtArtifact> getArtifacts(Student student) {
        ArrayList<BoughtArtifact> studentsArtifactsList = new ArrayList<>();
        studentsArtifactsList.add(new BoughtArtifact("Temple", 20, new ArtifactCategory(), "description", 1, new Date(), false));
        return studentsArtifactsList;
    }

    public void update(Student student, BoughtArtifact boughtArtifact) {

    }

}
