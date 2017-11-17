package com.codecool.krk.lucidmotors.queststore.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class LogoutHandler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        String cookie = "UUID=\"\"; max-age=0;";
        httpExchange.getResponseHeaders().add("Set-Cookie",cookie);

        String newLocation = "/";
        httpExchange.getResponseHeaders().set("Location", newLocation);
        httpExchange.sendResponseHeaders(302, -1);
    }
}
