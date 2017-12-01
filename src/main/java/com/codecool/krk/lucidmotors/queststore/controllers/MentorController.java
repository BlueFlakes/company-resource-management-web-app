package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.*;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class MentorController {
    private QuestCategoryDao questCategoryDao = QuestCategoryDao.getDao();
    private AvailableQuestDao availableQuestDao = AvailableQuestDao.getDao();
    School school;

    public MentorController(School school) throws DaoException {
        this.school = school;
    }

    public void changeQuestData(Map<String, String> formData) throws DaoException {
        Integer id = Integer.parseInt(formData.get("quest_id"));
        String name = formData.get("quest_name");
        QuestCategory questCategory = this.questCategoryDao.getQuestCategory(Integer.parseInt(formData.get("category_id")));
        String description = formData.get("description");

        BigInteger value = new BigInteger(formData.get("value"));
        BigInteger maxValue = null;
        if(formData.containsKey("max_value")) {
            maxValue = new BigInteger(formData.get("max_value"));
        }
        AvailableQuest quest = this.availableQuestDao.getQuest(id);
        quest.setName(name);
        quest.setQuestCategory(questCategory);
        quest.setDescription(description);
        quest.setValue(value);
        if(formData.containsKey("max_value")) {
            quest.setMaxValue(maxValue);
        }
        quest.update();
    }

    public void createNewAvailableQuest(Map<String, String> formData) throws DaoException {
        String name = formData.get("quest_name");
        QuestCategory questCategory = this.questCategoryDao.getQuestCategory(Integer.parseInt(formData.get("category_id")));
        String description = formData.get("description");

        BigInteger value = new BigInteger(formData.get("quest_value"));

        AvailableQuest questToAdd;
        if(formData.containsKey("max_value")) {
            BigInteger maxValue = new BigInteger(formData.get("max_value"));
            questToAdd = new AvailableQuest(name, questCategory, description, value, maxValue);
        } else {
            questToAdd = new AvailableQuest(name, questCategory, description, value);
        }
        questToAdd.save();
        String message = String.format("Take the new quest: %s. You can get %d cc!", questToAdd.getName(), questToAdd.getValue());
        new ChatMessage("system", message, "System messages").save();
    }

    public boolean addStudent(Map<String, String> formData) throws DaoException {
        Boolean isAdded = false;

        if (this.school.isLoginAvailable(formData.get("login"))) {
            String name = formData.get("name");
            String login = formData.get("login");
            String password = formData.get("password");
            String email = formData.get("email");
            Integer classId = Integer.valueOf(formData.get("class_id"));
            SchoolClass chosenClass = chooseProperClass(classId);
            Student student = new Student(name, login, password, email, chosenClass);
            student.save();
            isAdded = true;
        }

        return isAdded;
    }

    private SchoolClass chooseProperClass(Integer classId) throws DaoException {

        SchoolClass schoolClass = ClassDao.getDao().getSchoolClass(classId);

        return schoolClass;
    }

    public boolean addQuestCategory(Map<String, String> formData) throws DaoException {
        String questCategoryName = formData.get("name");

        if (questCategoryDao.getQuestByName(questCategoryName) == null) {
            QuestCategory questCategory = new QuestCategory(questCategoryName);
            this.questCategoryDao.save(questCategory);
            return true;
        }

        return false;
    }

    public List<BoughtArtifact> getStudentsArtifacts(Integer studentId) throws DaoException {
        Student student = StudentDao.getDao().getStudent(studentId);
        List<BoughtArtifact> studentArtifacts = ArtifactOwnersDao.getDao().getArtifacts(student);
        return studentArtifacts;
    }

    public void markQuest(Map<String, String> formData) throws DaoException {
        Integer studentId = Integer.valueOf(formData.get("student_id"));
        Student student = StudentDao.getDao().getStudent(studentId);
        Integer questId = Integer.parseInt(formData.get("quest_id"));
        BigInteger value = new BigInteger(formData.get("value"));
        AvailableQuest availableQuest = availableQuestDao.getQuest(questId);

        AchievedQuest achievedQuest = new AchievedQuest(availableQuest, student);
        achievedQuest.setValue(value);

        achievedQuest.save();
        student.addCoins(achievedQuest.getValue());
        student.update();
    }

    public boolean markArtifactAsUsed(Map<String, String> formData) throws DaoException {
        BoughtArtifactDao boughtArtifactDao = BoughtArtifactDao.getDao();

        Integer artifactId = Integer.parseInt(formData.get("artifact_id"));
        BoughtArtifact chosenArtifact = boughtArtifactDao.getArtifact(artifactId);

        if (!chosenArtifact.isUsed()) {
            chosenArtifact.markAsUsed();
            chosenArtifact.update();
            return true;
        }
        return false;
    }

}