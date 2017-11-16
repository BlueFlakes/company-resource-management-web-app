package com.codecool.krk.lucidmotors.queststore.enums;

public enum ManagerOptions {
    MENTORS ("templates/snippets/manager-mentor-actions-inner-menu-snippet.twig"),
    ADD_MENTOR ("templates/empty.twig"),
    EDIT_MENTOR ("templates/snippets/manager-edit-mentor-snippet.twig"),
    SHOW_MENTORS_CLASS ("templates/snippets/manager-show-mentor-class.twig"),
    CREATE_CLASS ("templates/manager-create-class-snippet.twig"),
    EXPERIENCE_LEVELS ("templates/manager-exp-levels-inner-menu-snippet.twig"),
    CREATE_NEW_LEVEL ("templates/manager-create-new-level.twig"),
    UPDATE_LEVEL ("templates/manager-update-level.twig"),
    SHOW_LEVELS ("templates/manager-show-all-levels.twig"),
    DEFAULT ("templates/snippets/home-snippet.twig");

    private String path;

    private ManagerOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
