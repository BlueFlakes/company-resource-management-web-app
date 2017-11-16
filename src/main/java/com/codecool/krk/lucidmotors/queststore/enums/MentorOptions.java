package com.codecool.krk.lucidmotors.queststore.enums;

public enum MentorOptions {
    ADD_STUDENT ("/mentor/empty.twig"),
    LIST_STUDENTS_WALLETS ("/mentor/empty.twig"),
    ADD_QUEST ("/mentor/empty.twig"),
    ADD_QUEST_CATEGORY ("/mentor/empty.twig"),
    UPDATE_QUEST ("/mentor/empty.twig"),
    MARK_BOUGHT_ARTIFACT_AS_USED ("/mentor/empty.twig"),
    APPROVE_QUEST_ACHIEVEMENT ("/mentor/empty.twig"),
    START_MENTOR_STORE_CONTROLLER ("/mentor/empty.twig"),
    SHOW_AVAILABLE_ARTIFACTS ("/mentor/empty.twig"),
    ADD_ARTIFACT ("/mentor/empty.twig"),
    UPDATE_ARTIFACT ("/mentor/empty.twig"),
    ADD_ARTIFACT_CATEGORY ("/mentor/empty.twig"),
    DEFAULT ("/mentor/empty.twig");

    private String path;

    private MentorOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
