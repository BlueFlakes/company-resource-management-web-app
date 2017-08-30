package questore.controllers;

import java.util.ArrayList;

import queststore.views.View;
import queststore.models.School;
import queststore.models.User;
import queststore.models.Student;
import queststore.models.Mentor;
import queststore.models.Manager;
import queststore.exceptions.WrongPasswordException;


public class LoginController {

    private School school;
    private View view = new View();

    public LoginController(School school) {
        this.school = school;
    }

    public void start() {
        String login = view.getInput("Please provide your login: ");
        String password = view.getInput("Please provide your password: ");

        try {
            User user = findUser(login, password);
            runUserController(user);
        } catch (WrongPasswordException e) {
            view.print(e.getMessage());
        }

    }

    private User findUser(String login, String password) throws WrongPasswordException {
        ArrayList<User> users = school.getAllUsers();

        for (User user : users) {
            String correctLogin = user.getLogin();
            String correctPassword = user.getPassword();

            if (correctLogin.equals(login) && correctPassword.equals(password)) {
                return user;
            }
        }

        throw new WrongPasswordException();
    }

    private void runUserController(User user) {
        if (user instanceof Manager) {
            new ManagerController().startController();
        }
        else if (user instanceof Mentor) {
            new MentorController().startController();
        }
        else if (user instanceof Student) {
            new StudentController().startController();
        }
    }
}
