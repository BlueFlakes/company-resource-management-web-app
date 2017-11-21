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

    public Student getStudent(Integer id) throws DaoException {
        return this.studentDao.getStudent(id);
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
            student.update();

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
            contribution.update();

            return true;
        }

        return false;
    }

    public List<Contribution> getThisUserContributions(User user) throws DaoException {
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
            giveMoneyBackToContributors(contributorsShares);
            return true;
        }

        return false;
    }

    private void giveMoneyBackToContributors(Map<Student, Integer> contributorsShares) throws DaoException {
        for (Map.Entry<Student, Integer> entry : contributorsShares.entrySet()) {
            Student student = entry.getKey();
            Integer spentCoinsAmount = entry.getValue();
            student.setPossesedCoins(student.getPossesedCoins() + spentCoinsAmount);
            student.update();
        }
    }

    public boolean takePartInContribution(Map<String, String> formData, User user) throws DaoException {
        final String coinsKey = "spent-coins-amount";
        final String contributionKey = "choosen-contribution";

        if (formData.containsKey(coinsKey) && formData.containsKey(contributionKey)
                && CustomMatchers.isPositiveInteger(formData.get(coinsKey)) && formData.get(coinsKey).length() < 10 ) {

            Student student = this.studentDao.getStudent(user.getId());
            final Integer studentPossessedCoins = student.getPossesedCoins();
            final Integer givenCoins = parseInt(formData.get(coinsKey));

            if (givenCoins <= studentPossessedCoins) {

                final Integer contributionId = parseInt(formData.get(contributionKey));
                Contribution contribution = this.contributionDao.getContribution(contributionId);
                Integer neededCoinsDiff = contribution.getShopArtifact().getPrice() - contribution.getGivenCoins();

                final Integer takenCoins = neededCoinsDiff >= givenCoins ? givenCoins : neededCoinsDiff;
                student.setPossesedCoins(studentPossessedCoins - takenCoins);
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