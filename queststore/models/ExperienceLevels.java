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
        Integer level;
        if(!this.levels.isEmpty()) {
            level = findLevelInMap(coins);
            level = (level != null) ? level : 0;
        } else {
            level = coins;
        }
        return level;
    }

    private Integer findLevelInMap(Integer coins){
        Integer level = null;
        for (Integer minimalCoinAmmount : levels.keySet()){
            if(minimalCoinAmmount <= coins) level = this.levels.get(minimalCoinAmmount);
        }

        return level;
    }
    public void addLevel(Integer coins, Integer level) {
        this.levels.put(coins, level);
    }

}