package com.codecool.krk.lucidmotors.queststore.views;

public class LoginView {
    private  final MenuPrinter menuPrinter = new MenuPrinter();

    public void printLoginMenu() {
        String fileName = "txt/loginControllerMenu.txt";
        menuPrinter.printMenu(fileName);
    }
}
