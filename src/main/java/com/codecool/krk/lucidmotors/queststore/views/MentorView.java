package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.Matchers.CustomMatchers;
import com.codecool.krk.lucidmotors.queststore.controllers.ExperienceLevelsController;
import com.codecool.krk.lucidmotors.queststore.controllers.MentorController;
import com.codecool.krk.lucidmotors.queststore.controllers.MentorStoreController;
import com.codecool.krk.lucidmotors.queststore.dao.*;
import com.codecool.krk.lucidmotors.queststore.enums.MentorOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Activity;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.Mentor;
import com.codecool.krk.lucidmotors.queststore.models.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MentorView {
    private School school;
    private ExperienceLevelsController experienceLevelsController;
    User user;
    Map<String, String> formData;

    public MentorView(School school, User user, Map<String, String> formData) throws DaoException {
        this.school = school;
        this.user = user;
        this.formData = formData;
        this.experienceLevelsController = new ExperienceLevelsController();
    }

    public Activity getActivity(MentorOptions mentorOption) throws IOException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("title", mentorOption.toString());
        model.with("menu_path", "classpath:/templates/snippets/mentor-menu-snippet.twig");
        model.with("is_text_available", false);
        model.with("role", "Mentor");
        model.with("user", this.user);

        try {
            insertData(mentorOption, model);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        String contentPath = "classpath:/" + mentorOption.getPath();
        model.with("content_path", contentPath);

        response = template.render(model);

        return new Activity(200, response);
    }

    private void insertData(MentorOptions mentorOption, JtwigModel model) throws DaoException {
        switch (mentorOption) {
//            case SHOW_MENTORS_CLASS:
//                showMentorClass(model);
//                break;

//            case EDIT_MENTOR:
//                editMentor(model);
//                break;
//
//            case SHOW_LEVELS:
//                model.with("experience_levels", this.experienceLevelsController.getLevels());
//                break;
//
//            case UPDATE_LEVEL:
//                runUpdateLevel(model);
//                break;
//
//            case CREATE_NEW_LEVEL:
//                runNewLevelCreation(model);
//                break;
//
            case ADD_STUDENT:
                addStudent(model);
                break;

            case LIST_STUDENTS_WALLETS:
                listStudentsWallets(model);
                break;

            case ADD_QUEST:
                addQuest(model);
                break;

            case ADD_QUEST_CATEGORY:
                addQuestCategory(model);
                break;

            case UPDATE_QUEST:
                updateQuest(model);
                break;

            case MARK_BOUGHT_ARTIFACT_AS_USED:

                break;

            case APPROVE_QUEST_ACHIEVEMENT:
                approveQuest(model);
                break;

            case SHOW_AVAILABLE_ARTIFACTS:
                showArtifacts(model);
                break;

            case ADD_ARTIFACT:
                addArtifact(model);
                break;

            case UPDATE_ARTIFACT:
                updateArtifact(model);
                break;

            case ADD_ARTIFACT_CATEGORY:
                addArtifactCategory(model);
                break;

        }
    }

    private void approveQuest(JtwigModel model) throws DaoException {
        model.with("students", new StudentDao(new ClassDao()).getAllStudents());
        model.with("available_quests", new AvailableQuestDao().getAllQuests());
        if(formData.containsKey("student_id") &&
                new MentorController(this.school).markQuest(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Quest approved");
        }
    }

    private void updateArtifact(JtwigModel model) throws DaoException {
        model.with("artifacts", new ShopArtifactDao().getAllArtifacts());
        model.with("artifact_categories", new ArtifactCategoryDao().getAllArtifactCategories());
        if(formData.containsKey("artifact_id") &&
                new MentorStoreController(this.school).updateArtifact(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Artifact successfully updated");
        }
    }

    private void addArtifact(JtwigModel model) throws DaoException {
        model.with("artifact_categories", new ArtifactCategoryDao().getAllArtifactCategories());
        if(formData.containsKey("category_id") &&
                new MentorStoreController(this.school).addArtifact(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Artifact successfully created");
        }
    }

    private void showArtifacts(JtwigModel model) throws DaoException {
        model.with("available_artifacts", new MentorStoreController(this.school).getAvailableArtifacts());
    }

    private void addQuest(JtwigModel model) throws DaoException {
        model.with("quest_categories", new QuestCategoryDao().getAllQuestCategories());
        if(formData.containsKey("category_id") &&
                new MentorController(this.school).createNewAvailableQuest(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Quest successfully created");
        }
    }

    private void updateQuest(JtwigModel model) throws DaoException {
        model.with("quests", new AvailableQuestDao().getAllQuests());
        model.with("quest_categories", new QuestCategoryDao().getAllQuestCategories());
        if(formData.containsKey("quest_id") &&
                new MentorController(this.school).changeQuestData(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Quest successfully updated");
        }
    }

    private void addQuestCategory(JtwigModel model) throws DaoException {
        if(formData.containsKey("name") && new MentorController(this.school).addQuestCategory(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Quest category successfully created");
        }
    }

    private void addArtifactCategory(JtwigModel model) throws DaoException {
        if(formData.containsKey("name") && new MentorStoreController(this.school).addArtifactCategory(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Artifact category successfully created");
        }
    }

    private void addStudent(JtwigModel model) throws DaoException {
        model.with("school_classes", this.school.getAllClasses());
        if(formData.containsKey("class_id") &&
                new MentorController(this.school).addStudent(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Student successfully created");
        }
    }

    private void listStudentsWallets(JtwigModel model) throws DaoException {
        List<Student> studentList = this.school.getAllStudents();
        model.with("students", studentList);
    }

//
//    private void editMentor(JtwigModel model) throws DaoException {
//        model.with("mentors", this.school.getAllMentors());
//        if(this.formData.containsKey("mentor_id") &&
//                new ManagerController(this.school).editMentor(this.formData)) {
//            model.with("is_text_available", true);
//            model.with("text", "Mentor successfully updated");
//        }
//    }
//
//    private void showMentorClass(JtwigModel model) throws DaoException {
//        if (this.formData.containsKey("mentor_id")) {
//            Integer mentorId = Integer.valueOf(this.formData.get("mentor_id"));
//            List<Student> studentList = new ManagerController(this.school).getMentorClass(mentorId);
//            model.with("students", studentList);
//            model.with("selected_mentor_id", mentorId);
//        }
//
//        model.with("mentors", this.school.getAllMentors());
//    }
//
//    private void runUpdateLevel(JtwigModel model) throws DaoException {
//        final String coinsKey = "experience-level-new-value";
//        final String levelsKey = "choosen-class";
//
//        model.with("experience_levels", this.experienceLevelsController.getLevels());
//
//        if (this.formData.containsKey(coinsKey) && this.formData.containsKey(levelsKey)
//                && areInputsParseableToInteger(coinsKey, levelsKey))  {
//
//            boolean wasUpdated = experienceLevelsController.updateLevel(this.formData, coinsKey, levelsKey);
//            model.with("is_text_available", true);
//
//            String message;
//            if (wasUpdated) {
//                message = "Successfully updated level";
//            } else {
//                message = "Wrong amount of xp points! TOO LOW number";
//            }
//
//            model.with("text", message);
//        }
//    }
//
//    private boolean areInputsParseableToInteger(String coinsKey, String levelsKey) {
//        String coins = this.formData.get(coinsKey).trim();
//        String level = this.formData.get(levelsKey).trim();
//
//        return CustomMatchers.isPositiveInteger(coins) && CustomMatchers.isPositiveInteger(level);
//    }
//
//    private void runNewLevelCreation(JtwigModel model) throws DaoException {
//        final String coinsKey = "experience-level";
//
//        if (this.formData.containsKey(coinsKey) && CustomMatchers.isPositiveInteger(this.formData.get(coinsKey))) {
//            boolean wasUpdated = experienceLevelsController.createNewLevel(this.formData, coinsKey);
//            model.with("is_text_available", true);
//
//            String message;
//            if (wasUpdated) {
//                message = "Successfully updated level";
//            } else {
//                message = "Wrong amount of xp points! TOO LOW number";
//            }
//
//            model.with("text", message);
//        }
//    }


}
