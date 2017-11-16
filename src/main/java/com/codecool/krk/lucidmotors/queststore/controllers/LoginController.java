package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.exceptions.WrongPasswordException;
import com.codecool.krk.lucidmotors.queststore.models.*;

public class LoginController {

    //private final UserInterface userInterface = new UserInterface();
    //private final LoginView loginView = new LoginView();
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
//
//        do {
//            showMenu();
//            //userChoice = //userInterface.inputs.getInput("What do you want to do: ");
//            //userInterface.clearWindow();
//            //handleUserRequest(userChoice);
//
//        } while (!userChoice.equals("0"));
    }

//    private void handleUserRequest(String userChoice) throws DaoException {
//
//        //LoginMenuOptions chosenOption = null;
//
//        switch (chosenOption) {
//            case HANDLE_LOGIN:
//                //
//                break;
//
//            case EXIT:
//                //userInterface.println("Have a nice day!");
//                break;
//
//            case DEFAULT:
//                //handleNoSuchCommand();
//        }
//    }

//    private void handleNoSuchCommand() {
////        userInterface.println("Wrong choice");
////        userInterface.pause();
//    }

//    private LoginMenuOptions getEnumValue(String userChoice) {
//        LoginMenuOptions chosenOption = null;
//
//        try {
//            //chosenOption = LoginMenuOptions.values()[Integer.parseInt(userChoice)];
//        } catch (IndexOutOfBoundsException | NumberFormatException e) {
//            //chosenOption = LoginMenuOptions.DEFAULT;
//        }
//
//        return chosenOption;
//    }

    private void showMenu() {
        //this.loginView.printLoginMenu();
    }

    public User getUser(String login, String password) throws DaoException {
        User user = this.school.getUser(login, password);

        return user;
    }

    /**
     * Runs appropriate controller based on user Type
     *
     * @param user
     * @throws DaoException
     */
    private void runUserController(User user) throws DaoException {

        if (user instanceof Manager) {
            //new ManagerController().startController(user, this.school);

        } else if (user instanceof Mentor) {
            //new MentorController().startController(user, this.school);

        } else if (user instanceof Student) {
            //new StudentController().startController(user, this.school);
        }
    }
}
