package com.codecool.krk.lucidmotors.queststore.models;

public class Manager extends User {

    public Manager(String name, String login, String password, String email) {
        super(name, login, password, email);
    }

    public Manager(String name, String login, String password, String email, Integer id) {
        super(name, login, password, email, id);
    }

    public String getManagerSaveString() {
        return String.format("%d|%s|%s|%s|%s%n", this.getId(), this.getName(), this.getLogin(), this.getPassword(), this.getEmail());
    }
}
