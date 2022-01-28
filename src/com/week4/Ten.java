package com.week4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Ten {
    /**
     * Interface
     */
    private interface TenFunc{
        Object call(Object arg) throws IOException;
    }

    private static class TheOne{
        private Object val;
        TheOne(Object val){
            this.val = val;
        }
        public TheOne bind(TenFunc func) throws IOException {
            val = func.call(val);
            return this;
        }
    }

    public static void main(String[] args) throws IOException {
        TheOne theOne = new TheOne(args[0]);
        theOne.bind(new readFile()).bind(new removeStopWords()).bind(new topKWords());
    }

    /**
     * ReadFile() function is used to read the target file and save terms into HashMap
     */
    public static class readFile implements TenFunc {
        @Override
        public Object call(Object arg) throws IOException {
            String fileName = (String) arg;
            Map<String, Integer> map = new HashMap<>();
            // func = removeStopWord()
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
            reader.close();
            return map;
        }
    }

    /**
     * remove stop words from map
     */
    private static class removeStopWords implements TenFunc {
        @Override
        public Object call(Object arg) {
            // func == topKWords
            Map<String, Integer> map = (Map<String, Integer>) arg;
            List<String> stop_words = null;
            try {
                stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../stop_words.txt"))).split(","));
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(String s : stop_words){
                if(map.containsKey(s))
                    map.remove(s);
            }
            return map;
        }
    }

    /**
     * Get the top 25 terms and print them out
     */
    private static class topKWords implements TenFunc {
        @Override
        public Object call(Object arg) {
            // func == no_op
            // Sort the hashmap
            Map<String, Integer> map = (Map<String, Integer>) arg;
            ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            Collections.sort(list, (o1, o2) -> { return -o1.getValue().compareTo(o2.getValue());});

            // Print the top 25 most frequent words
            int k = 0;
            for (Map.Entry<String, Integer> m : list) {
                if (k++ == 25) {break;}
                System.out.println(m.getKey() + " : " + m.getValue());
            }
            return null;
        }
    }
}
