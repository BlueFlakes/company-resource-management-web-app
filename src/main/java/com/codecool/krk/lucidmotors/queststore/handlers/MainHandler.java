package com.codecool.krk.lucidmotors.queststore.handlers;

import com.codecool.krk.lucidmotors.queststore.enums.*;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.handlers.helpers.Cookie;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

        Activity activity;
        try {
            activity = getActivity(httpExchange);
        } catch (DaoException e) {
            e.printStackTrace();
            activity = new Activity(500, e.toString());
        }
        sendResponse(activity, httpExchange);
    }

    private Activity getActivity(HttpExchange httpExchange) throws IOException, DaoException {
        Activity activity;

        Map<String, String> formData = getFormData(httpExchange);
        User user = getUserByCookie(httpExchange);

        String uri = httpExchange.getRequestURI().getPath();
        Map<String, String> parsedURI = parseURI(uri);
        String role =  parsedURI.get("role");
        String action = parsedURI.get("action").toUpperCase();

        if(user == null) {
            activity = new LoginView(this.school, formData, this.loggedUsers).getActivity(httpExchange);
        } else if (isProperUser(role, user)) {
            Cookie.renewCookie(httpExchange, "UUID");
            activity = getUserActivity(role, action, formData, user);
        } else {
            activity = getOtherActivity(role, formData, user);
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

    private boolean isProperUser(String role, User user) {
        return switchUser(user).equalsIgnoreCase(role);
    }

    static String switchUser(User user) {
        String userUrl;

        if (user instanceof Manager) {
            userUrl = "manager";
        } else if (user instanceof Mentor) {
            userUrl = "mentor";
        } else if (user instanceof Student) {
            userUrl = "student";
        } else {
            userUrl = "";
        }

        return userUrl;
    }

    public User getUserByCookie(HttpExchange httpExchange) {
        User user = null;
        String uuid = Cookie.getCookieValue(httpExchange, "UUID");
        if (uuid != null) {
            user = loggedUsers.getOrDefault(UUID.fromString(uuid), null);
        }

        return user;
    }

    private Map<String, String> parseURI (String uri) {
        Map<String, String> parsedURI = new HashMap<>();
        String[] uriList = uri.split("/");

        if (uriList.length == 2 && checkIsProperRole(uriList)) {
            parsedURI.put("role", uriList[1]);
            parsedURI.put("action", "");
        } else if (uriList.length == 3 && checkIsProperRole(uriList)) {
            parsedURI.put("role", uriList[1]);
            parsedURI.put("action", uriList[2]);
        } else {
            parsedURI.put("role", "");
            parsedURI.put("action", "");
        }

        return parsedURI;
    }

    public Activity getUserActivity(String role, String action, Map<String, String> formData, User user) throws DaoException, IOException {
        switch (role) {
            case "manager":
                return new ManagerView(this.school, user, formData).getActivity(EnumUtils.getValue(ManagerOptions.class, action));

            case "mentor":
                return new MentorView(this.school, user, formData).getActivity(EnumUtils.getValue(MentorOptions.class, action));

            case "student":
                return new StudentView(user, formData).getActivity(EnumUtils.getValue(StudentOptions.class, action));

        }
        return null;
    }

    private Activity getOtherActivity(String role, Map<String, String> formData, User user) throws DaoException {
        switch (role) {
            case "logout":
                return new LogoutView().getActivity();

            case "chat":
                return new ChatView(formData).getActivity();

            default:
                return redirectByUser(user);
        }
    }

    public static Activity redirectByUser(User user) {
        String userUrl = "/" + switchUser(user);

        return new Activity(302, userUrl);
    }

    private Boolean checkIsProperRole(String[] uriList) {
        Boolean isProperRole;
        try {
            Roles.valueOf(uriList[1].toUpperCase());
            isProperRole = true;
        } catch (IllegalArgumentException e) {
            isProperRole = false;
        }

        return isProperRole;
    }

    private void sendResponse(Activity activity, HttpExchange httpExchange) throws IOException {
        if (activity.hasHeader()) {
            httpExchange.getResponseHeaders().add(activity.getHeaderName(), activity.getHeaderContent());
        }

        if (activity.getHttpStatusCode().equals(200)) {
            String response = activity.getAnswer();
            writeHttpOutputStream(activity.getHttpStatusCode(), response, httpExchange);

        } else if (activity.getHttpStatusCode().equals(302)) {
            String newLocation = activity.getAnswer();
            httpExchange.getResponseHeaders().set("Location", newLocation);
            httpExchange.sendResponseHeaders(302, -1);

        } else if (activity.getHttpStatusCode().equals(500)) {
            httpExchange.sendResponseHeaders(500, 0);

        } else {
            String response = "404 (Not Found)\n";
            int httpStatusCode = 404;
            writeHttpOutputStream(httpStatusCode, response, httpExchange);
        }
    }

    private void writeHttpOutputStream(int httpStatusCode, String response, HttpExchange httpExchange) throws IOException {
        final byte[] finalResponseBytes = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(httpStatusCode, finalResponseBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(finalResponseBytes);
        os.close();
    }
}
