package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.AchievedQuestDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

import java.util.Date;

public class AchievedQuest extends AbstractQuest {

    private Student owner;
    private Date date;
    private AchievedQuestDao achievedQuestDao = new AchievedQuestDao();

    public AchievedQuest(AvailableQuest availableQuest, Student owner) throws DaoException {
        super(availableQuest.getName(), availableQuest.getQuestCategory(),
              availableQuest.getDescription(), availableQuest.getValue());
        this.date = new Date();
        this.owner = owner;
    }

    public AchievedQuest(String name, QuestCategory questCategory, String description, Integer value, Integer id,
                         Date date, Student owner) throws DaoException {
        super(name, questCategory, description, value, id);
        this.date = date;
        this.owner = owner;
    }

    public Student getOwner() {
        return owner;
    }

    public void setOwner(Student owner) {
        this.owner = owner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void save() throws DaoException {
        achievedQuestDao.saveQuest(this);
    }
}
