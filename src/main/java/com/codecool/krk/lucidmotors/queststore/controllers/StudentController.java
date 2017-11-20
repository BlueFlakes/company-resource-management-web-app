//package com.codecool.krk.lucidmotors.queststore.controllers;
//
//import com.codecool.krk.lucidmotors.queststore.dao.AchievedQuestDao;
//import com.codecool.krk.lucidmotors.queststore.dao.ArtifactOwnersDao;
//import com.codecool.krk.lucidmotors.queststore.dao.ExperienceLevelsDao;
//import com.codecool.krk.lucidmotors.queststore.enums.StudentControllerMenuOptions;
//import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
//import com.codecool.krk.lucidmotors.queststore.models.Student;
//
//
//public class StudentController extends AbstractUserController<Student> {
//
//    //private final StudentView studentView = null;
//
//    protected void handleUserRequest(String userChoice) throws DaoException {
//
//        StudentControllerMenuOptions chosenOption = getEnumValue(userChoice);
//
//        switch (chosenOption) {
//
//            case START_STORE_CONTROLLER:
//                startStoreController();
//                break;
//
//            case SHOW_LEVEL:
//                showLevel();
//                break;
//
//            case SHOW_WALLET:
//                showWallet();
//                break;
//
//            case EXIT:
//                break;
//
//            case DEFAULT:
//                handleNoSuchCommand();
//                break;
//        }
//    }
//
//    private StudentControllerMenuOptions getEnumValue(String userChoice) {
//        StudentControllerMenuOptions chosenOption;
//
//        try {
//            chosenOption = StudentControllerMenuOptions.values()[Integer.parseInt(userChoice)];
//        } catch (IndexOutOfBoundsException | NumberFormatException e) {
//            chosenOption = StudentControllerMenuOptions.DEFAULT;
//        }
//
//        return chosenOption;
//    }
//
//    protected void showMenu() {
//        this.studentView.printStudentMenu();
//    }

//
//    private void showLevel() throws DaoException {
//
//        Integer level = new ExperienceLevelsDao().getExperienceLevels().computeStudentLevel(this.user.getEarnedCoins());
//        this.userInterface.println(String.format("Your level: %d", level));
//        this.userInterface.pause();
//    }
//
//    private void startStoreController() throws DaoException {
//
//        new StudentStoreController().startController(this.user, this.school);
//    }
//}


package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.Matchers.CustomMatchers;
import com.codecool.krk.lucidmotors.queststore.dao.*;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class StudentController {

    private ArtifactOwnersDao artifactOwnersDao;
    private ShopArtifactDao shopArtifactDao;
    private StudentDao studentDao;
    private ContributionDao contributionDao;

    public StudentController() throws DaoException {
        this.shopArtifactDao = new ShopArtifactDao();
        this.artifactOwnersDao = new ArtifactOwnersDao();
        this.studentDao = new StudentDao();
        this.contributionDao = new ContributionDao();
    }

    public List<BoughtArtifact> getWallet(User student) throws DaoException {
        return this.artifactOwnersDao.getArtifacts(student);
    }

    public List<ShopArtifact> getShopArtifacts() throws DaoException {
        return this.shopArtifactDao.getAllArtifacts();
    }

    public boolean buyArtifact(Map<String, String> formData, User user) throws DaoException {
        final String key = "choosen-artifact";
        Integer artifactId = parseInt(formData.get(key));

        Student student = this.studentDao.getStudent(user.getId());
        ShopArtifact shopArtifact = shopArtifactDao.getArtifact(artifactId);

        Integer artifactPrice = shopArtifact.getPrice();
        Integer studentPossesedCoins = student.getPossesedCoins();

        if (studentPossesedCoins >= artifactPrice) {
            student.setPossesedCoins(studentPossesedCoins - artifactPrice);

            new BoughtArtifact(shopArtifact).save(new ArrayList<>(Collections.singletonList(student)));
            this.studentDao.update(student);

            return true;
        }

        return false;
    }

    public List<Contribution> getAvailableContributions() throws DaoException {
        return this.contributionDao.getOpenContributions();
    }

    public boolean addNewContribution(Map<String, String> formData, User user) throws DaoException {
        final String contributionNameKey = "contribution-name";
        final String choosenArtifactKey = "choosen-artifact-for-contribution";

        if (formData.containsKey(contributionNameKey) && formData.containsKey(choosenArtifactKey)) {
            String contributionName = formData.get(contributionNameKey);
            Integer choosenArtifactId = parseInt(formData.get(choosenArtifactKey));

            ShopArtifact shopArtifact = this.shopArtifactDao.getArtifact(choosenArtifactId);
            Student student = this.studentDao.getStudent(user.getId());
            Contribution contribution = new Contribution(contributionName, student, shopArtifact);
            this.contributionDao.save(contribution);

            return true;
        }

        return false;
    }

    public List<Contribution> getAllAuthorContributions(User user) throws DaoException {
        List<Contribution> contributions = this.contributionDao.getOpenContributions();
        BiPredicate<Integer, Integer> areEqual = Objects::equals;

        return contributions.stream()
                            .filter(c -> areEqual.test(c.getCreator().getId(), user.getId()))
                            .collect(Collectors.toList());
    }

    public boolean closeUserContribution(Map<String, String> formData, User user) throws DaoException {
        final String contributionNameKey = "choosen-contribution-to-close";

        if (formData.containsKey(contributionNameKey)) {
            Integer contributionId = parseInt(formData.get(contributionNameKey));
            Contribution contribution = this.contributionDao.getContribution(contributionId);
            contribution.setStatus("closed");
            contribution.update();

            Map<Student, Integer> contributorsShares = this.contributionDao.getContributorsShares(contributionId);

            for (Map.Entry<Student, Integer> entry : contributorsShares.entrySet()) {
                Student student = entry.getKey();
                Integer spentCoinsAmount = entry.getValue();
                student.setPossesedCoins(student.getPossesedCoins() + spentCoinsAmount);
                student.update();
            }

            return true;
        }

        return false;
    }

    public boolean takePartInContribution(Map<String, String> formData, User user) throws DaoException {
        final String coinsKey = "spent-coins-amount";
        final String contributionKey = "choosen-contribution";

        if (formData.containsKey(coinsKey) && formData.containsKey(contributionKey)
                && CustomMatchers.isPositiveInteger(formData.get(coinsKey))) {

            Student student = this.studentDao.getStudent(user.getId());
            final Integer studentPossessedCoins = student.getPossesedCoins();
            final Integer givenCoins = parseInt(formData.get(coinsKey));

            if (givenCoins <= studentPossessedCoins) {

                final Integer contributionId = parseInt(formData.get(contributionKey));
                Contribution contribution = this.contributionDao.getContribution(contributionId);
                Integer neededCoinsDiff = contribution.getShopArtifact().getPrice() - contribution.getGivenCoins();
                final Integer takenCoins = neededCoinsDiff >= givenCoins ? givenCoins : givenCoins - neededCoinsDiff;

                student.setPossesedCoins(student.getPossesedCoins() - takenCoins);
                contribution.addCoins(takenCoins);

                student.update();
                contribution.update();

                this.contributionDao.saveContributor(user, givenCoins, contribution);
                checkContributionStatus(contribution);
                return true;
            }
        }

        return false;
    }

    private void checkContributionStatus(Contribution contribution) throws DaoException {
        if (contribution.getShopArtifact().getPrice().equals(contribution.getGivenCoins())) {
            contribution.setStatus("closed");
            List<Student> contributors = contributionDao.getContributors(contribution.getId());

            BoughtArtifact boughtArtifact = new BoughtArtifact(contribution.getShopArtifact());
            new BoughtArtifactDao().save(boughtArtifact, contributors);

            contribution.update();
        }
    }

    public Integer getUserLevel(User user) throws DaoException {
        ExperienceLevelsDao experienceLevelsDao = new ExperienceLevelsDao();
        return this.studentDao.getStudent(user.getId()).getLevel(experienceLevelsDao.getExperienceLevels());
    }
}