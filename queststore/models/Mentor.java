package queststore.models;

public class Mentor extends User {

    public Mentor(String name, String login, String password, String email) {
        super(name, login, password, email);
    }

    public Mentor(String name, String login, String password, String email, Integer id) {
        super(name, login, password, email, id);
    }
}
