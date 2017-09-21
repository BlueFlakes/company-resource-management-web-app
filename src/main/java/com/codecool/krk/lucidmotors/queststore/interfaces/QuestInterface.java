package com.codecool.krk.lucidmotors.queststore.interfaces;

import com.codecool.krk.lucidmotors.queststore.models.QuestCategory;

public interface QuestInterface {

    String getName();

    void setName(String name);

    QuestCategory getQuestCategory();

    void setQuestCategory(QuestCategory questCategory);

    String getDescription();

    void setDescription(String description);

    Integer getValue();

    void setValue(Integer value);
}
