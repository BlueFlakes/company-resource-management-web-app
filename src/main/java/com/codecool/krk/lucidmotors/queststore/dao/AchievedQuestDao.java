package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.QuestCategory;
import com.codecool.krk.lucidmotors.queststore.models.AchievedQuest;
import com.codecool.krk.lucidmotors.queststore.models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AchievedQuestDao {

    private static AchievedQuestDao dao = null;
    private final Connection connection;
    private PreparedStatement stmt = null;
    private QuestCategoryDao questCategoryDao = QuestCategoryDao.getDao();
    private StudentDao studentDao = StudentDao.getDao();

    private AchievedQuestDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public static AchievedQuestDao getDao() throws DaoException {
        if (dao == null) {

            synchronized (AvailableQuestDao.class) {

                if(dao == null) {
                    dao = new AchievedQuestDao();
                }
            }
        }

        return dao;
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        Date achieveDate = dateFormatter.parse(dateString);

        return achieveDate;
    }

    private String convertDateToString(Date achieveDate) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String achieveDateString = dateFormatter.format(achieveDate);

        return achieveDateString;
    }

    public AchievedQuest getQuest(Integer id) throws DaoException {

        AchievedQuest achievedQuest = null;
        String sqlQuery = "SELECT * FROM achieved_quests WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                Integer value = result.getInt("value");
                Integer categoryId = result.getInt("category_id");
                String description = result.getString("description");
                Integer ownerId = result.getInt("owner_id");
                String achieveDateString = result.getString("date");

                QuestCategory questCategory = questCategoryDao.getQuestCategory(categoryId);
                Date achieveDate = this.parseDate(achieveDateString);
                Student owner = studentDao.getStudent(ownerId);

                achievedQuest = new AchievedQuest(name, questCategory, description, value, id, achieveDate, owner);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        } catch (ParseException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem! Wrong database date data!");
        }

        return achievedQuest;

    }

    public List<AchievedQuest> getAllQuests() throws DaoException {

        List<AchievedQuest> achievedQuests = new ArrayList<>();
        String sqlQuery = "SELECT * FROM achieved_quests;";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                Integer value = result.getInt("value");
                Integer categoryId = result.getInt("category_id");
                String description = result.getString("description");
                Integer ownerId = result.getInt("owner_id");
                String achieveDateString = result.getString("date");

                QuestCategory questCategory = questCategoryDao.getQuestCategory(categoryId);
                Date achieveDate = this.parseDate(achieveDateString);
                Student owner = studentDao.getStudent(ownerId);

                AchievedQuest achievedQuest = new AchievedQuest(name, questCategory, description, value, id,
                                                                achieveDate, owner);
                achievedQuests.add(achievedQuest);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        } catch (ParseException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem! Wrong database date data!");
        }

        return achievedQuests;
    }

    public List<AchievedQuest> getAllQuestsByStudent(Student student) throws DaoException {

        Integer ownerId = student.getId();
        List<AchievedQuest> achievedQuests = new ArrayList<>();
        String sqlQuery = "SELECT * FROM achieved_quests WHERE owner_id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, ownerId);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                Integer value = result.getInt("value");
                Integer categoryId = result.getInt("category_id");
                String description = result.getString("description");
                String achieveDateString = result.getString("date");

                QuestCategory questCategory = questCategoryDao.getQuestCategory(categoryId);
                Date achieveDate = this.parseDate(achieveDateString);

                AchievedQuest achievedQuest = new AchievedQuest(name, questCategory, description, value, id,
                                                                achieveDate, student);

                achievedQuests.add(achievedQuest);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        } catch (ParseException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem! Wrong database date data!");
        }

        return achievedQuests;
    }

    public void saveQuest(AchievedQuest achievedQuest) throws DaoException {
        String name = achievedQuest.getName();
        Integer value = achievedQuest.getValue();
        Integer categoryId = achievedQuest.getQuestCategory().getId();
        String description = achievedQuest.getDescription();
        Integer ownerId = achievedQuest.getOwner().getId();

        Date achieveDate = achievedQuest.getDate();

        String achieveDateString = this.convertDateToString(achieveDate);

        String sqlQuery = "INSERT INTO achieved_quests "
                + "(name, description, value, owner_id, date, category_id) "
                + "VALUES (?, ?, ?, ?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, value);
            stmt.setInt(4, ownerId);
            stmt.setString(5, achieveDateString);
            stmt.setInt(6, categoryId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }
    }

}
