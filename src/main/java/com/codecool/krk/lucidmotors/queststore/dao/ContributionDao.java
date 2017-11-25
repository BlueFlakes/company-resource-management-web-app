package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.Contribution;
import com.codecool.krk.lucidmotors.queststore.models.ShopArtifact;
import com.codecool.krk.lucidmotors.queststore.models.Student;
import com.codecool.krk.lucidmotors.queststore.models.User;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContributionDao {

    private static ContributionDao dao = null;
    private final Connection connection;
    private PreparedStatement stmt = null;

    public ContributionDao() throws DaoException {
        this.connection = DatabaseConnection.getConnection();
    }

    public static ContributionDao getDao() throws DaoException {

        if (dao == null) {

            synchronized (ContributionDao.class) {

                if (dao == null) {
                    dao = new ContributionDao();
                }
            }
        }

        return dao;
    }

    public void save(Contribution contribution) throws DaoException {

        String name = contribution.getName();
        Integer creatorId = contribution.getCreator().getId();
        Integer artifactId = contribution.getShopArtifact().getId();
        BigInteger givenCoins = contribution.getGivenCoins();
        String status = contribution.getStatus();

        String sqlQuery = "INSERT INTO contributions "
                + "(contribution_name, creator_id, artifact_id, given_coins, status)"
                + "VALUES (?, ?, ?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);
            stmt.setInt(2, creatorId);
            stmt.setInt(3, artifactId);
            stmt.setString(4, givenCoins.toString());
            stmt.setString(5, status);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }
    }

    public void update(Contribution contribution) throws DaoException {
        String contributionName = contribution.getName();
        Integer creatorId = contribution.getCreator().getId();
        Integer artifactId = contribution.getShopArtifact().getId();
        BigInteger givenCoins = contribution.getGivenCoins();
        Integer contributionId = contribution.getId();
        String status = contribution.getStatus();

        String sqlQuery = "UPDATE contributions "
                        + "SET contribution_name = ?, creator_id = ?, artifact_id = ?, given_coins = ?, status = ? "
                        + "WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, contributionName);
            stmt.setInt(2, creatorId);
            stmt.setInt(3, artifactId);
            stmt.setString(4, givenCoins.toString());
            stmt.setString(5, status);
            stmt.setInt(6, contributionId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }
    }

    public void saveContributor(User user, BigInteger coinsSpent, Contribution contribution) throws DaoException {
        Integer contributionId = contribution.getId();
        Integer studentId = user.getId();

        List<Student> contributors = getContributors(contributionId);

        if (isStudentAlreadyPartOfContribution(studentId, contributors)) {
            updateContributorCoins(contributionId, studentId, coinsSpent);
        } else {
            saveNewContributor(contributionId, studentId, coinsSpent);
        }
    }

    private boolean isStudentAlreadyPartOfContribution(Integer studentId, List<Student> contributors) {
        for (Student student : contributors) {
            if (studentId.equals(student.getId())) {
                return true;
            }
        }

        return false;
    }

    private void updateContributorCoins(Integer contributionId, Integer studentId, BigInteger coinsSpent) throws DaoException {
        String sqlQuery = "UPDATE contributors "
                        + "SET coins = ? "
                        + "WHERE student_id = ? AND contribution_id = ?;";

        try {
            BigInteger newCoinsAmount = getContributorSpentCoins(studentId, contributionId).add(coinsSpent);
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, newCoinsAmount.toString());
            stmt.setInt(2, studentId);
            stmt.setInt(3, contributionId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }
    }

    private BigInteger getContributorSpentCoins(Integer studentId, Integer contributionId) throws DaoException {

        BigInteger coins = new BigInteger("0");
        String sqlQuery = "SELECT coins FROM contributors WHERE contribution_id = ? AND student_id = ?";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, contributionId);
            stmt.setInt(2, studentId);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                coins = new BigInteger(result.getString("coins"));
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return coins;
    }

    private void saveNewContributor(Integer contributionId, Integer studentId, BigInteger coinsSpent) throws DaoException {
        String sqlQuery = "INSERT INTO contributors "
                        + "(contribution_id, student_id, coins)"
                        + "VALUES (?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, contributionId);
            stmt.setInt(2, studentId);
            stmt.setString(3, coinsSpent.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }
    }

    public List<Student> getContributors(Integer contributionId) throws DaoException {
        List<Student> contributors = new ArrayList<>();

        String sqlQuery = "SELECT student_id FROM contributors WHERE contribution_id = ?";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, contributionId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer studentId = result.getInt("student_id");

                Student student = StudentDao.getDao().getStudent(studentId);
                contributors.add(student);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return contributors;
    }

    public List<Contribution> getOpenContributions() throws DaoException {

        List<Contribution> openContributions = new ArrayList<>();
        String sqlQuery = "SELECT * FROM contributions";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer id = result.getInt("id");
                String contributionName = result.getString("contribution_name");
                Integer creatorId = result.getInt("creator_id");
                Integer artifactId = result.getInt("artifact_id");
                BigInteger givenCoins = new BigInteger(result.getString("given_coins"));
                String status = result.getString("status");

                ShopArtifact shopArtifact = ShopArtifactDao.getDao().getArtifact(artifactId);
                Student creator = StudentDao.getDao().getStudent(creatorId);

                Contribution contribution = new Contribution(contributionName, creator, shopArtifact,
                                                             givenCoins, id, status);

                if (contribution.getStatus().equals("open")) {
                    openContributions.add(contribution);
                }
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return openContributions;
    }

    public Contribution getContribution(Integer id) throws DaoException {
       Contribution contribution = null;
        String sqlQuery = "SELECT * FROM contributions WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                contribution = createContributionInstance(result);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return contribution;
    }

    private Contribution createContributionInstance(ResultSet result) throws DaoException, SQLException {
        String contributionName = result.getString("contribution_name");
        Integer creatorId = result.getInt("creator_id");
        Integer artifactId = result.getInt("artifact_id");
        BigInteger givenCoins = new BigInteger(result.getString("given_coins"));
        Integer id = result.getInt("id");
        String status = result.getString("status");

        ShopArtifact shopArtifact = ShopArtifactDao.getDao().getArtifact(artifactId);
        Student creator = StudentDao.getDao().getStudent(creatorId);

        return new Contribution(contributionName, creator, shopArtifact, givenCoins, id, status);
    }

    public HashMap<Student, BigInteger> getContributorsShares(Integer contributionId) throws DaoException {

        HashMap<Student, BigInteger> contributorsWithShares = new HashMap<>();

        String sqlQuery = "SELECT * FROM contributors WHERE contribution_id = ?";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, contributionId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Integer studentId = result.getInt("student_id");
                BigInteger spentCoins = new BigInteger(result.getString("coins"));
                Student student = StudentDao.getDao().getStudent(studentId);

                contributorsWithShares.put(student, spentCoins);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return contributorsWithShares;
    }
}
