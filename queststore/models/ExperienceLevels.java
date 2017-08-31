package queststore.models;

import java.util.TreeMap;

public class ExperienceLevels{
    private TreeMap<Integer, Integer> levels;

    public ExperienceLevels() {
        this.levels = new TreeMap<>();
    }

    public ExperienceLevels(TreeMap<Integer, Integer> levels) {
        this.levels = levels;
    }

    public Integer computeStudentLevel(Integer coins){
        return (Integer) coins/100;
    }
}