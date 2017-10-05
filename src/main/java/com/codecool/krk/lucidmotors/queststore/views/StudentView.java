package com.codecool.krk.lucidmotors.queststore.views;

import java.util.List;

public class StudentView {
    private final MenuPrinter menuPrinter = new MenuPrinter();

    public void printStudentMenu() {
        String fileName = "txt/studentMenu.txt";
        menuPrinter.printMenu(fileName);
    }

    public void printStudentStoreMenu() {
        String fileName = "txt/studentStoreMenu.txt";
        menuPrinter.printMenu(fileName);
    }
}
