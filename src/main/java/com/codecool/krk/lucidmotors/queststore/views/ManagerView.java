package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
import com.codecool.krk.lucidmotors.queststore.dao.MentorDao;
import com.codecool.krk.lucidmotors.queststore.matchers.CustomMatchers;
import com.codecool.krk.lucidmotors.queststore.controllers.ExperienceLevelsController;
import com.codecool.krk.lucidmotors.queststore.controllers.ManagerController;
import com.codecool.krk.lucidmotors.queststore.enums.ManagerOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import org.json.JSONObject;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ManagerView {
    private School school;
    private ExperienceLevelsController experienceLevelsController;
    private User user;
    private Map<String, String> formData;

    public ManagerView(School school, User user, Map<String, String> formData) throws DaoException {
        this.school = school;
        this.user = user;
        this.formData = formData;
        this.experienceLevelsController = new ExperienceLevelsController();
    }

    public Activity getActivity(ManagerOptions managerOption) throws IOException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("title", managerOption.toString());
        model.with("menu_path", "classpath:/templates/snippets/manager-menu-snippet.twig");
        model.with("is_text_available", false);
        model.with("role", "Manager");
        model.with("user", this.user);
        model.with("json", "");

        try {
            insertData(managerOption, model);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        String contentPath = "classpath:/" + managerOption.getPath();
        model.with("content_path", contentPath);

        response = template.render(model);

        return new Activity(200, response);

    }

    private void insertData(ManagerOptions managerOption, JtwigModel model) throws DaoException {
        switch (managerOption) {
            case SHOW_MENTORS_CLASS:
                showMentorClass(model);
                break;

            case EDIT_MENTOR:
                editMentor(model);
                break;

            case SHOW_LEVELS:
                model.with("experience_levels", this.experienceLevelsController.getLevels());
                break;

            case UPDATE_LEVEL:
                runUpdateLevel(model);
                break;

            case CREATE_NEW_LEVEL:
                runNewLevelCreation(model);
                break;

            case ADD_MENTOR:
                add_mentor(model);
                break;

            case CREATE_CLASS:
                create_class(model);
                break;

            case GET_MENTOR:
                getMentorData(model);
        }
    }

    private void getMentorData(JtwigModel model) throws DaoException {
        if(this.formData.containsKey("mentor_id")) {
            try {
                Integer mentorId = Integer.valueOf(this.formData.get("mentor_id"));
                JSONObject mentor = MentorDao.getDao().getMentor(mentorId).toJSON();

                model.with("json", mentor.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void create_class(JtwigModel model) throws DaoException {
        if(formData.containsKey("classname") ) {
            boolean wasSuccessfully = new ManagerController(this.school).createClass(this.formData);
            model.with("is_text_available", true);

            if (wasSuccessfully) {
                model.with("text", "Class successfully created");
            } else {
                model.with("text", "Sorry but given name is already occupied.");
            }
        }
    }

    private void add_mentor(JtwigModel model) throws DaoException {
        model.with("school_classes", this.school.getAllClasses());

        if(formData.containsKey("class_id")) {
            boolean wasSuccessfully = new ManagerController(this.school).addMentor(this.formData);
            model.with("is_text_available", true);

            if (wasSuccessfully) {
                model.with("text", "Mentor successfully created");
            } else {
                model.with("text", "Sorry, used login is already occupied.");
            }
        }
    }

    private void editMentor(JtwigModel model) throws DaoException {
        model.with("mentors", this.school.getAllMentors());
        model.with("school_classes", this.school.getAllClasses());
        if(this.formData.containsKey("mentor_id") &&
                new ManagerController(this.school).editMentor(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Mentor successfully updated");
        }
    }

    private void showMentorClass(JtwigModel model) throws DaoException {
        if (this.formData.containsKey("mentor_id")) {
            Integer mentorId = Integer.valueOf(this.formData.get("mentor_id"));
            List<Student> studentList = new ManagerController(this.school).getMentorClass(mentorId);
            model.with("mentor_data", MentorDao.getDao().getMentor(mentorId));
            model.with("students", studentList);
            model.with("selected_mentor_id", mentorId);
        }

        model.with("mentors", this.school.getAllMentors());
    }

    private void runUpdateLevel(JtwigModel model) throws DaoException {
        final String coinsKey = "experience-level-new-value";
        final String levelsKey = "choosen-class";

        model.with("experience_levels", this.experienceLevelsController.getLevels());

        if (this.formData.containsKey(coinsKey) && this.formData.containsKey(levelsKey)
                && areInputsParseableToInteger(coinsKey, levelsKey))  {

            boolean wasUpdated = experienceLevelsController.updateLevel(this.formData, coinsKey, levelsKey);
            model.with("is_text_available", true);

            String message;
            if (wasUpdated) {
                message = "Successfully updated level";
            } else {
                message = "Wrong amount of xp points! TOO LOW number";
            }

            model.with("text", message);
        }
    }

    private boolean areInputsParseableToInteger(String coinsKey, String levelsKey) {
        String coins = this.formData.get(coinsKey).trim();
        String level = this.formData.get(levelsKey).trim();

        return CustomMatchers.isPositiveInteger(coins) && CustomMatchers.isPositiveInteger(level);
    }

    private void runNewLevelCreation(JtwigModel model) throws DaoException {
        final String coinsKey = "experience-level";
        ExperienceLevelsDao experienceLevelsDao = ExperienceLevelsDao.getDao();
        Integer nextLevel = experienceLevelsDao.getHighestLevelID() + 1;
        Integer nextLevelNeededCoins = experienceLevelsDao.getHighestLevelCoins() + 1;
        model.with("next_level", nextLevel);
        model.with("next_level_min_value", nextLevelNeededCoins);

        if (this.formData.containsKey(coinsKey) && CustomMatchers.isPositiveInteger(this.formData.get(coinsKey))) {
            boolean wasUpdated = experienceLevelsController.createNewLevel(this.formData, coinsKey);
            model.with("is_text_available", true);

            String message;
            if (wasUpdated) {
                message = "Successfully updated level";
            } else {
                message = "Wrong amount of xp points! TOO LOW number";
            }

            model.with("text", message);
        }
    }


}
