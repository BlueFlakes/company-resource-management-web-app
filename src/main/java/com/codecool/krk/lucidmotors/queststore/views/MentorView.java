package com.codecool.krk.lucidmotors.queststore.views;

public class MentorView {
    private final MenuPrinter menuPrinter = new MenuPrinter();

    public void printMentorMenu() {
        String fileName = "txt/mentorMenu.txt";
        menuPrinter.printMenu(fileName);
    }

    public void printMentorStoreMenu() {
        String fileName = "txt/mentorStoreMenu.txt";
        menuPrinter.printMenu(fileName);
    }
}
