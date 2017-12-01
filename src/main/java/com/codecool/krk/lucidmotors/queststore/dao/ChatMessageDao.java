package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ChatMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDao {
    private static ChatMessageDao dao = null;
    private final Connection connection;
    private PreparedStatement stmt = null;

    private ChatMessageDao() throws DaoException {
        this.connection = DatabaseConnection.getConnection();
    }

    public static ChatMessageDao getDao() throws DaoException {
        if (dao == null) {

            synchronized (ChatMessageDao.class) {

                if(dao == null) {
                    dao = new ChatMessageDao();
                }
            }
        }

        return dao;
    }

    private Integer getRoomId(String roomName) throws DaoException {
        Integer roomId = 1;
        String sqlQuery = "SELECT id FROM chat_rooms WHERE name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            roomName = roomName;
            preparedStatement.setString(1, roomName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                roomId = resultSet.getInt("id");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomId;
    }

    public List<String> getRoomNames() throws DaoException {
        List<String> roomNames = new ArrayList<>();
        String sqlQuery = "SELECT name FROM chat_rooms;";
        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                roomNames.add(name);
            }
            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomNames;
    }

    public List<ChatMessage> getMessages(Integer from, String roomName) throws DaoException {
        List<ChatMessage> chatMessages = new ArrayList<>();

        Integer roomId =  getRoomId(roomName);
        String sqlQuery;
        if (roomId == null) {
            sqlQuery = "SELECT * FROM chat WHERE id > ?;";
        } else {
            sqlQuery = "SELECT * FROM chat WHERE id > ? AND room_id = ?";
        }

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, from);
            if(roomId != null) {
                stmt.setInt(2, roomId);
            }
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                String message = result.getString("message");
                ChatMessage chatMessage = new ChatMessage(id, name, message, roomName);
                chatMessages.add(chatMessage);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return chatMessages;
    }

    public void save(ChatMessage chatMessage) throws DaoException {
        String sqlQuery = "INSERT INTO chat (name, message, room_id) VALUES (?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, chatMessage.getUser());
            stmt.setString(2, chatMessage.getMessage());
            Integer roomId = getRoomId(chatMessage.getRoomName());
            stmt.setInt(3, roomId);
            stmt.execute();

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
