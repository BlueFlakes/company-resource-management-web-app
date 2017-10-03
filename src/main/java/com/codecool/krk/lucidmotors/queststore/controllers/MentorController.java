package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.*;
import com.codecool.krk.lucidmotors.queststore.enums.MentorMenuOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.exceptions.LoginInUseException;
import com.codecool.krk.lucidmotors.queststore.models.*;

import java.util.ArrayList;

class MentorController extends AbstractUserController<Mentor> {

    private QuestCategoryDao questCategoryDao = new QuestCategoryDao();
    private AvailableQuestDao availableQuestDao = new AvailableQuestDao();

    MentorController() throws DaoException {
    }

    /**
     * Switches between methods, acording to userChoice param.
     *
     * @param userChoice
     * @throws DaoException
     */
    protected void handleUserRequest(String userChoice) throws DaoException {

        MentorMenuOptions chosenOption = getEnumValue(userChoice);

        switch (chosenOption) {
            case ADD_STUDENT:
                addStudent();
                break;

            case ADD_QUEST:
                addQuest();
                break;

            case LIST_STUDENTS_WALLETS:
                listWallets();
                break;

            case ADD_QUEST_CATEGORY:
                addQuestCategory();
                break;

            case UPDATE_QUEST:
                updateQuest();
                break;

            case MARK_BOUGHT_ARTIFACT_AS_USED:
                markBoughtArtifactsAsUsed();
                break;

            case START_MENTOR_STORE_CONTROLLER:
                runMentorStoreController();
                break;

            case EXIT:
                break;

            case DEFAULT:
                handleNoSuchCommand();
        }
    }

    private MentorMenuOptions getEnumValue(String userChoice) {
        MentorMenuOptions chosenOption;

        try {
            chosenOption = MentorMenuOptions.values()[Integer.parseInt(userChoice)];
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            chosenOption = MentorMenuOptions.DEFAULT;
        }

        return chosenOption;
    }

    protected void showMenu() {
        userInterface.printMentorMenu();
    }

    /**
     * Gather data about new student, create object and insert data into database.
     *
     * @throws DaoException
     */
    private void addStudent() throws DaoException {

        String[] questions = {"Name: ", "Login: ", "Password: ", "Email: "};
        String[] expectedTypes = {"String", "String", "String", "String"};

        ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
        String name = basicUserData.get(0);
        String login = basicUserData.get(1);
        String password = basicUserData.get(2);
        String email = basicUserData.get(3);
        SchoolClass choosenClass = chooseProperClass();

        try {
            this.school.isLoginAvailable(login);
            Student student = new Student(name, login, password, email, choosenClass);
            student.save();
        } catch (LoginInUseException e) {
            userInterface.println(e.getMessage());
        }

        this.userInterface.pause();
    }

    /**
     * Shows all classes and returns SchoolClass object chosen by user.
     *
     * @return
     * @throws DaoException
     */
    private SchoolClass chooseProperClass() throws DaoException {

        ArrayList<SchoolClass> allClasses = this.school.getAllClasses();
        int userChoice;

        do {
            showAvailableClasses(allClasses);
            userChoice = getUserChoice() - 1;

        } while (userChoice > (allClasses.size() - 1) || userChoice < 0);
        return allClasses.get(userChoice);
    }

    /**
     * Gets choice of class from user
     *
     * @return class number (generated automatically based on ArrayList size)
     */
    private Integer getUserChoice() {

        String[] questions = {"Please choose class: "};
        String[] expectedTypes = {"integer"};
        ArrayList<String> userInput = userInterface.inputs.getValidatedInputs(questions, expectedTypes);

        return Integer.parseInt(userInput.get(0));
    }

    /**
     * Prints all classes and index generated on base of ArrayList size
     *
     * @param allClasses
     */
    private void showAvailableClasses(ArrayList<SchoolClass> allClasses) {
        userInterface.newLine();

        for (int i = 0; i < allClasses.size(); i++) {
            String index = Integer.toString(i + 1);
            System.out.println(index + ". " + allClasses.get(i));
        }

        userInterface.newLine();
    }

    /**
     * Gather data about new Quest and insert it into database
     * @throws DaoException
     */
    private void addQuest() throws DaoException {

        ArrayList<String> questInfo = this.askForQuestDetails();

        try {
            this.createNewAvailableQuest(questInfo);
            this.userInterface.println("New quest added successfully!");
        } catch (NullPointerException e) {
            this.userInterface.println("Given quest category id does not exist!");
        }

        this.userInterface.pause();
    }

    /**
     * Asks user for details about new quests, which is going to be added.
     *
     * @throws DaoException
     */
    private ArrayList<String> askForQuestDetails () throws DaoException {
        String[] questions = {"Quest name: ", "Quest category: ", "Description: ", "Value: "};
        String[] types = {"string", "integer", "string", "integer"};

        this.displayAllQuestCategories();

        return this.userInterface.inputs.getValidatedInputs(questions, types);
    }

    /**
     * Iterates through all questCategories and calls toString method on each.
     *
     * @throws DaoException
     */
    private void displayAllQuestCategories() throws DaoException {
        ArrayList<QuestCategory> questCategories = this.questCategoryDao.getAllQuestCategories();

        this.userInterface.println("Available quest categories:");
        for (QuestCategory questCategory : questCategories) {
            this.userInterface.println(questCategory.toString());
        }
        this.userInterface.println("\n");
    }

    /**
     * Creates new AvailableQuest instance and adds it to proper table in database.
     *
     * @param questInfo
     * @throws DaoException
     */
    private void createNewAvailableQuest(ArrayList<String> questInfo) throws DaoException {
        String name = questInfo.get(0);
        QuestCategory questCategory = this.questCategoryDao.getQuestCategory(Integer.parseInt(questInfo.get(1)));
        String description = questInfo.get(2);
        Integer value = Integer.parseInt(questInfo.get(3));

        AvailableQuest questToAdd = new AvailableQuest(name, questCategory, description, value);
        questToAdd.save();
    }

    /**
     * Gather data about new Quest Category and inset it into database
     */
    private void addQuestCategory() throws DaoException {

        String[] questions = {"Name: "};
        String[] types = {"string"};
        this.displayAllQuestCategories();

        ArrayList<String> questCategoryInfo = this.userInterface.inputs.getValidatedInputs(questions, types);
        String questCategoryName = questCategoryInfo.get(0);

        QuestCategory questCategory = new QuestCategory(questCategoryName);
        this.questCategoryDao.save(questCategory);
        this.userInterface.println("New quest category added successfully!");

        this.userInterface.pause();
    }

    /**
     * Get choice which quest to update.
     * Gather new data about quest.
     * Update database.
     */
    private void updateQuest() throws DaoException {

        this.displayAvailableQuests();
        this.displayAllQuestCategories();
        String[] questions = {"Quest id: ", "New name: ", "New quest category id: ", "New description: ", "New value: "};
        String[] types = {"integer", "string", "integer", "string", "integer"};
        ArrayList<String> newQuestInfo = this.userInterface.inputs.getValidatedInputs(questions, types);

        try {
            this.changeQuestData(newQuestInfo);
            this.userInterface.println("Quest updated successfully!");
        } catch (NullPointerException e) {
            this.userInterface.println("Given quest category id or quest id is wrong!");
        }

        this.userInterface.pause();
    }

    /**
     * Iterates through all AvailableQuest objects and calls toString method on each.
     *
     * @throws DaoException
     */
    private void displayAvailableQuests() throws DaoException {
        ArrayList<AvailableQuest> availableQuests = availableQuestDao.getAllQuests();

        this.userInterface.println("Available quests list:");
        for (AvailableQuest availableQuest : availableQuests) {
            this.userInterface.println(availableQuest.toString());
        }
        this.userInterface.println("\n");
    }

    /**
     * Updates already existing quest in the database.
     *
     * @param newQuestInfo
     * @throws DaoException
     */
    private void changeQuestData(ArrayList<String> newQuestInfo) throws DaoException {
        Integer id = Integer.parseInt(newQuestInfo.get(0));
        String newName = newQuestInfo.get(1);
        QuestCategory newQuestCategory = this.questCategoryDao.getQuestCategory(Integer.parseInt(newQuestInfo.get(2)));
        String newDescription = newQuestInfo.get(3);
        Integer newValue = Integer.valueOf(newQuestInfo.get(4));

        AvailableQuest quest = this.availableQuestDao.getQuest(id);
        quest.setName(newName);
        quest.setQuestCategory(newQuestCategory);
        quest.setDescription(newDescription);
        quest.setValue(newValue);
        quest.update();
    }

    private void chooseAndMarkArtifact(Student student) throws DaoException {

        this.userInterface.println("Choose student's artifact from list: ");
        ArtifactOwnersDao artifactOwnersDao = new ArtifactOwnersDao();
        BoughtArtifactDao boughtArtifactDao = new BoughtArtifactDao();

        this.userInterface.printBoughtArtifacts(artifactOwnersDao.getArtifacts(student));
        String[] question = {"id: "};
        String[] type = {"integer"};
        Integer artifactId = Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
        BoughtArtifact chosenArtifact = boughtArtifactDao.getArtifact(artifactId);

        if (chosenArtifact != null && !chosenArtifact.isUsed()) {
            chosenArtifact.markAsUsed();
            chosenArtifact.update();
        } else if (chosenArtifact == null) {
            this.userInterface.println("There is no artifact of such id!");
        } else {
            this.userInterface.println("Chosen artifact is already used!");
        }

    }

    private void markBoughtArtifactsAsUsed() throws DaoException {

        this.userInterface.println("Choose student from list: ");
        StudentDao studentDao = new StudentDao(new ClassDao());

        this.userInterface.printStudents(studentDao.getAllStudents());
        String[] question = {"id: "};
        String[] type = {"integer"};
        Integer studentId = Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
        Student chosenStudent = studentDao.getStudent(studentId);

        if (chosenStudent != null) {
            chooseAndMarkArtifact(chosenStudent);
        } else {
            this.userInterface.println("There is no student of such id!");
        }

        this.userInterface.pause();

    }

    private void runMentorStoreController() throws DaoException {

        new MentorStoreController().startController(this.user, this.school);
    }

    private void showWallet(Student student) throws DaoException {
        userInterface.println("\nName: " + student.getName());
        userInterface.println("Balance: " + student.getPossesedCoins());
        userInterface.printBoughtArtifacts(student, new ArtifactOwnersDao().getArtifacts(student));

    }

    private void listWallets() throws DaoException {
        ArrayList<Student> students = new StudentDao(new ClassDao()).getAllStudents();

        userInterface.println("List of students wallets: ");
        for (Student student : students) {
            this.showWallet(student);
        }

        this.userInterface.pause();
    }
}
