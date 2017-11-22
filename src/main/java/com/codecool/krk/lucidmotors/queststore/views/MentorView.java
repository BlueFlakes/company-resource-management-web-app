package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.controllers.MentorController;
import com.codecool.krk.lucidmotors.queststore.controllers.MentorStoreController;
import com.codecool.krk.lucidmotors.queststore.dao.*;
import com.codecool.krk.lucidmotors.queststore.enums.MentorOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MentorView {
    private School school;
    private MentorController mentorController = new MentorController(this.school);
    private MentorStoreController mentorStoreController = new MentorStoreController(this.school);

    private ArtifactCategoryDao artifactCategoryDao = new ArtifactCategoryDao();
    private AvailableQuestDao availableQuestDao = new AvailableQuestDao();
    private QuestCategoryDao questCategoryDao = new QuestCategoryDao();

    User user;
    Map<String, String> formData;

    public MentorView(School school, User user, Map<String, String> formData) throws DaoException {
        this.school = school;
        this.user = user;
        this.formData = formData;
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
                markArtifact(model);
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

    private void markArtifact(JtwigModel model) throws DaoException {
        model.with("students", this.school.getAllStudents());
        model.with("phase", 1);

        if (formData.containsKey("student_id")) {
            Integer studentId = Integer.parseInt(formData.get("student_id"));
            List<BoughtArtifact> studentArtifacts = mentorController.getStudentsArtifacts(studentId);
            model.with("artifacts", studentArtifacts);
            model.with("is_disabled", true);
            model.with("phase", 2);
            model.with("selected_student_id", studentId);
        } else if (formData.containsKey("artifact_id")) {
            model.with("is_text_available", true);

            if (mentorController.markArtifactAsUsed(formData)) {
                model.with("text", "Artifact marked as used");
            } else {
                model.with("text", "Chosen artifact is already used!");
            }
        }
    }

    private void approveQuest(JtwigModel model) throws DaoException {
        model.with("students", this.school.getAllStudents());
        model.with("available_quests", availableQuestDao.getAllQuests());
        if(formData.containsKey("student_id") &&
                mentorController.markQuest(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Quest approved");
        }
    }

    private void updateArtifact(JtwigModel model) throws DaoException {
        model.with("artifacts", mentorStoreController.getAvailableArtifacts());
        model.with("artifact_categories", artifactCategoryDao.getAllArtifactCategories());
        if(formData.containsKey("artifact_id") &&
                mentorStoreController.updateArtifact(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Artifact successfully updated");
        }
    }

    private void addArtifact(JtwigModel model) throws DaoException {
        model.with("artifact_categories", artifactCategoryDao.getAllArtifactCategories());
        if(formData.containsKey("category_id") &&
                mentorStoreController.addArtifact(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Artifact successfully created");
        }
    }

    private void showArtifacts(JtwigModel model) throws DaoException {
        model.with("available_artifacts", mentorStoreController.getAvailableArtifacts());
    }

    private void addQuest(JtwigModel model) throws DaoException {
        model.with("quest_categories", questCategoryDao.getAllQuestCategories());
        if(formData.containsKey("category_id") &&
                mentorController.createNewAvailableQuest(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Quest successfully created");
        }
    }

    private void updateQuest(JtwigModel model) throws DaoException {
        model.with("quests", availableQuestDao.getAllQuests());
        model.with("quest_categories", questCategoryDao.getAllQuestCategories());
        if(formData.containsKey("quest_id") &&
                mentorController.changeQuestData(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Quest successfully updated");
        }
    }

    private void addQuestCategory(JtwigModel model) throws DaoException {
        if(formData.containsKey("name") && mentorController.addQuestCategory(this.formData)) {
            model.with("is_text_available", true);
            model.with("text", "Quest category successfully created");
        }
    }

    private void addArtifactCategory(JtwigModel model) throws DaoException {
        if(formData.containsKey("name") && mentorStoreController.addArtifactCategory(this.formData)) {
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

}
