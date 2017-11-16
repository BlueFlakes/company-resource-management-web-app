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

public class LoginHandler {
    private School school;
    Map<String, String> formData;
    private Map<UUID, User> loggedUsers = new HashMap<>();

    public LoginHandler(School school, Map<String, String> formData, Map<UUID, User> loggedUsers) {
        this.formData = formData;
        this.school = school;
        this.loggedUsers = loggedUsers;
    }

    public Activity getActivity(HttpExchange httpExchange) throws IOException {
        final String defaultValue = "";
        Activity activity = null;

        if(!this.formData.isEmpty()) {
            String login = formData.getOrDefault("login", defaultValue);
            String password = formData.getOrDefault("password", defaultValue);
            try {
                User user = new LoginController(this.school).getUser(login, password);
                if (user != null) {
                    UUID uuid = UUID.randomUUID();
                    this.loggedUsers.put(uuid, user);
                    HttpCookie cookie = new HttpCookie("UUID", uuid.toString());
                    httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                    activity = MainHandler.switchUser(user);
                } else {
                    activity = new Activity(302, "/");
                }
            } catch (DaoException e) {
                e.printStackTrace();
            }

        } else {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
            JtwigModel model = JtwigModel.newModel();

            String response = template.render(model);
            activity = new Activity(200, response);
        }

        return activity;
    }

    private String redirect(UUID uuid) {
        User user = this.loggedUsers.get(uuid);
        String userUrl = null;



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
