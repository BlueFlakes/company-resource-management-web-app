package com.codecool.krk.lucidmotors.queststore.enums;

public enum ManagerOptions {
    MENTORS ("templates/snippets/manager-mentor-actions-inner-menu-snippet.twig"),
    ADD_MENTOR ("templates/empty.twig"),
    EDIT_MENTOR ("templates/empty.twig"),
    SHOW_MENTORS_CLASS ("templates/snippets/manager-mentor-class.twig"),
    CREATE_CLASS ("templates/empty.twig"),
    EXPERIENCE_LEVELS ("templates/empty.twig"),
    CREATE_NEW_LEVEL ("templates/empty.twig"),
    UPDATE_LEVEL ("templates/empty.twig"),
    SHOW_LEVELS ("templates/empty.twig"),
    DEFAULT ("templates/empty.twig");

    private String path;

    private ManagerOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
