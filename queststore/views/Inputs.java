    public String getInput(String question){
        System.out.print(question + ": ");
        return in.nextLine().trim();
    }

    public ArrayList<String> getValidatedInputs(String[] questions, String[] expectedTypes) {
        ArrayList<String> receivedData = null;

        try {
            areEqualByLength(questions, expectedTypes);
            areDeliveredTypesCorrect(expectedTypes);
            receivedData = getMultipleInputs(questions, expectedTypes);

        } catch (InvalidArgumentException e) {
            System.out.println(e.getMessage());
        } catch (NotEqualElementsException e) {
            System.out.println(e.getMessage());
        }

        return receivedData;
    }

    private ArrayList<String> getMultipleInputs(String[] deliveredQuestions, String[] expectedTypes) {
        ArrayList<String> collectedData = new ArrayList<>();
        boolean isExpectedType;
        String userInput;

        for(int i = 0; i < deliveredQuestions.length; i++) {

            do {
                userInput = getInput(deliveredQuestions[i]);
                isExpectedType = true;

                try {
                    validateUserInput(userInput, expectedTypes[i]);

                } catch (NumberFormatException e) {
                    isExpectedType = false;
                    System.out.println("Error: failure due to wrong input delivered.\n");

                } catch (IllegalArgumentException e) {
                    isExpectedType = false;
                    System.out.println(e.getMessage());
                }

            } while (!isExpectedType);

            collectedData.add(userInput);
        }

        return collectedData;
    }

    private void validateUserInput(String userInput, String expectedType) {
        if (expectedType.equalsIgnoreCase("String")) {
            if (userInput.isEmpty()) {
                throw new IllegalArgumentException("Error: expected at least one sign.\n");
            }
        } else if (expectedType.equalsIgnoreCase("Integer")) {
            Integer.parseInt(userInput);
        } else if (expectedType.equalsIgnoreCase("Float")) {
            Float.parseFloat(userInput);
        } else if (expectedType.equalsIgnoreCase("Double")) {
            Double.parseDouble(userInput);
        }
    }
