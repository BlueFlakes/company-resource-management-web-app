package com.codecool.krk.lucidmotors.queststore.views;

public class ManagerView {
    private  final MenuPrinter menuPrinter = new MenuPrinter();

    public void printManagerMenu() {
        String fileName = "txt/managerMenu.txt";
        menuPrinter.printMenu(fileName);
    }

    public void printExperienceLevelsMenu() {
        String fileName = "txt/experienceLevelsMenu.txt";
        menuPrinter.printMenu(fileName);
    }
}
