package com.codecool.krk.lucidmotors.queststore.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLoader {

    public List<String> getDataFromFile(String fileName) {
        List<String> temp = new ArrayList<>();
        Scanner inputStream;

        try {
            inputStream = new Scanner(new File(fileName));

            while(inputStream.hasNextLine()) {
                String line = inputStream.nextLine();
                temp.add(line);
            }

            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
