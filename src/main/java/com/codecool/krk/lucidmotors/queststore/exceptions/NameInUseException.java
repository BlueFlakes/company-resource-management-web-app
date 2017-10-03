package com.codecool.krk.lucidmotors.queststore.exceptions;

public class NameInUseException extends Exception {

    public NameInUseException() {
        super("Given name is already in use!");
    }
}
