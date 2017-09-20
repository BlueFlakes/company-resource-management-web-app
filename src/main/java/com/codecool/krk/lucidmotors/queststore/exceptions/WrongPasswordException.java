package com.codecool.krk.lucidmotors.queststore.exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Wrong login or password!");
    }
}
