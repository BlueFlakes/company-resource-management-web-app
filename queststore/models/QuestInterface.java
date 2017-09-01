package queststore.models;

public interface QuestInterface {

    void setName(String name);
    void setQuestCategory(QuestCategory questCategory);
    void setDescription (String description);
    void setValue (Integer value);
    String getName();
    QuestCategory getQuestCategory();
    String getDescription();
    Integer getValue();
}
