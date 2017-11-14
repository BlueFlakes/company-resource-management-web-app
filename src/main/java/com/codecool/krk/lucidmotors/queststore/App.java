package com.codecool.krk.lucidmotors.queststore;

import com.codecool.krk.lucidmotors.queststore.controllers.LoginController;
import com.codecool.krk.lucidmotors.queststore.controllers.MainController;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.exceptions.WrongPasswordException;
import com.codecool.krk.lucidmotors.queststore.dao.DatabaseConnection;

import java.io.IOException;

class App {

    public static void main(String[] args) {

        try {
            School school = new School("Codecool");
            new MainController(school).start();

        } catch (DaoException ex) {
            System.out.println("Database connection failed!\n" + ex.getMessage());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
