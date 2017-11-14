package com.codecool.krk.lucidmotors.queststore.handlers;

import com.codecool.krk.lucidmotors.queststore.models.School;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class MentorHandler implements HttpHandler {
    School school;
    public MentorHandler(School school) {
        this.school = school;
    }

    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
