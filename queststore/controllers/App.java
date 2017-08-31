package queststore.controllers;

import queststore.models.School;
import queststore.exceptions.WrongPasswordException;

public class App {

    public static void main(String[] args) {
        School school = new School("Codecool");
        try{
            new LoginController(school).start();
        } catch (WrongPasswordException e) {
            System.out.println(e.getMessage());
        }
    }
}
