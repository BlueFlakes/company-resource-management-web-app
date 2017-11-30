package com.codecool.krk.lucidmotors.queststore.models;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StackedBoughtArtifact extends BoughtArtifact {
    private Integer amount = 0;

    public StackedBoughtArtifact(BoughtArtifact boughtArtifact, Integer amount) throws DaoException {
        super(boughtArtifact.getName(), boughtArtifact.getPrice(), boughtArtifact.getArtifactCategory(), boughtArtifact.getDescription(),
                boughtArtifact.getId(), boughtArtifact.getRealDate(), boughtArtifact.isUsed());
        this.amount = amount;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public static List<StackedBoughtArtifact> getUserStackedBoughtArtifacts(List<BoughtArtifact> boughtArtifacts) throws DaoException {
        Map<String, StackedBoughtArtifact> countedBoughtArtifacts = getCountedUserArtifactsByState(boughtArtifacts);

        return countedBoughtArtifacts.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private static Map<String, StackedBoughtArtifact> getCountedUserArtifactsByState(List<BoughtArtifact> boughtArtifacts)
            throws DaoException {

        Map<String, StackedBoughtArtifact> countedArtifacts = new LinkedHashMap<>();

        for (int i = 0; i < boughtArtifacts.size(); i++) {
            BoughtArtifact boughtArtifact = boughtArtifacts.get(i);

            boolean boughtArtifactState = boughtArtifact.isUsed();
            Integer amount = 0;

            for (BoughtArtifact nextArtifact : boughtArtifacts) {
                if (boughtArtifact.getName().equals(nextArtifact.getName())
                        && boughtArtifactState == nextArtifact.isUsed()) {
                    amount++;
                }
            }

            String identificator = boughtArtifactState ? boughtArtifact.getName() + "true" : boughtArtifact.getName() + "false";
            countedArtifacts.put(identificator, new StackedBoughtArtifact(boughtArtifact, amount));
        }

        return countedArtifacts;
    }
}
