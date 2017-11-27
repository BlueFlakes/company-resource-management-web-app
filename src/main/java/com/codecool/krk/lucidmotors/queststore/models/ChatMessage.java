package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.ChatMessageDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import org.json.JSONObject;

public class ChatMessage {
    Integer id;
    String user;
    String message;
    String roomName;

    public ChatMessage(String user, String message, String roomName) {
        this.user = user;
        this.message = message;
        this.roomName = roomName;
    }

    public ChatMessage(Integer id, String user, String message, String roomName) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.roomName = roomName;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getRoomName() {
        return roomName;
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
