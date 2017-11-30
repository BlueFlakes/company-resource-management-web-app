package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.controllers.MentorController;
import com.codecool.krk.lucidmotors.queststore.controllers.MentorStoreController;
import com.codecool.krk.lucidmotors.queststore.dao.*;
import com.codecool.krk.lucidmotors.queststore.enums.MentorOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import org.json.JSONObject;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MentorView {
    private School school;
    private MentorController mentorController = new MentorController(this.school);
    private MentorStoreController mentorStoreController = new MentorStoreController(this.school);

    private ArtifactCategoryDao artifactCategoryDao = ArtifactCategoryDao.getDao();
    private AvailableQuestDao availableQuestDao = AvailableQuestDao.getDao();
    private QuestCategoryDao questCategoryDao = QuestCategoryDao.getDao();

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
        model.with("json", "");

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

            case GET_ARTIFACT:
                getArtifactData(model);

            case GET_QUEST:
                getQuestData(model);

        }
    }

    private void getArtifactData(JtwigModel model) throws DaoException {
        if(this.formData.containsKey("artifact_id")) {
            try {
                Integer artifactId = Integer.valueOf(this.formData.get("artifact_id"));
                JSONObject artifact = ShopArtifactDao.getDao().getArtifact(artifactId).toJson();

                model.with("json", artifact.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void getQuestData(JtwigModel model) throws DaoException {
        if(this.formData.containsKey("quest_id")) {
            try {
                Integer questId = Integer.valueOf(this.formData.get("quest_id"));
                JSONObject quest = AvailableQuestDao.getDao().getQuest(questId).toJson();

                model.with("json", quest.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void markArtifact(JtwigModel model) throws DaoException {
        model.with("students", this.school.getAllStudents());
        model.with("phase", 1);

        if (formData.containsKey("student_id")) {
            Integer studentId = Integer.parseInt(formData.get("student_id"));
            List<BoughtArtifact> boughtArtifacts = mentorController.getStudentsArtifacts(studentId);
            List<StackedBoughtArtifact> stacked = StackedBoughtArtifact.getUserStackedBoughtArtifacts(boughtArtifacts);
            List<StackedBoughtArtifact> stackedAvailable = stacked.stream()
                                                                  .filter(o -> !o.isUsed())
                                                                  .collect(Collectors.toList());

            model.with("artifacts", stackedAvailable);
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
        if(formData.containsKey("student_id")) {
            mentorController.markQuest(this.formData);
            model.with("is_text_available", true);
            model.with("text", "Quest approved");
        }

        model.with("students", this.school.getAllStudents());
        model.with("available_quests", availableQuestDao.getAllQuests());
    }

    private void updateArtifact(JtwigModel model) throws DaoException {
        if(formData.containsKey("artifact_id")) {
            boolean wasSuccessfully = mentorStoreController.updateArtifact(this.formData);
            model.with("is_text_available", true);

            if (wasSuccessfully) {
                model.with("text", "Artifact successfully created");
            } else {
                model.with("text", "Given artifact name is already occupied.");
            }
        }

        model.with("artifacts", mentorStoreController.getAvailableArtifacts());
        model.with("artifact_categories", artifactCategoryDao.getAllArtifactCategories());
    }

    private void addArtifact(JtwigModel model) throws DaoException {
        if(formData.containsKey("category_id")) {
            boolean wasSuccessfully = mentorStoreController.addArtifact(this.formData);
            model.with("is_text_available", true);

            if (wasSuccessfully) {
                model.with("text", "Artifact successfully created");
            } else {
                model.with("text", "Given artifact name is already occupied.");
            }
        }

        model.with("artifact_categories", artifactCategoryDao.getAllArtifactCategories());
    }

    private void showArtifacts(JtwigModel model) throws DaoException {
        model.with("available_artifacts", mentorStoreController.getAvailableArtifacts());
    }

    private void addQuest(JtwigModel model) throws DaoException {
        if(formData.containsKey("category_id")) {
            mentorController.createNewAvailableQuest(this.formData);
            model.with("is_text_available", true);
            model.with("text", "Quest successfully created");
        }

        model.with("quest_categories", questCategoryDao.getAllQuestCategories());
    }

    private void updateQuest(JtwigModel model) throws DaoException {
        if(formData.containsKey("quest_id")) {
            mentorController.changeQuestData(this.formData);
            model.with("is_text_available", true);
            model.with("text", "Quest successfully updated");
        }

        model.with("quests", availableQuestDao.getAllQuests());
        model.with("quest_categories", questCategoryDao.getAllQuestCategories());
    }

    private void addQuestCategory(JtwigModel model) throws DaoException {
        if(formData.containsKey("name")) {
            boolean wasSuccessfully = mentorController.addQuestCategory(this.formData);
            model.with("is_text_available", true);

            if (wasSuccessfully) {
                model.with("text", "Quest category successfully created");
            } else {
                model.with("text", "Quest category name is already in use.");
            }
        }
    }

    private void addArtifactCategory(JtwigModel model) throws DaoException {
        if(formData.containsKey("name")) {
            boolean wasSuccessfully = mentorStoreController.addArtifactCategory(this.formData);
            model.with("is_text_available", true);

            if (wasSuccessfully) {
                model.with("text", "Artifact category successfully created");
            } else {
                model.with("text", "This name is already used.");
            }
        }
    }

    private void addStudent(JtwigModel model) throws DaoException {
        if(formData.containsKey("class_id")) {
            boolean wasSuccessfully = new MentorController(this.school).addStudent(this.formData);
            model.with("is_text_available", true);

            if (wasSuccessfully) {
                model.with("text", "Student successfully created");
            } else {
                model.with("text", "Used login is already occupied.");
            }
        }

        model.with("school_classes", this.school.getAllClasses());
    }

    private void listStudentsWallets(JtwigModel model) throws DaoException {
        List<Student> studentList = this.school.getAllStudents();
        model.with("students", studentList);
    }

}
