package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.dao.AvailableQuestDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import org.json.JSONObject;

import java.math.BigInteger;

public class AvailableQuest extends AbstractQuest {

    private AvailableQuestDao availableQuestDao = AvailableQuestDao.getDao();

    public AvailableQuest(String name, QuestCategory questCategory, String description,
                          BigInteger value) throws DaoException {

        super(name, questCategory, description, value);
    }

    public AvailableQuest(String name, QuestCategory questCategory, String description,
                          BigInteger value, Integer id) throws DaoException {

        super(name, questCategory, description, value, id);
    }

    public void save() throws DaoException {
        availableQuestDao.save(this);
    }

    public void update() throws DaoException {
        availableQuestDao.updateQuest(this);
    }

    @Override
    public String toString() {
        Integer id = this.getId();
        String name = this.getName();
        String description = this.getDescription();
        BigInteger value = this.getValue();

        return ("\t" + id + ". " + name + ": " + description + "; value: " + value);
    }

    public JSONObject toJson() {
        JSONObject jsonQuest = new JSONObject();
        jsonQuest.put("id", this.getId().toString());
        jsonQuest.put("name", this.getName());
        jsonQuest.put("value", this.getValue());
        jsonQuest.put("description", this.getDescription());
        jsonQuest.put("categoryId", this.getQuestCategory().getId());
        jsonQuest.put("categoryName", this.getQuestCategory().getName());

        return jsonQuest;
    }

}
