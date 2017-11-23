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

    public Activity getActivity(StudentOptions studentOption) throws DaoException{

        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/main.twig");
        JtwigModel model = JtwigModel.newModel();

        try {
            insertData(studentOption, model);

        } catch (DaoException e) {
            e.printStackTrace();
        }

        Student student = this.studentController.getStudent(user.getId());

        model.with("user", student);
        model.with("role", student.getClass().getSimpleName());
        model.with("level", this.studentController.getUserLevel(student));
        model.with("title", studentOption.toString());
        model.with("menu_path", "classpath:/templates/snippets/student-menu-snippet.twig");

        String contentPath = "classpath:/" + studentOption.getPath();
        model.with("content_path", contentPath);

        response = template.render(model);

        return new Activity(200, response);
    }

    private void insertData(StudentOptions studentOption, JtwigModel model) throws DaoException {
        switch (studentOption) {
            case SHOW_WALLET:
                showWallet(model);
                break;

            case SHOW_AVAILABLE_ARTIFACTS:
                showAvailableArtifacts(model);
                break;

            case BUY_ARTIFACT:
                handleArtifactShopping(model);
                break;

            case AVAILABLE_CONTRIBUTIONS:
                handleJoinContribution(model);
                break;

            case CREATE_CONTRIBUTION:
                handleCreateContribution(model);
                break;

            case CLOSE_CONTRIBUTION:
                handleCloseContribution(model);
                break;
        }
    }

    private void showWallet(JtwigModel model) throws DaoException {
        List<BoughtArtifact> boughtArtifacts = studentController.getWallet(this.user);
        model.with("bought_artifacts", boughtArtifacts);
    }

    private void showAvailableArtifacts(JtwigModel model) throws DaoException {
        List<ShopArtifact> shopArtifacts = studentController.getShopArtifacts();
        model.with("shop_artifacts", shopArtifacts);
    }

    private void handleArtifactShopping(JtwigModel model) throws DaoException {
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
    }

    private void handleJoinContribution(JtwigModel model) throws DaoException {
        List<Contribution> contributions = studentController.getAvailableContributions();
        model.with("available_contributions", contributions);
        boolean succesfullyPaid = studentController.takePartInContribution(formData, user);

        String message;
        if (succesfullyPaid) {
            model.with("is_text_available", true);
            message = "Succesfuly Paid! :)";

        } else {
            message = "Sorry but you dont have enough money!";
        }
        model.with("max_value", Integer.MAX_VALUE);
        model.with("text", message);
    }

    private void handleCreateContribution(JtwigModel model) throws DaoException {
        List<ShopArtifact> shopArtifacts1 = studentController.getShopArtifacts();
        model.with("shop_artifact", shopArtifacts1);
        boolean wasSuccesfullyAdded = studentController.addNewContribution(formData, user);

        if (wasSuccesfullyAdded) {
            model.with("is_text_available", true);
            model.with("text", "Succesfully added contribution!");
        }
    }

    private void handleCloseContribution(JtwigModel model) throws DaoException {
        List<Contribution> userContributions = studentController.getThisUserContributions(user);
        model.with("user_contributions", userContributions);
        boolean wasSuccesfulyClosed = studentController.closeUserContribution(formData, user);

        if (wasSuccesfulyClosed) {
            model.with("is_text_available", true);
            model.with("text", "Succesfully closed contribution!");

        }
    }
}
