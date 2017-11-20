package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.controllers.StudentController;
import com.codecool.krk.lucidmotors.queststore.enums.StudentOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;
import java.util.Map;

public class StudentView {
    private StudentController studentController;
    private User user;
    private Map<String, String> formData;

    public StudentView(User user, Map<String, String> formData) throws DaoException {
        this.user = user;
        this.formData = formData;
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
                break;

            case SHOW_AVAILABLE_ARTIFACTS:
                List<ShopArtifact> shopArtifacts = studentController.getShopArtifacts();
                model.with("shop_artifacts", shopArtifacts);
                break;

            case BUY_ARTIFACT:

                if (formData.containsKey("choosen-artifact")) {
                    boolean wasSuccesfullyBought = studentController.buyArtifact(this.formData, this.user);
                    model.with("is_text_available", true);

                    String message;
                    if (wasSuccesfullyBought) {
                        message = "Artifact succesfuly bought!";
                    } else {
                        message = "Sorry but you dont have enough money!";
                    }

                    model.with("text", message);

                } else {
                    List<ShopArtifact> shopArtifacts1 = studentController.getShopArtifacts();
                    model.with("shop_artifact", shopArtifacts1);
                }
                break;

            case AVAILABLE_CONTRIBUTIONS:
                List<Contribution> contributions = studentController.getAvailableContributions();
                model.with("available_contributions", contributions);
                break;

            case CREATE_CONTRIBUTION:
                List<ShopArtifact> shopArtifacts1 = studentController.getShopArtifacts();
                model.with("shop_artifact", shopArtifacts1);
                boolean wasSuccesfullyAdded = studentController.addNewContribution(formData, user);

                if (wasSuccesfullyAdded) {
                    model.with("is_text_available", true);
                    model.with("text", "Succesfully added contribution!");
                }
                break;

            case CLOSE_CONTRIBUTION:
                List<Contribution> userContributions = studentController.getAllAuthorContributions(user);
                model.with("user_contributions", userContributions);
                boolean wasSuccesfulyClosed = studentController.closeUserContribution(formData, user);

                if (wasSuccesfulyClosed) {
                    model.with("is_text_available", true);
                    model.with("text", "Succesfully closed contribution!");

                }

                break;
        }
    }
}
