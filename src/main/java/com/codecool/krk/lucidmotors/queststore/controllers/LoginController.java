package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.enums.LoginMenuOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.exceptions.WrongPasswordException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import java.util.ArrayList;

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
    public void start() throws DaoException {

        String userChoice;

        do {
            showMenu();
            userChoice = userInterface.inputs.getInput("What do you want to do: ");
            userInterface.clearWindow();
            handleUserRequest(userChoice);

        } while (!userChoice.equals("0"));
    }

    private void handleUserRequest(String userChoice) throws DaoException {

        LoginMenuOptions chosenOption = getEnumValue(userChoice);

        switch (chosenOption) {
            case HANDLE_LOGIN:
                handleLogin();
                break;

            case EXIT:
                userInterface.println("Have a nice day!");
                break;

            case DEFAULT:

        }
    }

    private LoginMenuOptions getEnumValue(String userChoice) {
        LoginMenuOptions chosenOption;

        try {
            chosenOption = LoginMenuOptions.values()[Integer.parseInt(userChoice)];
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            chosenOption = LoginMenuOptions.DEFAULT;
        }

        return chosenOption;
    }

    private void showMenu() {
        userInterface.printLoginMenu();
    }

    private void handleLogin() throws DaoException {

        String[] questions = {"-> Login: ", "-> Password: "};
        String[] expectedTypes = {"String", "String"};

        ArrayList<String> userInputs = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
        String login = userInputs.get(0);
        String password = userInputs.get(1);

        User user = this.school.getUser(login, password);

        if (user != null) {
            runUserController(user);
        } else {
            userInterface.println("error: ~please provide correct login and password!");
            userInterface.pause();
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
