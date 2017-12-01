package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.handlers.MainHandler;
import com.codecool.krk.lucidmotors.queststore.handlers.Static;
import com.codecool.krk.lucidmotors.queststore.handlers.StyleHandler;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Random;

public class MainController {

    School school;
    HttpServer server;
    public MainController(School school) {
        this.school = school;
    }

    private final Integer START_PORT = 8000;
    private final Integer RANGE = 2000;
    private Integer port = new Random().nextInt(RANGE) + START_PORT;

    public void startServer() throws IOException {

        this.server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new MainHandler(this.school));
        server.createContext("/static/css/style.css", new StyleHandler());
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor

        server.start();
        System.out.println("Server started on port: " + this.port);
    }

    public void stop() {
        this.server.stop(0);
    }
}
