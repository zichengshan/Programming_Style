package com.week1;

/**
 * Steps in terminal:
 * 1. Type "cd Week1" to get into the Week1 folder
 * 2. Type "javac TermFrequency.java" to compile
 * 2. Type "java TermFrequency ../pride-and-prejudice.txt" to run the code
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TermFrequency {
    private static Map<String, Integer> map = new HashMap<>();
    private static final List<String> stop_words = new ArrayList<>();

    /**
     * ReadFile() function is used to read the target file and save terms into HashMap
     * @param fileName
     * @throws IOException
     */
    public static void readFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String strLine = reader.readLine();
        while (strLine != null) {
            String[] wordSplit = strLine.split("[^a-zA-Z0-9]+");
            for (String s : wordSplit) {
                String w = s.toLowerCase();
                if(w.length() >= 2)
                    map.put(w,map.getOrDefault(w,0) + 1);
            }
            strLine = reader.readLine();
        }
        // Need to close the reader once finished
        reader.close();
    }

    /**
     * stopWords() function is used to parse stop_words.txt file and store keywords into arraylist
     */
    private static void stopWords(){
        String str = "";
        try {
            byte[] encoded = Files.readAllBytes(Paths.get("../stop_words.txt"));
            str = new String(encoded);
        } catch (IOException e) {
            System.out.println("Error");
        }
        String[] words = str.split(",");

        // save keywords to arraylist
        for(String word : words)
            stop_words.add(word);
    }

    /**
     * topKFrequent() function is used to find the top K frequent terms in target file
     * Use min heap
     * @param map
     * @param k
     */
    private static void topKFrequent(Map<String, Integer> map, int k){
        Queue<String> heap = new PriorityQueue<>((n1, n2) -> map.get(n1) - map.get(n2));
        String[] res = new String[25];
        for(String s : map.keySet()){
            heap.add(s);
            if(heap.size() > k)
                heap.poll();
        }

        for(int i = k - 1; i >= 0; --i){
            String word = heap.poll();
            res[i] = word;
        }

        for(int i = 0; i < k; i++)
            System.out.println(res[i] + " - " + map.get(res[i]));
    }

    public static void main(String[] args) throws IOException {
        readFile(args[0]);
        stopWords();
        for(String s : stop_words){
            if(map.containsKey(s))
                map.remove(s);
        }
        topKFrequent(map,25);
    }
}
