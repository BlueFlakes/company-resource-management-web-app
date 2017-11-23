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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        URIResponse parsedURI = parseURI(uri);

        if(user == null) {
            activity = new LoginView(this.school, formData, this.loggedUsers).getActivity(httpExchange);
        } else if (isProperUser(parsedURI.getRole(), user)) {
            Cookie.renewCookie(httpExchange, "UUID");
            activity = getUserActivity(parsedURI, formData, user);
        } else {
            activity = getOtherActivity(parsedURI.getRole(), formData, user);
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

    private boolean isProperUser(Roles role, User user) {
        return switchUser(user) == role;
    }

    static Roles switchUser(User user) {
        Roles userUrl;

        if (user instanceof Manager) {
            userUrl = Roles.MANAGER;
        } else if (user instanceof Mentor) {
            userUrl = Roles.MENTOR;
        } else if (user instanceof Student) {
            userUrl = Roles.STUDENT;
        } else {
            userUrl = null;
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

    private URIResponse parseURI (String uri) {
        String[] uriList = uri.split("/");
        URIResponse response = null;

        if (uriList.length > 1 && checkIsProperRole(uriList)) {
            Roles role = EnumUtils.getValue(Roles.class, uriList[1].toUpperCase());

            if (uriList.length == 2) {
                response = new URIResponse(role, Action.DEFAULT, "");
            }

            if (uriList.length == 3) {
                String request = uriList[2].toUpperCase();
                response = new URIResponse(role, Action.getUserAction(role), request);
            }
        } else {
            response = new URIResponse(Roles.DEFAULT, Action.DEFAULT, "");
        }

        return response;
    }

    public Activity getUserActivity(URIResponse response, Map<String, String> formData, User user) throws DaoException, IOException {
        switch (response.getRole()) {
            case MANAGER:
                return new ManagerView(this.school, user, formData).getActivity((ManagerOptions) getProperAction(response));

            case MENTOR:
                return new MentorView(this.school).getActivity((MentorOptions) getProperAction(response));

            case STUDENT:
                return new StudentView(user, formData).getActivity((StudentOptions) getProperAction(response));

        }
        return null;
    }

    private Enum getProperAction(URIResponse uriResponse) {
        Action action = uriResponse.getAction();
        String command = uriResponse.getCommand();

        return action.prepareCommand(uriResponse.getRole(), command);
    }

    private Activity getOtherActivity(Roles role, Map<String, String> formData, User user) throws DaoException {
//        TODO: mam wrażenie że action powinno byc zamiast role
        switch (role) {
            case LOGOUT:
                return new LogoutView().getActivity();

            case CHAT:
                return new ChatView(formData).getActivity();

            case DEFAULT:
                return redirectByUser(user);
        }

        return null;
    }

    public static Activity redirectByUser(User user) {
        String userUrl = "/";

        if (switchUser(user) != null) {
            userUrl = "/" + switchUser(user).toString().toLowerCase();
        }

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
