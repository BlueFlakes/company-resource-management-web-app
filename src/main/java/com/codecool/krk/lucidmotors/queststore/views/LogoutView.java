package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.models.Activity;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class LogoutView {
    public Activity getActivity() {
        String cookie = "UUID=\"\"; max-age=0;";
        return new Activity(302, "/", "Set-Cookie", cookie);
    }
}
