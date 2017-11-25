package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.*;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;

import java.util.List;
import java.util.Map;

public class MentorController {
    private QuestCategoryDao questCategoryDao = QuestCategoryDao.getDao();
    private AvailableQuestDao availableQuestDao = AvailableQuestDao.getDao();
    School school;

    public MentorController(School school) throws DaoException {
        this.school = school;
    }

    public boolean changeQuestData(Map<String, String> formData) throws DaoException {
        Boolean isAdded = true;

        Integer id = Integer.parseInt(formData.get("quest_id"));
        String name = formData.get("quest_name");
        QuestCategory questCategory = this.questCategoryDao.getQuestCategory(Integer.parseInt(formData.get("category_id")));
        String description = formData.get("description");

        try {
            Integer value = Integer.parseInt(formData.get("value"));

            AvailableQuest quest = this.availableQuestDao.getQuest(id);
            quest.setName(name);
            quest.setQuestCategory(questCategory);
            quest.setDescription(description);
            quest.setValue(value);
            quest.update();
        } catch (NumberFormatException e) {
            isAdded = false;
        }
        return isAdded;
    }

    public boolean createNewAvailableQuest(Map<String, String> formData) throws DaoException {
        Boolean isAdded = true;

        String name = formData.get("quest_name");
        QuestCategory questCategory = this.questCategoryDao.getQuestCategory(Integer.parseInt(formData.get("category_id")));
        String description = formData.get("description");

        try {
            Integer value = Integer.parseInt(formData.get("quest_value"));

            AvailableQuest questToAdd = new AvailableQuest(name, questCategory, description, value);
            questToAdd.save();
            String message = String.format("Take the new quest: %s. You can get %d cc!", questToAdd.getName(), questToAdd.getValue());
            new ChatMessage("system", message).save();
        } catch (NumberFormatException e) {
            isAdded = false;
        }
        return isAdded;
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

        QuestCategory questCategory = new QuestCategory(questCategoryName);
        this.questCategoryDao.save(questCategory);

        return true;
    }

    public List<BoughtArtifact> getStudentsArtifacts(Integer studentId) throws DaoException {
        Student student = StudentDao.getDao().getStudent(studentId);
        List<BoughtArtifact> studentArtifacts = ArtifactOwnersDao.getDao().getArtifacts(student);
        return studentArtifacts;
    }

    public boolean markQuest(Map<String, String> formData) throws DaoException {
        Integer studentId = Integer.valueOf(formData.get("student_id"));
        Student student = StudentDao.getDao().getStudent(studentId);
        Integer questId = Integer.parseInt(formData.get("quest_id"));
        AvailableQuest availableQuest = availableQuestDao.getQuest(questId);

        AchievedQuest achievedQuest = new AchievedQuest(availableQuest, student);
        achievedQuest.save();
        student.addCoins(availableQuest.getValue());
        student.update();

        return true;
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