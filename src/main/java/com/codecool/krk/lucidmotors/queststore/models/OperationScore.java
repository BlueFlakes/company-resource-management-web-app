package com.codecool.krk.lucidmotors.queststore.models;

// Should be used only for connection between view <- (controller <- model) relation
public class OperationScore<T extends Enum<T>> {
    private T operationStatus;
    private String message;

    public OperationScore(T operationStatus, String message) {
        this.operationStatus = operationStatus;
        this.message = message;
    }

    public T getOperationStatus( ) {
        return operationStatus;
    }

    public String getMessage( ) {
        return message;
    }
}
