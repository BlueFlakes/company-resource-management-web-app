package com.codecool.krk.lucidmotors.queststore.models;

import lombok.Getter;

@Getter
public class Stylesheet {
    private String name;
    private String path;

    public Stylesheet(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
