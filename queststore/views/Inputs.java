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
