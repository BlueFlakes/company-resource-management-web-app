package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.User;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.Mentor;
import com.codecool.krk.lucidmotors.queststore.models.Manager;

import com.codecool.krk.lucidmotors.queststore.exceptions.WrongPasswordException;


public class LoginController {

    private School school;
    private UserInterface userInterface = new UserInterface();

    public LoginController(School school) {
        this.school = school;
    }

    public void start() throws WrongPasswordException {

        String login = userInterface.inputs.getInput("Please provide your login: ");
        String givenPassword = userInterface.inputs.getInput("Please provide your password: ");

        User user = this.school.getUser(login);
        if (user != null) {
            String expectedPassword = user.getPassword();
            if (expectedPassword.equals(givenPassword)) {
                runUserController(user);
            } else {
                throw new WrongPasswordException();
            }

        } else {
            throw new WrongPasswordException();
        }
    }

    private void runUserController(User user) {
        if (user instanceof Manager) {
            new ManagerController().startController(user, this.school);
        } else if (user instanceof Mentor) {
            new MentorController().startController(user, this.school);
        } else if (user instanceof Student) {
            new StudentController().startController(user, this.school);
        }
    }
}
