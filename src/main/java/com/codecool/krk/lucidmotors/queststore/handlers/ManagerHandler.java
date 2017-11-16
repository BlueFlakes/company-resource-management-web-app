package com.codecool.krk.lucidmotors.queststore.handlers;

import com.codecool.krk.lucidmotors.queststore.enums.ManagerOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Activity;
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

public class ManagerHandler {
    private School school;
//    private Map<UUID, User> loggedUsers;

    public ManagerHandler(School school) {
        this.school = school;
//        this.loggedUsers = loggedUsers;
    }

    public Activity getActivity(ManagerOptions managerOption) throws IOException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("title", managerOption.toString());
        model.with("menu_path", "classpath:/templates/snippets/manager-menu-snippet.twig");
        try {
            insertData(managerOption, model);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        String contentPath = "classpath:/" + managerOption.getPath();
        model.with("content_path", contentPath);

        response = template.render(model);

        return new Activity(200, response);
    }

    private void insertData(ManagerOptions managerOption, JtwigModel model) throws DaoException {
        switch (managerOption) {
            case SHOW_MENTORS_CLASS:
                model.with("mentors", this.school.getAllMentors());
                break;
        }
    }


}
