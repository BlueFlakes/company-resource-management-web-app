package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.exceptions.WrongPasswordException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

public class LoginController {

    private final UserInterface userInterface = new UserInterface();
    private School school;

    public LoginController(School school) {
        this.school = school;
    }

    /**
     * Gets login and password from user, check existance of such user.
     * If password is correct runs appropriate controller.
     *
     * @throws WrongPasswordException
     * @throws DaoException
     */
    public void start() throws WrongPasswordException, DaoException {

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

    /**
     * Runs appropriate controller based on user Type
     *
     * @param user
     * @throws DaoException
     */
    private void runUserController(User user) throws DaoException {

        if (user instanceof Manager) {
            new ManagerController().startController(user, this.school);

        } else if (user instanceof Mentor) {
            new MentorController().startController(user, this.school);

        } else if (user instanceof Student) {
            new StudentController().startController(user, this.school);
        }
    }
}
