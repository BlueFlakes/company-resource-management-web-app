package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.models.Activity;
import com.codecool.krk.lucidmotors.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LogoutView {
    User user;
    Map<UUID, User> loggedUsers;

    public LogoutView(User user, Map<UUID, User> loggedUsers) {
        this.user = user;
        this.loggedUsers = loggedUsers;
    }

    public Activity getActivity() {
        String cookie = "UUID=\"\"; max-age=0;";
        Optional<UUID> keyToRemove = this.loggedUsers.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().equals(this.user))
                        .map(entry -> entry.getKey())
                        .findAny();

        if(keyToRemove.isPresent()) {
            loggedUsers.remove(keyToRemove.get());
        }

        return new Activity(302, "/", "Set-Cookie", cookie);
    }
}
