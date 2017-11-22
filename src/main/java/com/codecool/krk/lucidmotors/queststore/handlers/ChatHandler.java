package com.codecool.krk.lucidmotors.queststore.handlers;

import com.codecool.krk.lucidmotors.queststore.dao.ChatMessageDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Activity;
import com.codecool.krk.lucidmotors.queststore.models.ChatMessage;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatHandler implements HttpHandler {

    static final int CHAT_SIZE = 5;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            Map<String, String> formData = recieveData(httpExchange);
            if (formData.isEmpty()) {
                sendJson(httpExchange);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            httpExchange.sendResponseHeaders(500, 0);
        }


    }

    public void sendJson(HttpExchange httpExchange) throws DaoException, IOException {
        List<ChatMessage> chatMessages = ChatMessageDao.getDao().getMessages();
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        JSONArray jsonArray = new JSONArray();
        for (int i = chatMessages.size()-1; i >= chatMessages.size() - ChatHandler.CHAT_SIZE && i >= 0; i--) {
            ChatMessage message = chatMessages.get(i);
            jsonArray.put(message.toJson());
        }

        String response = jsonArray.toString();

        final byte[] finalResponseBytes = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(200, finalResponseBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(finalResponseBytes);
        os.close();
    }

    public Map<String, String> recieveData(HttpExchange httpExchange) throws DaoException, IOException {
        Map<String, String> formData = getFormData(httpExchange);
        if (formData.containsKey("chat-message") && formData.containsKey("chat-user")) {
            new ChatMessage(formData.get("chat-user"), formData.get("chat-message")).save();

            String newLocation = "/";
            httpExchange.getResponseHeaders().set("Location", newLocation);
            httpExchange.sendResponseHeaders(302, -1);
        }

        return formData;
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


}
