package com.week7;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Iterators {
    public static void main(String[] args) throws IOException {
        Iterator<Character> i1 = new readFile(args[0]);
        Iterator<String> i2 = new allWords(i1);
        Iterator<String> i3 = new filterWords(i2);
        Iterator<ArrayList<Map.Entry<String, Integer>>> i4 = new count_and_sort(i3);

        int count = 1;

        while(i4.hasNext()){
            ArrayList<Map.Entry<String, Integer>> list = i4.next();
            System.out.println("---------------- Update: " + count + " ---------------------");
            count++;
            // Print the top 25 most frequent words
            int k = 0;
            for (Map.Entry<String, Integer> m : list) {
                if (k++ == 25) {break;}
                System.out.println(m.getKey() + " : " + m.getValue());
            }
        }
    }
}

/**
 * read the file in character
 */
class readFile implements Iterator<Character> {
    FileReader fr;
    private int content;

    public readFile(String path) throws IOException {
        File file = new File(path);
        fr = new FileReader(file);
    }

    @Override
    public boolean hasNext() {
        try {
            content = fr.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content != -1;

    }

    @Override
    public Character next() {
        return (char)content;
    }
}

/**
 * concat the chars to word in string
 */
class allWords implements Iterator<String> {

    private static Iterator<Character> prior;
    private int index = 0;
    private String nextString;
    private boolean flag = true;

    public allWords(Iterator<Character> p) {
        prior = p;
    }

    @Override
    public boolean hasNext() {
        char temp = ' ';
        StringBuilder stringBuilder = new StringBuilder();

        if(prior.hasNext())
            temp = prior.next();
        else
            return false;

        while (Character.isLetterOrDigit(temp)) {
            stringBuilder.append(Character.toLowerCase(temp));
            if(prior.hasNext())
                temp = prior.next();
            else{
                nextString = stringBuilder.toString();
                return true;
            }
        }
        nextString = stringBuilder.toString();
        return true;
    }

    @Override
    public String next() {
        return nextString;
    }
}

/**
 * filter the stop words
 */
class filterWords implements Iterator {
    Iterator<String> prior;
    String word;
    List<String> stop_words;
    public filterWords(Iterator<String> p) throws IOException {
        prior = p;
        stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../stop_words.txt"))).split(","));
    }

    @Override
    public boolean hasNext() {
        while(prior.hasNext()){
            word = prior.next();
            if(!stop_words.contains(word) && word.length() >= 2){
                return true;
            }
        }
        return false;
    }

    @Override
    public String next() {
        return word;
    }
}

/**
 * count and sort every 5000 words
 */
class count_and_sort implements Iterator<ArrayList<Map.Entry<String, Integer>>>{

    private Map<String, Integer> map = new HashMap<>();
    private Iterator<String> prior;
    int count = 0;
    String str;
    public count_and_sort(Iterator<String> p){
        prior = p;
    }
    @Override
    public boolean hasNext() {
        count = 0;
        if(prior.hasNext()){
            str = prior.next();
        }else
            return false;

        while(count == 0 || count % 5000 != 0){
            map.put(str, map.getOrDefault(str, 0) + 1);
            count++;
            if(prior.hasNext()){
                str = prior.next();
            }else{
                return true;
            }
        }
        return true;
    }

    @Override
    public ArrayList<Map.Entry<String, Integer>> next() {
        // Sort the hashmap
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> { return -o1.getValue().compareTo(o2.getValue());});
        return list;
    }
}
