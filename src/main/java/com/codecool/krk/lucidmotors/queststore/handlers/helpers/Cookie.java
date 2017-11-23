package com.codecool.krk.lucidmotors.queststore.handlers.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;

public class Cookie {

    static final int COOKIE_MAX_AGE = 600;

    public static String getCookieValue(HttpExchange httpExchange, String name) {
        String value = null;
        String allCookies = httpExchange.getRequestHeaders().getFirst("Cookie");
        String[] cookies = allCookies != null ? allCookies.split("; ") : new String[]{};
        for(String cookie : cookies) {
            HttpCookie newCookie = HttpCookie.parse(cookie).get(0);
            if (newCookie.getName().equals(name)) {
                value = newCookie.getValue();
            }
        }

        return value;
    }

    public static void renewCookie(HttpExchange httpExchange, String name) {
        setCookie(httpExchange, name, getCookieValue(httpExchange, name));
    }

    public static void setCookie(HttpExchange httpExchange, String name, String value) {
        StringBuilder cookie = new StringBuilder();

        cookie.append(generateCookieAttribute(name, value));
        cookie.append(generateCookieAttribute("max-age", String.valueOf(COOKIE_MAX_AGE)));
        cookie.append(generateCookieAttribute("path", "/"));

        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
    }

    private static String generateCookieAttribute(String name, String value) {
        return name + "=" + value + ";";
    }

}
