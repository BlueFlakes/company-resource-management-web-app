package com.codecool.krk.lucidmotors.queststore.views;

import java.util.Iterator;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import com.codecool.krk.lucidmotors.queststore.dao.FileLoader;
import com.codecool.krk.lucidmotors.queststore.models.*;

public class UserInterface {
    private final Scanner in = new Scanner(System.in);
    public final Inputs inputs = new Inputs();

    public void println(String text) {
        System.out.println(text);
    }

    public void newLine() {
        System.out.println();
    }

    public void clearWindow() {
        for(int i = 0; i < 50; i ++) {
            System.out.println();
        }
    }

    public void pause() {
        System.out.println();
        System.out.print("Press enter to continue...");
        in.nextLine();
    }

    public void print(Iterator collection) {
        while (collection.hasNext()) {
            println(collection.next().toString());
        }
    }

    public void printArtifactsCategories(ArrayList<ArtifactCategory> artifactCategories) {
        println("Possible artifact categories:");

        artifactCategories.stream()
                          .map(artifactCategory -> String.format("id: %d, name: %s",
                                                                 artifactCategory.getId(),
                                                                 artifactCategory.getName()))
                          .forEach(System.out::println);
    }


}
