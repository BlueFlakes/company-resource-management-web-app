package com.codecool.krk.lucidmotors.queststore.models;


import com.codecool.krk.lucidmotors.queststore.enums.Action;
import com.codecool.krk.lucidmotors.queststore.enums.Roles;
import lombok.Getter;

@Getter
public class URIResponse {
    private Roles role;
    private Action action;
    private String command;

    public URIResponse(Roles role, Action action, String command) {
        this.role = role;
        this.action = action;
        this.command = command;
    }

    @Override
    public String toString( ) {
        return "role: " + this.role.toString() + " | action: " + this.action.toString() + " | command: " + this.command;
    }
}
