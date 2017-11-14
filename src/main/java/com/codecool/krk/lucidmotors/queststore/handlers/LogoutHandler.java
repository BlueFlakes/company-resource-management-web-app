package com.codecool.krk.lucidmotors.queststore.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class LogoutHandler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        String cookie = "UUID=\"\"; expires=01 Jan 1900;";
        httpExchange.getResponseHeaders().add("Set-Cookie",cookie);

        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/logged.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("redirect", "<meta http-equiv=\"refresh\" content=\"0; url=/\" />");

        response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
