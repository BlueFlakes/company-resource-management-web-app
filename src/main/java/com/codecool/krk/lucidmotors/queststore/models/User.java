package com.codecool.krk.lucidmotors.queststore.models;

import org.json.JSONObject;

public abstract class User {

    private String name;
    private Integer id;
    private String login;
    private String password;
    private String email;

    public User(String name, String login, String password, String email) {

        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.id = null;
    }

    public User(String name, String login, String password, String email, Integer id) {

        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.id = id;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.login;
    }

    public JSONObject toJSON() {
        JSONObject jsonUser = new JSONObject();

        jsonUser.put("name", this.name);
        jsonUser.put("login", this.login);
        jsonUser.put("password", this.password);
        jsonUser.put("email", this.email);
        jsonUser.put("id", this.id.toString());

        return jsonUser;
    }
}
