package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.*;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.exceptions.LoginInUseException;
import com.codecool.krk.lucidmotors.queststore.enums.MentorMenuOptions;
import com.codecool.krk.lucidmotors.queststore.models.*;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;

import java.util.ArrayList;

class MentorController extends AbstractUserController<Mentor> {

      /**
     * Switches between methods, acording to userChoice param.
     *
     * @param userChoice
     * @throws DaoException
     */
    protected void handleUserRequest(String userChoice) throws DaoException {

        MentorMenuOptions chosenOption;
        try {
            chosenOption = MentorMenuOptions.values()[Integer.parseInt(userChoice)];
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            chosenOption = null;
        }

        if (chosenOption != null) {
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

                case APPROVE_QUEST_ACHIEVEMENT:
                    approveQuestAchievement();
                    break;

                case START_MENTOR_STORE_CONTROLLER:
                    runMentorStoreController();
                    break;

                case EXIT:
                    break;

                default:
                    handleNoSuchCommand();
            }
        }
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
     */
    private void addQuest() {

        String[] questions = {"Name: ", "Quest category: ", "Description: ", "Value: "};
        String[] types = {"string", "string", "string", "integer"};
        ArrayList<String> questInfo = this.userInterface.inputs.getValidatedInputs(questions, types);
        //this.addNewQuestRecord(questInfo);
        this.userInterface.pause();
    }

    private void addNewQuestRecord(ArrayList<String> questInfo) throws DaoException {
        QuestCategoryDao qcDao = new QuestCategoryDao();

        String name = questInfo.get(0);
        QuestCategory questCategory = qcDao.getQuestCategory(questInfo.get(1));
        String description = questInfo.get(2);
        Integer value = Integer.parseInt(questInfo.get(3));

        AvailableQuest questToAdd = new AvailableQuest(name, questCategory, description, value);
        // #TODO QUEST TO ADD NEEDS TO BE RECORDED IN DAO

    }

    /**
     * Gather data about new Quest Category and inset it into database
     */
    private void addQuestCategory() throws DaoException {

        String[] questions = {"Name: "};
        String[] types = {"string"};
        ArrayList<String> questCategoryInfo = this.userInterface.inputs.getValidatedInputs(questions, types);

        String questCategoryName = questCategoryInfo.get(0);
        QuestCategoryDao qcDao = new QuestCategoryDao();
        QuestCategory questCategory = new QuestCategory(questCategoryName);

        qcDao.save(questCategory);
        this.userInterface.println("New quest category added successfully!");
        this.userInterface.pause();
    }

    /**
     * Get choice which quest to update.
     * Gather new data about quest.
     * Update database.
     */
    private void updateQuest() {

        Integer id = this.getQuestId();
        String[] questions = {"new name: ", "new quest category: ", "new description: ", "new value: "};
        String[] types = {"string", "string", "string", "integer"};
        this.userInterface.inputs.getValidatedInputs(questions, types);
        // # TODO implement database connection
        this.userInterface.pause();
    }

    /**
     * Gets integer from user
     * @return
     */
    private Integer getQuestId() {

        String[] question = {"Provide quest id: "};
        String[] type = {"integer"};

        return Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
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

    private void handleNoSuchCommand() {

        userInterface.println("Wrong command!");
        this.userInterface.pause();
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

    private void chooseAndMarkQuest(Student student) throws DaoException {

        this.userInterface.println("Choose quest you want to confirm from list: ");
        AvailableQuestDao availableQuestDao = new AvailableQuestDao();

        this.userInterface.printAvailableQuests(availableQuestDao.getAllQuests());
        String[] question = {"id: "};
        String[] type = {"integer"};
        Integer questId = Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
        AvailableQuest availableQuest = availableQuestDao.getQuest(questId);

        if (availableQuest != null) {
            AchievedQuest achievedQuest = new AchievedQuest(availableQuest, student);
            achievedQuest.save();
            this.userInterface.println("Quest approved!");
            this.userInterface.println(String.format("Student got %s coins.", availableQuest.getValue()));
            student.addCoins(availableQuest.getValue());
            student.update();
        } else {
            this.userInterface.println("There is no quest of such id!");
        }

    }

    private void approveQuestAchievement() throws DaoException {

        this.userInterface.println("Choose student from list: ");
        StudentDao studentDao = new StudentDao(new ClassDao());

        this.userInterface.printStudents(studentDao.getAllStudents());
        String[] question = {"id: "};
        String[] type = {"integer"};
        Integer studentId = Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
        Student chosenStudent = studentDao.getStudent(studentId);

        if (chosenStudent != null) {
            chooseAndMarkQuest(chosenStudent);
        } else {
            this.userInterface.println("There is no student of such id!");
        }

        this.userInterface.pause();
    }
}
