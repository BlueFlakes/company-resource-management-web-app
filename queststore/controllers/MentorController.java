package queststore.controllers;

import queststore.models.User;
import queststore.models.School;


public class MentorController {
    private void handleUserRequest(String userChoice) {
        switch(userChoice) {
            case "1":
                addStudent();
                break;

            case "2":
                addQuest();
                break;

            case "3":
                addQuestCategory();
                break;

            case "4":
                updateQuest();
                break;

            case "5":
                markBoughtArtifactsAsUsed();
                break;

            case "6":
                runMentorStoreController();

            default:
                handleNoSuchCommand();

        }
    }

    private void addStudent() {
    }

    private void addQuest() {
    }

    private void addQuestCategory() {
    }

    private void updateQuest() {
    }

    private void markBoughtArtifactsAsUsed() {
    }

}
