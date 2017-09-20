package com.codecool.krk.lucidmotors.queststore;

import com.codecool.krk.lucidmotors.queststore.controllers.LoginController;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.exceptions.WrongPasswordException;

public class App {

    public static void main(String[] args) {

        School school = new School("Codecool");
        try {
            new LoginController(school).start();
        } catch (WrongPasswordException e) {
            System.out.println(e.getMessage());
        }
    }
}
