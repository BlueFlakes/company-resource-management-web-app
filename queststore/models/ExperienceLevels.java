package queststore.models;

import java.util.TreeMap;

public class ExperienceLevels{
    private TreeMap<Integer, Integer> levels;

    public Integer computeStudentLevel(Integer coins){
        return (Integer) coins/100;
    }
}