package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ChatMessageDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import org.json.JSONObject;

public class ChatMessage {
    Integer id;
    String user;
    String message;

    public ChatMessage(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public ChatMessage(Integer id, String user, String message) {
        this.id = id;
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject toJson() {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("id", this.id);
        jsonMessage.put("user", this.user);
        jsonMessage.put("message", this.message);

        return jsonMessage;
    }

    public void save() throws DaoException {
        ChatMessageDao.getDao().save(this);
    }
}
