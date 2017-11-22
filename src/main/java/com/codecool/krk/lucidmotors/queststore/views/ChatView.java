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

    public Activity getActivity() throws DaoException{
        Activity activity;
        if(isProperMessage()) {
            activity = receiveData();
        } else {
            activity = sendJson();
        }

        return activity;

    }

    public Activity sendJson() throws DaoException {
        List<ChatMessage> chatMessages = ChatMessageDao.getDao().getMessages();
        JSONArray jsonArray = new JSONArray();
        for (int i = chatMessages.size()-1; i >= chatMessages.size() - ChatView.CHAT_SIZE && i >= 0; i--) {
            ChatMessage message = chatMessages.get(i);
            jsonArray.put(message.toJson());
        }

        String response = jsonArray.toString();

        return new Activity(200, response, "Content-Type", "application/json");
    }

    public Activity receiveData() throws DaoException {
        new ChatMessage(formData.get("chat-user"), formData.get("chat-message")).save();

        return new Activity(302, "/");
    }

    private Boolean isProperMessage() {
        return (formData.containsKey("chat-message") && formData.containsKey("chat-user"));
    }



}
