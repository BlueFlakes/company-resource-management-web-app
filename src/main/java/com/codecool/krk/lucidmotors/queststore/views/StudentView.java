package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.controllers.StudentController;
import com.codecool.krk.lucidmotors.queststore.enums.StudentOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Activity;
import com.codecool.krk.lucidmotors.queststore.models.BoughtArtifact;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.models.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class StudentView {
    private School school;
    private StudentController studentController;
    private User user;

    public StudentView(School school, User user) throws DaoException {
        this.school = school;
        this.user = user;
        this.studentController = new StudentController();
    }

    public Activity getActivity(StudentOptions studentOption) {
        System.out.println(studentOption);
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("title", studentOption.toString());
        model.with("menu_path", "classpath:/templates/snippets/student-menu-snippet.twig");

        try {
            insertData(studentOption, model);

        } catch (DaoException e) {
            e.printStackTrace();
        }

        String contentPath = "classpath:/" + studentOption.getPath();
        model.with("content_path", contentPath);

        response = template.render(model);

        return new Activity(200, response);
    }

    private void insertData(StudentOptions studentOption, JtwigModel model) throws DaoException {
        switch (studentOption) {
            case SHOW_WALLET:
                List<BoughtArtifact> boughtArtifacts = studentController.getWallet(this.user);
                model.with("bought_artifacts", boughtArtifacts);
        }
    }
}
