package com.angybrids.level;

import java.util.HashMap;

public class LevelDatabase {
    public static HashMap<Integer,LevelData> database = new HashMap<>();
    public static void add(LevelData levelData) {
        database.put(levelData.getLevel(), levelData);
    }
    public static LevelData get(int level) {
        return database.get(level);
    }
}
