package com.week7;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {
        System.out.println(readFile("../pride-and-prejudice.txt"));
    }
    public static int readFile(String fileName) throws IOException {
        List<String> stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../stop_words.txt"))).split(","));
        int count = 0;
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String strLine = reader.readLine();
        while (strLine != null) {
            String[] wordSplit = strLine.split("[^a-zA-Z0-9]+");
            for (String s : wordSplit) {
                String w = s.toLowerCase();
                if(w.length() >= 2 && !stop_words.contains(w))
                    count++;
            }
            strLine = reader.readLine();
        }
        // Need to close the reader once finished
        reader.close();
        return count;
    }
}
