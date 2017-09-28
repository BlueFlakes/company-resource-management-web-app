package com.codecool.krk.lucidmotors.queststore.interfaces;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.User;

public interface UserController {

    void startController(User user, School school) throws DaoException;
}
