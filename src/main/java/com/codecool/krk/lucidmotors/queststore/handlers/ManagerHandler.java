package com.codecool.krk.lucidmotors.queststore.handlers;

import com.codecool.krk.lucidmotors.queststore.models.Manager;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.Map;
import java.util.UUID;

public class ManagerHandler implements HttpHandler {
    private School school;
    private Map<UUID, User> loggedUsers;

    public ManagerHandler(School school, Map<UUID, User> loggedUsers) {
        this.school = school;
        this.loggedUsers = loggedUsers;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/logged.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("redirect", redirect(httpExchange));
        model.with("title", "Manager menu");
        model.with("navigation", "static/snippets/ManagerMenuSnippet.html");
        model.with("templatePath", "classpath:/templates/empty.twig");

        response = template.render(model);

        final byte[] finalResponseBytes = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(200, finalResponseBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(finalResponseBytes);
        os.close();
    }

    private String redirect(HttpExchange httpExchange) {
        String response = "<meta http-equiv=\"refresh\" content=\"0; url=/\" />";

        String cookieStr =  httpExchange.getRequestHeaders().getFirst("Cookie");
        if(cookieStr != null) {
            HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
            if (cookie.getName().equals("UUID")) {
                UUID uuid = UUID.fromString(cookie.getValue());
                User user = this.loggedUsers.get(uuid);
                if (user instanceof Manager) {
                    response = "";
                }
            }
        }



        return response;
    }

}
