package com.codecool.krk.lucidmotors.queststore.views;

import java.util.Iterator;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import com.codecool.krk.lucidmotors.queststore.dao.FileLoader;
import com.codecool.krk.lucidmotors.queststore.models.*;

public class UserInterface {
    private final Scanner in = new Scanner(System.in);
    private final FileLoader dataLoader = new FileLoader();
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

    public void printMentorMenu() {
        String fileName = "txt/mentorMenu.txt";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printMentorStoreMenu() {
        String fileName = "txt/mentorStoreMenu.txt";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printStudentMenu() {
        String fileName = "txt/studentMenu.txt";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printStudentStoreMenu() {
        String fileName = "txt/studentStoreMenu.txt";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printManagerMenu() {
        String fileName = "txt/managerMenu.txt";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printExperienceLevelsMenu() {
        String fileName = "txt/experienceLevelsMenu.txt";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printLoginMenu() {
        String fileName = "txt/loginControllerMenu.txt";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    private void printMenu(List<String> data) {
        clearWindow();
        printAllRecordsInMenuStyle(data);
    }

    private void printAllRecordsInMenuStyle(List<String> data) {
        String title = data.get(0);

        System.out.println(title);

        for(int i = 1; i < data.size(); i++) {
            String index = (Integer.toString(i % (data.size() - 1)));
            String row = data.get(i);

            System.out.println("\t" + "(" + index + ") " + row);
        }

        System.out.println();
    }

    public void print(Iterator collection) {
        while (collection.hasNext()) {
            println(collection.next().toString());
        }
    }

    public void printBoughtArtifactsByStudent(ArrayList<BoughtArtifact> studentArtifacts) {
        println("Owned artifacts:");
        print(studentArtifacts.iterator());
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
