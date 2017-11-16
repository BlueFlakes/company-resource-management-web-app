package com.codecool.krk.lucidmotors.queststore.controllers;
import com.codecool.krk.lucidmotors.queststore.handlers.*;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.User;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainController {

    School school;

    public MainController(School school) {
        this.school = school;
    }

    private static Integer port = 8000;

    public void startServer() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new MainHandler(this.school));
        server.createContext("/logout", new LogoutHandler());
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor

        server.start();
        System.out.println("Server started");
    }
}
