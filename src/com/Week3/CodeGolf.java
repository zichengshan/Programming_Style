package com.Week3;

/**
 * Steps in terminal:
 * 1. Type "cd Week3" to get into the Week2 folder
 * 2. Type "javac CodeGolf.java" to compile
 * 2. Type "java CodeGolf ../pride-and-prejudice.txt" to run the code
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CodeGolf {
    private static Map<String, Integer> map = new HashMap<>();
    public static void main(String[] args) throws IOException {
        // Get all the stop words
        List<String> stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../stop_words.txt"))).split(","));
        // Get all the words in pride-and-prejudice.txt
        // Save all the words into map
        // Convert uppercase letters to lowercase letters
        // The length of the word is greater than or equal to 2
        Arrays.asList(new String(Files.readAllBytes(Paths.get(args[0]))).split("[^a-zA-Z0-9]+")).forEach(str -> {
            if (!stop_words.contains(str.toLowerCase()) && str.toLowerCase().length() >= 2)
                map.put(str.toLowerCase(), map.getOrDefault(str.toLowerCase(), 0) + 1);
        });

        // Sort the hashmap
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> { return -o1.getValue().compareTo(o2.getValue());});

        // Print the top 25 most frequent words
        int k = 0;
        for (Map.Entry<String, Integer> m : list) {
            if (k++ == 25) {break;}
            System.out.println(m.getKey() + " : " + m.getValue());
        }
    }
}