package queststore.controllers;

import java.util.ArrayList;

import queststore.views.UserInterface;
import queststore.models.School;
import queststore.models.User;
import queststore.models.Student;
import queststore.models.Mentor;
import queststore.models.Manager;
import queststore.exceptions.WrongPasswordException;


public class LoginController {

    private School school;
    private UserInterface userInterface = new UserInterface();

    public LoginController(School school) {
        this.school = school;
    }

    public void start() throws WrongPasswordException {
        String login = userInterface.getInput("Please provide your login");
        String givenPassword = userInterface.getInput("Please provide your password");

        User user = this.school.getUser(login);
        if (user != null) {
            String expectedPassword = user.getPassword();
            if (expectedPassword.equals(givenPassword)) {
                runUserController(user);
            }
            
            else {
                throw new WrongPasswordException();
            }
        }
        else{
            throw new WrongPasswordException();
        }

    }

    private void runUserController(User user) {
        if (user instanceof Manager) {
            new ManagerController().startController(user, this.school);
        }
        else if (user instanceof Mentor) {
            new MentorController().startController(user, this.school);
        }
        else if (user instanceof Student) {
            new StudentController().startController(user, this.school);
        }
    }
}
