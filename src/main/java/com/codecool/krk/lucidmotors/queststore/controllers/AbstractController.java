package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.User;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;


public abstract class AbstractController<T> implements UserController {

    final UserInterface userInterface = new UserInterface();
    protected School school;
    protected T user;

    protected abstract void showMenu();
    protected abstract void handleUserRequest(String userChoice) throws DaoException;

    public final void startController(User user, School school) throws DaoException {

        this.user = (T) user;
        this.school = school;

        String userChoice;

        do {
            showMenu();
            userChoice = userInterface.inputs.getInput("What do you want to do: ");
            handleUserRequest(userChoice);

        } while (!userChoice.equals("0"));
    }
}