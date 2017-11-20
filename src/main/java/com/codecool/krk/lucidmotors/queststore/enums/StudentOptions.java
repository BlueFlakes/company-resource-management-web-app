package com.codecool.krk.lucidmotors.queststore.enums;

public enum StudentOptions {
    STORE ("templates/snippets/student-store-snippet.twig"),
    SHOW_WALLET ("templates/snippets/student-wallet-snippet.twig"),
    SHOW_AVAILABLE_ARTIFACTS ("templates/snippets/student-store-show-available-artifact.twig"),
    BUY_ARTIFACT ("templates/snippets/student-store-buy-artifact.twig"),
    AVAILABLE_CONTRIBUTIONS ("templates/empty.twig"),
    CREATE_CONTRIBUTION ("templates/empty.twig"),
    CLOSE_CONTRIBUTION ("templates/empty.twig"),
    DEFAULT ("templates/snippets/home-snippet.twig");

    private String path;

    private StudentOptions(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}