package com.codecool.krk.lucidmotors.queststore.views;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import com.codecool.krk.lucidmotors.queststore.exceptions.NotEqualElementsException;
import com.codecool.krk.lucidmotors.queststore.exceptions.InvalidArgumentException;

public class Inputs {
    private final Scanner in = new Scanner(System.in);

    public String getInput(String question){
        System.out.print(question);
        return in.nextLine().trim();
    }

    public ArrayList<String> getValidatedInputs(String[] questions, String[] expectedTypes) {
        ArrayList<String> receivedData = null;

        try {
            areEqualByLength(questions, expectedTypes);
            areDeliveredTypesCorrect(expectedTypes);
            receivedData = getMultipleInputs(questions, expectedTypes);

        } catch (InvalidArgumentException | NotEqualElementsException e) {
            System.out.println(e.getMessage());
        }

        return receivedData;
    }

    private void areEqualByLength(String[] deliveredQuestions, String[] expectedTypes) throws NotEqualElementsException {
        if (deliveredQuestions.length != expectedTypes.length) {
            throw new NotEqualElementsException();
        }
    }

    private void areDeliveredTypesCorrect(String[] deliveredTypes) throws InvalidArgumentException {
        ArrayList<String> correctTypes = new ArrayList<String>() {{
            add("integer");
            add("string");
            add("float");
            add("double");
        }};

        for(String type : deliveredTypes) {
            type = type.toLowerCase();

            if (!correctTypes.contains(type)) {
                throw new InvalidArgumentException();
            }
        }
    }

    private ArrayList<String> getMultipleInputs(String[] deliveredQuestions, String[] expectedTypes) {
        ArrayList<String> collectedData = new ArrayList<>();
        boolean isExpectedType;
        String userInput;

        for (int i = 0; i < deliveredQuestions.length; i++) {

            do {
                userInput = getInput(deliveredQuestions[i]);
                isExpectedType = handleUserInputValidation(userInput, expectedTypes[i]);

            } while (!isExpectedType);

            collectedData.add(userInput);
        }

        return collectedData;
    }

    private boolean handleUserInputValidation(String userInput, String expectedType) {
        boolean isValid = false;

        if (isInputApproved(userInput, expectedType)) {
            isValid = true;
        } else {
            boolean isFloatingPointNumber = (expectedType.equalsIgnoreCase("float")
                    || expectedType.equalsIgnoreCase("double"));

            String errorMessageType = isFloatingPointNumber ? "floating point number!" : expectedType.toLowerCase();
            System.out.println("Error: ~expect " + errorMessageType);
        }

        return isValid;
    }

    private Boolean isInputApproved(String userInput, String expectedType) {
        Boolean isValid = null;

        if (expectedType.equalsIgnoreCase("String")) {
            isValid = isProperBuildedString(userInput);

        } else if (expectedType.equalsIgnoreCase("Integer")) {
            isValid = isInteger(userInput);

        } else if (expectedType.equalsIgnoreCase("Float")) {
            isValid = isFloatingPointNumber(userInput);

        } else if (expectedType.equalsIgnoreCase("Double")) {
            isValid = isFloatingPointNumber(userInput);
        }

        return isValid;
    }

    private boolean isProperBuildedString(String userInput) {

        return !userInput.isEmpty();
    }

    private boolean isInteger(String userInput) {

        for (char sign : userInput.toCharArray()) {

            if (!Character.isDigit(sign)) {
                return false;
            }
        }

        return !userInput.isEmpty();
    }

    private boolean isFloatingPointNumber(String userInput) {
        int startFromNextValue = 1;
        int wrongValue = -1;

        int separatorIndex = userInput.indexOf(".");

        if (separatorIndex != wrongValue) {
            String firstPartOfNumber = userInput.substring(0, separatorIndex);
            String secondPartOfNumber = userInput.substring(separatorIndex + startFromNextValue, userInput.length());

            if (!(isInteger(firstPartOfNumber) && isInteger(secondPartOfNumber))) {
                return false;
            }

        } else {
            return false;
        }

        return true;
    }
}
