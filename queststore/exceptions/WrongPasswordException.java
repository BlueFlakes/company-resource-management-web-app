package queststore.exceptions;

public class WrongPasswordException extends Exception {

    public WrongKeyException() {
      super("Wrong login or password!");
    }
}
