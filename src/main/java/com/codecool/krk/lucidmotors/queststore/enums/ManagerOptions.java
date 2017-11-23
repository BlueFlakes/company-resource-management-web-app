package com.codecool.krk.lucidmotors.queststore.enums;

public enum ManagerOptions {
    MENTORS ("templates/snippets/manager-mentor-actions-inner-menu-snippet.twig"),
    ADD_MENTOR ("templates/snippets/manager-add-mentor-snippet.twig"),
    EDIT_MENTOR ("templates/snippets/manager-edit-mentor-snippet.twig"),
    SHOW_MENTORS_CLASS ("templates/snippets/manager-show-mentor-class.twig"),
    CREATE_CLASS ("templates/snippets/manager-create-class-snippet.twig"),
    EXPERIENCE_LEVELS ("templates/snippets/manager-exp-levels-inner-menu-snippet.twig"),
    CREATE_NEW_LEVEL ("templates/snippets/manager-create-new-level.twig"),
    UPDATE_LEVEL ("templates/snippets/manager-update-level.twig"),
    SHOW_LEVELS ("templates/snippets/manager-show-all-levels.twig"),
    DEFAULT ("templates/snippets/home-snippet.twig");

    private String path;

    ManagerOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
