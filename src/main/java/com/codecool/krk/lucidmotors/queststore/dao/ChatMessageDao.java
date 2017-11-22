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

    public List<ChatMessage> getMessages() throws DaoException {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        String sqlQuery = "SELECT * FROM chat";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                String message = result.getString("message");
                ChatMessage chatMessage = new ChatMessage(name, message);
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
        String sqlQuery = "INSERT INTO chat (name, message) VALUES (?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, chatMessage.getUser());
            stmt.setString(2, chatMessage.getMessage());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ChatMessage.class + " class caused a problem!");
        }
    }

}
