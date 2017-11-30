package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.matchers.Compare;
import com.codecool.krk.lucidmotors.queststore.matchers.CustomMatchers;
import com.codecool.krk.lucidmotors.queststore.dao.*;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.*;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static com.codecool.krk.lucidmotors.queststore.matchers.Compare.isHigherOrEqual;
import static com.codecool.krk.lucidmotors.queststore.matchers.Compare.isLowerOrEqual;
import static java.lang.Integer.parseInt;

public class StudentController {

    private ArtifactOwnersDao artifactOwnersDao;
    private ShopArtifactDao shopArtifactDao;
    private StudentDao studentDao;
    private ContributionDao contributionDao;

    public StudentController() throws DaoException {

        this.shopArtifactDao = ShopArtifactDao.getDao();
        this.artifactOwnersDao = ArtifactOwnersDao.getDao();
        this.studentDao = StudentDao.getDao();
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

        BigInteger artifactPrice = shopArtifact.getPrice();
        BigInteger studentPossesedCoins = student.getPossesedCoins();

        if (isHigherOrEqual(studentPossesedCoins, artifactPrice)) {
            student.setPossesedCoins(studentPossesedCoins.subtract(artifactPrice));

            new BoughtArtifact(shopArtifact).save(new ArrayList<>(Collections.singletonList(student)));
            student.update();

            return true;
        }

        return false;
    }

    public List<Contribution> getAvailableContributions() throws DaoException {
        return this.contributionDao.getOpenContributions();
    }

    public String addNewContribution(Map<String, String> formData, User user) throws DaoException {
        String contributionName = formData.get("contribution-name");
        Integer choosenArtifactId = parseInt(formData.get("choosen-artifact-for-contribution"));

        if (this.contributionDao.getContributionByName(contributionName) == null) {
            ShopArtifact shopArtifact = this.shopArtifactDao.getArtifact(choosenArtifactId);
            Student student = this.studentDao.getStudent(user.getId());
            Contribution contribution = new Contribution(contributionName, student, shopArtifact);
            this.contributionDao.save(contribution);

            String message = String.format("%s open contribution for %s.", student.getName(), shopArtifact.getName());
            new ChatMessage("system", message, "System messages").save();

            return "Succesfully added new contribution!";
        } else {
            return "Sorry but this name is already used.";
        }
    }

    public List<Contribution> getThisUserContributions(User user) throws DaoException {
        List<Contribution> contributions = this.contributionDao.getOpenContributions();
        BiPredicate<Integer, Integer> areEqual = Objects::equals;

        return contributions.stream()
                            .filter(c -> areEqual.test(c.getCreator().getId(), user.getId()))
                            .collect(Collectors.toList());
    }

    public boolean closeUserContribution(Map<String, String> formData) throws DaoException {
        final String contributionNameKey = "choosen-contribution-to-close";

        if (formData.containsKey(contributionNameKey)) {
            Integer contributionId = parseInt(formData.get(contributionNameKey));
            Contribution contribution = this.contributionDao.getContribution(contributionId);

            contribution.setStatus("closed");
            contribution.update();

            Map<Student, BigInteger> contributorsShares = this.contributionDao.getContributorsShares(contributionId);
            giveMoneyBackToContributors(contributorsShares);
            return true;
        }

        return false;
    }

    private void giveMoneyBackToContributors(Map<Student, BigInteger> contributorsShares) throws DaoException {
        for (Map.Entry<Student, BigInteger> entry : contributorsShares.entrySet()) {
            Student student = entry.getKey();
            BigInteger spentCoinsAmount = entry.getValue();
            student.setPossesedCoins(student.getPossesedCoins().add(spentCoinsAmount));
            student.update();
        }
    }

    public boolean takePartInContribution(Map<String, String> formData, User user) throws DaoException {
        final String coinsKey = "spent-coins-amount";
        final String contributionKey = "choosen-contribution";

        if (formData.containsKey(coinsKey) && formData.containsKey(contributionKey)
                && CustomMatchers.isPositiveInteger(formData.get(coinsKey))) {

            Student student = this.studentDao.getStudent(user.getId());
            final BigInteger studentPossessedCoins = student.getPossesedCoins();
            final BigInteger givenCoins = new BigInteger(formData.get(coinsKey));

            if (isLowerOrEqual(givenCoins, studentPossessedCoins)) {

                final Integer contributionId = parseInt(formData.get(contributionKey));
                Contribution contribution = this.contributionDao.getContribution(contributionId);
                BigInteger neededCoinsDiff = getNeededCoinsDiff(contribution);

                final BigInteger takenCoins = isHigherOrEqual(neededCoinsDiff, givenCoins) ? givenCoins : neededCoinsDiff;
                student.setPossesedCoins(studentPossessedCoins.subtract(takenCoins));
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

    private BigInteger getNeededCoinsDiff(Contribution contribution) {
        BigInteger artifactPrice = contribution.getShopArtifact().getPrice();
        BigInteger collectedCoins = contribution.getGivenCoins();

        return artifactPrice.subtract(collectedCoins);
    }

    private void checkContributionStatus(Contribution contribution) throws DaoException {
        if (contribution.getShopArtifact().getPrice().equals(contribution.getGivenCoins())) {
            contribution.setStatus("closed");
            List<Student> contributors = contributionDao.getContributors(contribution.getId());

            BoughtArtifact boughtArtifact = new BoughtArtifact(contribution.getShopArtifact());
            BoughtArtifactDao.getDao().save(boughtArtifact, contributors);

            contribution.update();
        }
    }

    public Integer getUserLevel(User user) throws DaoException {
        ExperienceLevelsDao experienceLevelsDao = new ExperienceLevelsDao();
        return this.studentDao.getStudent(user.getId()).getLevel(experienceLevelsDao.getExperienceLevels());
    }
}