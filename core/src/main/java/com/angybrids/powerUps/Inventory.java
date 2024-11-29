package com.angybrids.powerUps;

import java.util.HashMap;

public class Inventory {
    public static final HashMap<String, Integer> inventory = new HashMap<>();

    public static void addItem(String s) {
        inventory.put(s, inventory.getOrDefault(s, 0) + 1);
    }

    public static int getItemCount(String s) {
        return inventory.getOrDefault(s, 0);
    }

    public static void useItem(String s) {
        inventory.put(s, inventory.getOrDefault(s, 0) - 1);
    }

    public static void display() {
        System.out.println(inventory);
    }
}
