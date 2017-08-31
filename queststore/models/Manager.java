package queststore.models;

public class Manager extends User{
    public Manager(String name, String login, String password, String email) {
        super(name, login, password, email);
    }

    public Manager(String name, String login, String password, String email, Integer id) {
        super(name, login, password, email, id);
    }

    public Class getClas() {
        return this.class_;
    }
}
