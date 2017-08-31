package queststore.exceptions;

public class LoginInUseException extends Exception {

    public LoginInUseException() {
      super("Login already in use! Choose different one!");
    }
}
