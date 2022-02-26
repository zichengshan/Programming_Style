package com.week8;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Thirty {
    private static BlockingQueue<String> words = new LinkedBlockingQueue<String>();
    private static List<String> stop_words = new ArrayList<>();
    private static BlockingQueue<Map<String, Integer>> freq_space = new LinkedBlockingQueue<>();

     // Thread: get word from words queue and check whether they are valid words and save the sub-frequencies into freq_space
    public static class Process_words extends Thread {
        @Override
        public void run() {
            Map<String, Integer> map = new HashMap<>();
            while (!words.isEmpty()) {
                String word = words.poll();
                if(!stop_words.contains(word))
                    map.put(word, map.getOrDefault(word, 0) + 1);
            }
            freq_space.offer(map);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        // Read the file and split words --> words
        File file = new File(args[0]);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String strLine = reader.readLine();
        while (strLine != null) {
            String[] wordSplit = strLine.split("[^a-zA-Z0-9]+");
            for (String s : wordSplit) {
                String w = s.toLowerCase();
                if(w.length() >= 2)
                    words.offer(w);
            }
            strLine = reader.readLine();
        }
        // Need to close the reader once finished
        reader.close();

        // Read the file and split words --> stop_words
        stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../stop_words.txt"))).split(","));

        // Open 5 new threads to read words, and save them in a list
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            threads.add(new Process_words());
        }

        // Start all five threads
        for(Thread t : threads)
            t.start();

        // Wait for these threads to die
        for(Thread t : threads)
            t.join();

        // Merge the sub frequencies to res
        Map<String, Integer> res = new HashMap<>();
        while(!freq_space.isEmpty()){
            Map<String, Integer> curMap = freq_space.poll();
            for(String key : curMap.keySet()){
                res.put(key, curMap.get(key) + res.getOrDefault(key,0));
            }
        }

        // Sort the hashmap
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(res.entrySet());
        Collections.sort(list, (o1, o2) -> { return -o1.getValue().compareTo(o2.getValue());});

        // Print top 25 most frequent words
        int k = 0;
        for (Map.Entry<String, Integer> m : list) {
            if (k++ == 25) {break;}
            System.out.println(m.getKey() + " : " + m.getValue());
        }
    }
}
