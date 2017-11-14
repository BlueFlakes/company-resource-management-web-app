package com.codecool.krk.lucidmotors.queststore.handlers;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.*;
import java.util.stream.Collectors;

public class ManagerMentorHandler implements HttpHandler {
    private School school;
    private Map<UUID, User> loggedUsers;

    public ManagerMentorHandler(School school, Map<UUID, User> loggedUsers) {
        this.school = school;
        this.loggedUsers = loggedUsers;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/logged.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("redirect", redirect(httpExchange));
        model.with("title", "Manager -> mentor");
        model.with("navigation", "static/snippets/ManagerMenuSnippet.html");
        try {
            List<List<String>> mentors = new ArrayList<>();
            for (Mentor mentor : this.school.getAllMentors()) {
                List<String> attributes = Arrays.asList(mentor.getId().toString(), mentor.getName());
                mentors.add(attributes);
            }
            model.with("mentors", mentors);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        model.with("templatePath", "classpath:/templates/empty2.html");



        response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String redirect(HttpExchange httpExchange) {
        String response = "<meta http-equiv=\"refresh\" content=\"0; url=/\" />";

        String cookieStr =  httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
        if (cookie.getName().equals("UUID")) {
            UUID uuid = UUID.fromString(cookie.getValue());
            User user = this.loggedUsers.get(uuid);
            if (user instanceof Manager) {
                response = "";
            }
        }

        return response;
    }

}
