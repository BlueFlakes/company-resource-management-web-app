package com.codecool.krk.lucidmotors.queststore.handlers;

import com.codecool.krk.lucidmotors.queststore.enums.LoginMenuOptions;
import com.codecool.krk.lucidmotors.queststore.enums.ManagerOptions;
import com.codecool.krk.lucidmotors.queststore.enums.MentorOptions;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

public class MainHandler implements HttpHandler {

    private Map<UUID, User> loggedUsers = new HashMap<>();
    private School school;

    public MainHandler(School school) {
        this.school = school;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //reading state

        Activity activity = getActivity(httpExchange);

        sendResponse(activity, httpExchange);


    }

    private Activity getActivity(HttpExchange httpExchange) throws IOException {
        Activity activity = null;

        Map<String, String> formData = getFormData(httpExchange);
        User user = getUserByCookie(httpExchange);

        String uri = httpExchange.getRequestURI().getPath();
        Map<String, String> parsedURI = parseURI(uri);
        String role =  parsedURI.get("role");
        String action = parsedURI.get("action");

        if(user == null) {
            activity = new LoginHandler(this.school, formData, this.loggedUsers).getActivity(httpExchange);
        } else if (!role.equals("")) {
            switch (role) {
                case "manager":
                    activity = new ManagerHandler(this.school).getActivity(getManagerEnumValue(action));
                    break;

                case "mentor":
                    activity = new MentorHandler(this.school).getActivity(getMentorEnumValue(action));
                    break;
            }
        } else {
            activity = switchUser(user);
        }

        return activity;
    }

    private Map<String,String> getFormData(HttpExchange httpExchange) throws IOException {
        Map<String, String> postValues = new HashMap<>();

        String method = httpExchange.getRequestMethod();

        if(method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            String[] pairs = formData.split("&");
            for(String pair : pairs){
                String[] keyValue = pair.split("=");
                String value = (keyValue.length > 1) ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
                postValues.put(keyValue[0], value);
            }
        }

        return postValues;
    }

    private User getUserByCookie(HttpExchange httpExchange) {
        User user = null;

        String allCookies = httpExchange.getRequestHeaders().getFirst("Cookie");
        String[] cookies = allCookies != null ? allCookies.split("; ") : new String[]{};
        for(String cookie : cookies) {
            HttpCookie newCookie = HttpCookie.parse(cookie).get(0);
            if (newCookie.getName().equals("UUID")) {
                user = loggedUsers.getOrDefault(UUID.fromString(newCookie.getValue()), null);

            }
        }

        return user;
    }

    static Activity switchUser(User user) {
        String userUrl;

        if (user instanceof Manager) {
            userUrl = "/manager";
        } else if (user instanceof Mentor) {
            userUrl = "/mentor";
        } else if (user instanceof Student) {
            userUrl = "/student";
        } else {
            userUrl = "/";
        }

        return new Activity(302, userUrl);
    }

    private Map<String, String> parseURI (String uri) {
        Map<String, String> parsedURI = new HashMap<String, String>();
        String[] uriList = uri.split("/");
        List<String> roles = Arrays.asList("student", "mentor", "manager", "logout");

        if (uriList.length == 2 && roles.contains(uriList[1])) {
            parsedURI.put("role", uriList[1]);
            parsedURI.put("action", "");
        } else if (uriList.length == 3 && roles.contains(uriList[1])) {
            parsedURI.put("role", uriList[1]);
            parsedURI.put("action", uriList[2]);
        } else {
            parsedURI.put("role", "");
            parsedURI.put("action", "");
        }

        return parsedURI;
    }

    private ManagerOptions getManagerEnumValue(String userAction) {
        ManagerOptions chosenOption;

        try {
            chosenOption = ManagerOptions.valueOf(userAction.toUpperCase());
        } catch (IllegalArgumentException e) {
            chosenOption = ManagerOptions.DEFAULT;
        }

        return chosenOption;
    }

    private MentorOptions getMentorEnumValue(String userAction) {
        MentorOptions chosenOption;

        try {
            chosenOption = MentorOptions.valueOf(userAction.toUpperCase());
        } catch (IllegalArgumentException e) {
            chosenOption = MentorOptions.DEFAULT;
        }

        return chosenOption;
    }


    private void sendResponse(Activity activity, HttpExchange httpExchange) throws IOException {
        if (activity.getHttpStatusCode().equals(200)) {
            String response = activity.getAnswer();

            final byte[] finalResponseBytes = response.getBytes("UTF-8");
            httpExchange.sendResponseHeaders(200, finalResponseBytes.length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(finalResponseBytes);
            os.close();
        } else if (activity.getHttpStatusCode().equals(302)) {
            String newLocation = activity.getAnswer();
            httpExchange.getResponseHeaders().set("Location", newLocation);
            httpExchange.sendResponseHeaders(302, -1);
        } else {
            String response = "404 (Not Found)\n";
            httpExchange.sendResponseHeaders(404, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }

}
