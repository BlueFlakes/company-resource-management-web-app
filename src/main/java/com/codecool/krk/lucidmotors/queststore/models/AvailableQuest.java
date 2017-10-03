package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.AvailableQuestDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

import java.util.ArrayList;

public class AvailableQuest extends AbstractQuest {

    private AvailableQuestDao availableQuestDao = new AvailableQuestDao();

    public AvailableQuest(String name, QuestCategory questCategory, String description,
                          Integer value) throws DaoException {

        super(name, questCategory, description, value);
    }

    public AvailableQuest(String name, QuestCategory questCategory, String description,
                          Integer value, Integer id) throws DaoException {

        super(name, questCategory, description, value, id);
    }

    public void save() throws DaoException {
        availableQuestDao.save(this);
    }

    public void update() throws DaoException {
        availableQuestDao.updateQuest(this);
    }

    public String toString() {
        return String.format("id: %d. name: %s, description: %s, value: %d", this.getId(), this.getName(),
                             this.getDescription(), this.getValue());
    }

}
