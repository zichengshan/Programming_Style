package com.week8;

import javafx.util.Pair;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ThirtyTwoNew {

    // split the file into chunks and save them in a list
    public static List<String> partition(String path, int number) throws IOException {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String strLine = reader.readLine();
        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;
        while (strLine != null) {
            stringBuilder.append(strLine).append(" ");
            i++;
            if(i % number == 0) {
                list.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                i = 0;
            }
            strLine = reader.readLine();
        }
        list.add(stringBuilder.toString());
        // Need to close the reader once finished
        reader.close();

        return list;
    }

    // for each chunk, we split them into pairs --> <String, 1>
    public static List<Pair<String, Integer>> split_words(String string) throws IOException {
        // get all the stop words
        List<String> stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../stop_words.txt"))).split(","));

        List<Pair<String, Integer>> sortedPairs = new ArrayList<>();
        String[] wordSplit = string.split("[^a-zA-Z0-9]+");
        for (String s : wordSplit) {
            String w = s.toLowerCase();
            if(w.length() >= 2 && !stop_words.contains(w))
                sortedPairs.add(new Pair<>(w,1));
        }
        return sortedPairs;
    }

    // regroup the pairs and get the dictionary as follows:
    // {"a" : {<age : 1>, <along : 1>, <almost : 1> .. }}
    // {"b" : {<bag : 1>, <belong : 1>, <beach : 1> .. }}
    // .......
    public static Map<String, List<Pair<String, Integer>>> regroup(List<List<Pair<String, Integer>>> list){
        Map<String, List<Pair<String, Integer>>> map = new HashMap<>();
        for (List<Pair<String, Integer>> pairs : list){
            for (Pair<String, Integer> p : pairs){
                String firstLetter = String.valueOf(p.getKey().charAt(0));
                if (!map.containsKey(firstLetter)) {
                    map.put(firstLetter, new ArrayList<Pair<String, Integer>>());
                }
                map.get(firstLetter).add(p);
            }
        }
        return map;
    }

    // count the all the words in diverse reducers
    public static Map<String, Integer> count_words(Map<String, List<Pair<String, Integer>>> map){
        Map<String, Integer> res = new HashMap<>();
        for(String key : map.keySet()){
            List<Pair<String, Integer>> pairs = map.get(key);
            for(Pair<String, Integer> p : pairs){
                    res.put(p.getKey(), res.getOrDefault(p.getKey(),0) + 1);
                }
        }
        return res;
    }

    // sort the map and print the top 25 most frequent words
    public static void sort(Map<String, Integer> map){
        // Sort the hashmap
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> { return -o1.getValue().compareTo(o2.getValue());});

        // Print the top 25 most frequent words
        int k = 0;
        System.out.println("------------ Result: -----------------");
        for (Map.Entry<String, Integer> m : list) {
            if (k++ == 25) {break;}
            System.out.println(m.getKey() + " : " + m.getValue());
        }
    }


    public static void main(String[] args) throws IOException {
        List<List<Pair<String, Integer>>> list = new ArrayList<>();
        for(String string : partition(args[0], 200)){
            list.add(split_words(string));
        }
        sort(count_words(regroup(list)));
    }
}
