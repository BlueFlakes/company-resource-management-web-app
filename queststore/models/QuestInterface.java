package queststore.models;

public interface QuestInterface{
    public void setName(String name);
    public void setQuestCategory(QuestCategory questCategory);
    public void setDescription (String description);
    public void setValue (Integer value);
    public String getName();
    public QuestCategory getQuestCategory();
    public String getDescription();
    public Integer getValue();
}