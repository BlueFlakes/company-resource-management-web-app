package queststore.models;

public abstract class User {
    private String name;
    private Integer id;
    private String login;
    private String password;
    private String email;
    private static Integer index = 0;

    public User(String name, String login, String password, String email) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.id = index++;
    }

    public setName(String name) {
        this.name = name;
    }

    public setId(Integer id) {
        this.id = id;
    }

    public setLogin(String login) {
        this.login = login;
    }

    public setEmail(String email) {
        this.email = email;
    }

    public setPassword(String password) {
        this.password = password;
    }

    public getName() {
        return this.name;
    }

    public getId() {
        return this.id;
    }

    public getLogin() {
        return this.login;
    }

    public getEmail() {
        return this.email;
    }

    public getPassword() {
        return this.password;
    }
}
