package queststore.models;

import java.util.LinkedHashMap;

public class ExperienceLevels{
    private LinkedHashMap<Integer, Integer> levels;

    public Integer computeStudentLevel(Integer coins){
        return (Integer) coins/100;
    }
}