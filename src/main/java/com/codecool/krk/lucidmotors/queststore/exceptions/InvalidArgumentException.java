package com.codecool.krk.lucidmotors.queststore.exceptions;

public class InvalidArgumentException extends Exception {

    public InvalidArgumentException( ) {

        super("Invalid type provided in expected types.");
    }
}
