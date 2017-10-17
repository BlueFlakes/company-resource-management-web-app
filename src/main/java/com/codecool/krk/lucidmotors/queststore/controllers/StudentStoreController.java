package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.BoughtArtifactDao;
import com.codecool.krk.lucidmotors.queststore.dao.ContributionDao;
import com.codecool.krk.lucidmotors.queststore.dao.ShopArtifactDao;
import com.codecool.krk.lucidmotors.queststore.enums.StudentStoreMenuOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.StudentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentStoreController extends AbstractUserController<Student> {

    private final ShopArtifactController shopArtifactController = new ShopArtifactController();
    private final StudentView studentView = new StudentView();
    private final ContributionDao contributionDao = new ContributionDao();

    public StudentStoreController() throws DaoException {
    }

    protected void handleUserRequest(String userChoice) throws DaoException {

        StudentStoreMenuOptions chosenOption = getEnumValue(userChoice);

        switch (chosenOption) {

            case SHOW_AVAILABLE_ARTIFACTS:
                shopArtifactController.showAvailableArtifacts();
                break;

            case BUY_ARTIFACT:
                buyArtifact();
                break;

            case CONTRIBUTION:
                spendCoinsOnContribution();
                break;

            case CREATE_CONTRIBUTION:
                createNewContribution();
                break;

            case CLOSE_CONTRIBUTION:
                closeContribution();
                break;

            case EXIT:
                break;

            case DEFAULT:
                handleNoSuchCommand();
                break;
        }
    }

    private StudentStoreMenuOptions getEnumValue(String userChoice) {
        StudentStoreMenuOptions chosenOption;

        try {
            chosenOption = StudentStoreMenuOptions.values()[Integer.parseInt(userChoice)];
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            chosenOption = StudentStoreMenuOptions.DEFAULT;
        }

        return chosenOption;
    }

    protected void showMenu() {
        this.studentView.printStudentStoreMenu();
    }

    private void buyArtifact() throws DaoException {
        /* #TODO refactor */
        this.userInterface.print(new ShopArtifactDao().getAllArtifacts().iterator());

        try {
            ShopArtifact shopArtifact = this.getArtifactChoice();

            if (shopArtifact == null) {
                this.userInterface.println("No such artifact");

            } else if (!isStudentBalanceEnough(shopArtifact.getPrice())) {
                this.userInterface.println("Not enough money");

            } else {
                this.makePurchase(shopArtifact);
            }

        } catch (NumberFormatException e) {
            this.userInterface.println("Wrong artifact id.");
        }

        this.userInterface.pause();
    }

    private boolean isStudentBalanceEnough(Integer coinsToSpend) {
        Integer studentBalance = this.user.getPossesedCoins();

        return studentBalance >= coinsToSpend;
    }

    private ShopArtifact getArtifactChoice() throws NumberFormatException, DaoException {

        String input = this.userInterface.inputs.getInput("artifact id:");
        Integer artifact_id = Integer.parseInt(input);

        return new ShopArtifactDao().getArtifact(artifact_id);
    }

    private void makePurchase(ShopArtifact shopArtifact) throws DaoException {

        BoughtArtifact boughtArtifact = new BoughtArtifact(shopArtifact);

        this.user.substractCoins(boughtArtifact.getPrice());
        this.user.update();
        this.userInterface.println(String.format("Bought artifact: %s", boughtArtifact.getName()));

        ArrayList<Student> owners = new ArrayList<>();
        owners.add(this.user);

        new BoughtArtifactDao().save(boughtArtifact, owners);
    }

    private void createNewContribution() throws DaoException {

        ShopArtifactDao shopArtifactDao = new ShopArtifactDao();
        this.userInterface.print(shopArtifactDao.getAllArtifacts().iterator());

        String[] questions = {"Contribution name: "};
        String[] expectedTypes = {"String"};

        ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
        String name = basicUserData.get(0);
        ShopArtifact shopArtifact = getArtifactChoice();

        Contribution contribution = new Contribution(name, user, shopArtifact);
        contribution.save();
        this.userInterface.println("New contribution created successfully!");
        this.userInterface.pause();
    }

    private void spendCoinsOnContribution() throws DaoException {

        this.userInterface.print(contributionDao.getOpenContributions().iterator());

        String[] questions = {"Id of contribution you would like to contribute to: ", "Coins: "};
        String[] expectedTypes = {"Integer", "Integer"};

        ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
        Integer contributionId = Integer.parseInt(basicUserData.get(0));
        Integer coinsToSpend = Integer.parseInt(basicUserData.get(1));

        Contribution contribution = getOpenContribution(contributionId);

        if (!(contribution == null)) {
            takePartInContribution(contribution, coinsToSpend);
        } else {
            this.userInterface.println("Contribution with given id doesn't exist!");
        }

        this.userInterface.pause();
    }

    private Contribution getOpenContribution(Integer contributionId) throws DaoException {
        ArrayList<Contribution> openedContributions = contributionDao.getOpenContributions();
        Contribution contribution = null;

        for (Contribution openedContribution : openedContributions) {
            if (openedContribution.getId().equals(contributionId)) {
                contribution = openedContribution;
            }
        }

        return contribution;
    }

    private void takePartInContribution(Contribution contribution, Integer coinsToSpend) throws DaoException {
        Integer neededCoinsToFinish = contribution.getShopArtifact().getPrice() - contribution.getGivenCoins();

        if (!isStudentBalanceEnough(coinsToSpend)) {
            userInterface.println("You don't have enough coins!");
        } else if (coinsToSpend > neededCoinsToFinish) {
            userInterface.println("Max offer for this contribution at this moment is " + neededCoinsToFinish);
        } else {
            contribution.addCoins(coinsToSpend);
            user.substractCoins(coinsToSpend);
            contribution.update();
            user.update();
            contributionDao.saveContributor(user, coinsToSpend, contribution);
            this.userInterface.println("Coins added successfully!");
            checkContributionStatus(contribution);
        }
    }

    private void checkContributionStatus(Contribution contribution) throws DaoException {
        if (contribution.getShopArtifact().getPrice().equals(contribution.getGivenCoins())) {
            contribution.setStatus("closed");
            ArrayList<Student> contributors = contributionDao.getContributors(contribution.getId());

            BoughtArtifact boughtArtifact = new BoughtArtifact(contribution.getShopArtifact());
            new BoughtArtifactDao().save(boughtArtifact, contributors);

            contribution.update();
        }
    }

    private void closeContribution() throws DaoException {
        this.userInterface.print(contributionDao.getOpenContributions().iterator());

        String[] questions = {"Id of contribution you would like to close: "};
        String[] expectedTypes = {"Integer"};

        ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
        Integer contributionId = Integer.parseInt(basicUserData.get(0));

        Contribution contribution = contributionDao.getContribution(contributionId);

        if (contribution == null) {
            this.userInterface.println("Given contribution id is wrong!");
        } else if (user.getId().equals(contribution.getCreator().getId())){
            contribution.setStatus("closed");
            giveMoneyBackToStudents(contribution);
            contribution.update();
        } else {
            this.userInterface.println("You are not creator of this contribution!");
        }

        this.userInterface.pause();
    }

    private void giveMoneyBackToStudents(Contribution contribution) throws DaoException {
        HashMap<Student, Integer> contributorsWithShares = contributionDao.getContributorsShares(contribution.getId());

        for(Map.Entry<Student,Integer> entry : contributorsWithShares.entrySet()) {
            Student student = entry.getKey();
            Integer spentCoins = entry.getValue();

            if (user.getId().equals(student.getId())) {
                user.returnCoins(spentCoins);
                user.update();
            } else {
                student.returnCoins(spentCoins);
                student.update();
            }
        }
    }
}
