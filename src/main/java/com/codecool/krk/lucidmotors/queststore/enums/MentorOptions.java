package com.codecool.krk.lucidmotors.queststore.enums;

public enum MentorOptions {
    STUDENTS ("templates/snippets/mentor-students-inner-menu-snippet.twig"),
    QUESTS ("templates/snippets/mentor-quests-inner-menu-snippet.twig"),
    ARTIFACTS("templates/snippets/mentor-artifacts-inner-menu-snippet.twig"),
    ADD_STUDENT ("templates/snippets/mentor-add-student-snippet.twig"),
    LIST_STUDENTS_WALLETS ("templates/snippets/mentor-list-students-wallets-snippet.twig"),
    ADD_QUEST ("templates/snippets/mentor-add-quest-snippet.twig"),
    ADD_QUEST_CATEGORY ("templates/snippets/mentor-add-quest-category-snippet.twig"),
    UPDATE_QUEST ("templates/snippets/mentor-update-quest-snippet.twig"),
    MARK_BOUGHT_ARTIFACT_AS_USED ("templates/snippets/mentor-mark-bought-artifact-as-used-snippet.twig"),
    APPROVE_QUEST_ACHIEVEMENT ("templates/snippets/mentor-approve-quest-achievement-snippet.twig"),
    SHOW_AVAILABLE_ARTIFACTS ("templates/snippets/mentor-show-available-artifacts-snippet.twig"),
    ADD_ARTIFACT ("templates/snippets/mentor-add-artifact-snippet.twig"),
    UPDATE_ARTIFACT ("templates/snippets/mentor-update-artifact-snippet.twig"),
    ADD_ARTIFACT_CATEGORY ("templates/snippets/mentor-add-artifact-category-snippet.twig"),
    DEFAULT ("templates/snippets/home-snippet.twig");

    private String path;

    MentorOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
