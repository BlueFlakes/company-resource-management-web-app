package com.codecool.krk.lucidmotors.queststore.handlers;

import com.codecool.krk.lucidmotors.queststore.controllers.LoginController;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.*;

public class LoginHandler implements HttpHandler {
    private School school;
    private Map<UUID, User> loggedUsers;

    public LoginHandler(School school, Map<UUID, User> loggedUsers) {
        this.loggedUsers = loggedUsers;
        this.school = school;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response;

        String cookieStr =  httpExchange.getRequestHeaders().getFirst("Cookie");

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
        JtwigModel model = JtwigModel.newModel();

        String method = httpExchange.getRequestMethod();

        if(cookieStr != null) {
            HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
            if (cookie.getName().equals("UUID")) {
                UUID uuid = UUID.fromString(cookie.getValue());
                model.with("redirect", redirect(uuid));
            }

        } else if (method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            Map inputs = parseFormData(formData);

            String login = inputs.get("login").toString();
            String password = inputs.get("password").toString();
            try {
                Optional<User> userOptional = new LoginController(this.school).getUser(login, password);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    UUID uuid = UUID.randomUUID();
                    this.loggedUsers.put(uuid, user);
                    HttpCookie cookie = new HttpCookie("UUID", uuid.toString());
                    httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                    model.with("redirect", redirect(uuid));
                }
            } catch (DaoException e) {
                e.printStackTrace();
            }

        }

        response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String redirect(UUID uuid) {
        User user = this.loggedUsers.get(uuid);
        String userUrl = null;

        if (user == null) {
            userUrl = "/logout";
        } else if (user instanceof Manager) {
            userUrl = "/manager";
        } else if (user instanceof Mentor) {
            userUrl = "/mentor";
        } else if (user instanceof Student) {
            userUrl = "/student";
        }

        String content;
        if(userUrl != null) {
            content = String.format("<meta http-equiv=\"refresh\" content=\"0; url=%s\" />", userUrl);
        } else {
            content = "";
        }

        return content;
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = (keyValue.length > 1) ? new URLDecoder().decode(keyValue[1], "UTF-8") : "";
            map.put(keyValue[0], value);
        }
        return map;
    }
}
