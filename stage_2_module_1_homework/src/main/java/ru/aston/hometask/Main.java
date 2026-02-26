package ru.aston.hometask;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Integer> map = new CrippledHashMap<>();
        map.put("1", 1);
        System.out.println(map.get("1"));
    }
}
