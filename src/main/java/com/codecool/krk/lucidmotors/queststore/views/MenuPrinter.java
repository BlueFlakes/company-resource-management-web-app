package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.dao.FileLoader;

import java.util.List;

class MenuPrinter {
    private final UserInterface userInterface = new UserInterface();
    private final FileLoader dataLoader = new FileLoader();

    void printMenu(String filePath) {
        List<String> importedData = importMenu(filePath);

        userInterface.clearWindow();
        printAllRecordsInMenuStyle(importedData);
    }

    private List<String> importMenu(String filePath) {
        return dataLoader.getDataFromFile(filePath);
    }

    private void printAllRecordsInMenuStyle(List<String> data) {

        if (data.size() > 0) {
            printTitle(data);
        }

        for(int i = 1; i < data.size(); i++) {
            String index = (Integer.toString(i % (data.size() - 1)));
            String row = data.get(i);

            System.out.println("\t" + "(" + index + ") " + row);
        }

        System.out.println();
    }

    private void printTitle(List<String> temp) {
        String title = temp.get(0);
        System.out.println(title);
    }
}
