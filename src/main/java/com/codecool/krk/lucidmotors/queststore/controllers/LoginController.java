package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.exceptions.WrongPasswordException;
import com.codecool.krk.lucidmotors.queststore.models.*;

public class LoginController {

    private School school;

    public LoginController(School school) {
        this.school = school;
    }


    public User getUser(String login, String password) throws DaoException {
        User user = this.school.getUser(login, password);

        return user;
    }

}
