package com.codecool.krk.lucidmotors.queststore.interfaces;

import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.User;

import java.sql.SQLException;

public interface UserController {

    void startController(User user, School school) throws SQLException;
}
