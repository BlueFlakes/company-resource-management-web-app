package com.codecool.krk.lucidmotors.queststore;

import com.codecool.krk.lucidmotors.queststore.controllers.MainController;
import com.codecool.krk.lucidmotors.queststore.dao.DatabaseConnection;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

class App {
    private static Scanner in = new Scanner(System.in);


    public static void main(String[] args) {

        try {
            School school = new School("Codecool");
            MainController controller = new MainController(school);
            controller.startServer();
            boolean isRunning = true;

            while (isRunning) {
                isRunning = checkServerIsRunning(controller);
            }

        } catch (DaoException ex) {
            System.out.println("Database connection failed!\n" + ex.getMessage());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean checkServerIsRunning(MainController controller) throws DaoException {
        System.out.print("> ");
        String x = in.nextLine().trim();

        if (x.equalsIgnoreCase("exit")) {
            controller.stop();
            DatabaseConnection.closeConnection();
            System.out.println("Server closed.");
            return false;
        }

        return true;
    }
}
