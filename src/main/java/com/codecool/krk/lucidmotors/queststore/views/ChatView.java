package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.dao.ChatMessageDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Activity;
import com.codecool.krk.lucidmotors.queststore.models.ChatMessage;
import org.json.JSONArray;

import java.util.List;
import java.util.Map;

public class ChatView {

    static final int CHAT_SIZE = 5;

    private Map<String, String> formData;

    public ChatView(Map<String, String> formData) {
        this.formData = formData;
    }

    public Activity getActivity() throws DaoException {
        Activity activity;
        if(isProperMessage()) {
            activity = receiveData();
        } else if (formData.containsKey("from") && formData.containsKey("room")) {
            Integer from = Integer.valueOf(formData.get("from"));
            String room = formData.get("room");
            activity = sendJson(from, room);
        } else if (formData.containsKey("get_rooms")) {
            activity = getAvailableRooms();
        } else {
            activity = new Activity(302, "/");
        }

        return activity;

    }

    public Activity sendJson(Integer from, String room) throws DaoException {
        List<ChatMessage> chatMessages = ChatMessageDao.getDao().getMessages(from, room);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < chatMessages.size(); i++) {
            ChatMessage message = chatMessages.get(i);
            jsonArray.put(message.toJson());
        }

        String response = jsonArray.toString();

        return new Activity(200, response, "Content-Type", "application/json");
    }

    public Activity receiveData() throws DaoException {
        new ChatMessage(formData.get("chat-user"), formData.get("chat-message"), formData.get("room")).save();

        return new Activity(200, "");
    }

    public Activity getAvailableRooms() throws DaoException {
        List<String> availableRooms = ChatMessageDao.getDao().getRoomNames();
        JSONArray jsonArray = new JSONArray();
        for(String room : availableRooms) {
            jsonArray.put(room);
        }

        String response = jsonArray.toString();

        return new Activity(200, response, "Content-Type", "application/json");
    }

    private Boolean isProperMessage() {
        return (formData.containsKey("chat-message") && formData.containsKey("chat-user") &&
        formData.containsKey("room"));
    }



}
