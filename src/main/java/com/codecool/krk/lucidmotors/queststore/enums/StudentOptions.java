package com.codecool.krk.lucidmotors.queststore.enums;

public enum StudentOptions {
    STORE ("templates/empty.twig"),
    SHOW_WALLET ("templates/empty.twig"),
    SHOW_AVAILABLE_ARTIFACTS ("templates/empty.twig"),
    BUY_ARTIFACT ("templates/empty.twig"),
    CONTRIBUTIONS ("templates/empty.twig"),
    CREATE_CONTRIBUTION ("templates/empty.twig"),
    CLOSE_CONTRIBUTION ("templates/empty.twig"),
    DEFAULT ("templates/empty.twig");

    private String path;

    private StudentOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}