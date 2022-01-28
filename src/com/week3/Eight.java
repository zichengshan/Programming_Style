package com.week3;
/**
 * Steps in terminal:
 * 1. Type "cd Week3" to get into the Week2 folder
 * 2. Type "javac Eight.java" to compile
 * 2. Type "java Eight ../pride-and-prejudice.txt" to run the code
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Eight {
    private static final char[] lowerLetter = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static final char[] upperLetter = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final char[] number = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static List<String> result = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        // map is used to save terms and their corresponding frequency
        Map<String, Integer> map = new HashMap<>();

        // Get all the stop words and save them in an arraylist
        List<String> stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../stop_words.txt"))).split(","));

        // Builder the reader
        File file = new File("../pride-and-prejudice.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String strLine = reader.readLine();

        // We read the file line by line and use recursive method to get all the terms of each line
        while(strLine != null){
            StringReader stringReader = new StringReader(strLine);
            List<String> tempList = parse(stringReader, new ArrayList<>(),stop_words);
            result.addAll(tempList);
            strLine = reader.readLine();
            stringReader.close();
        }

        // Close the reader
        reader.close();

        // save term into the map
        for (int i = 0; i < result.size(); i++){
            String string = result.get(i);
            map.put(string, map.getOrDefault(string, 0) + 1);
        }

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

    /**
     * parse() function is used to iterate to get the characters of one single word, and then recurse to get the rest of the words
     * @param reader
     * @param res
     * @param stop_words
     * @return
     * @throws IOException
     */
    private static List<String> parse(Reader reader, List<String> res, List<String> stop_words) throws IOException {
        int length = 0;
        // check whether the reader has reached the end
        if((length = reader.read()) == -1)
            return res;
        // if reader has not reached the end, append the current character into StringBuilder if it is valid
        char c;
        StringBuilder sb = new StringBuilder();
        if(isAlphanumeric((char)length))
            sb.append(Character.toLowerCase((char)length));

        // iterate to append valid char into the StringBuilder
        // At the same time, convert uppercase letters to lowercase letters
        while(isAlphanumeric(c = (char)reader.read()))
            sb.append(Character.toLowerCase(c));
        if(!stop_words.contains(sb.toString()) && (sb.toString().length() >= 2))
            res.add(sb.toString());
        // Recursive to parse the remaining terms
        return parse(reader, res, stop_words);
    }

    /**
     * isalnum() function is used to check whether a char is alphanumeric
     * @param c
     * @return
     */
    private static boolean isAlphanumeric(char c){
        for(int i = 0; i < lowerLetter.length; i++) {
            if (c == lowerLetter[i])
                return true;
        }
        for(int i = 0; i < upperLetter.length; i++) {
            if (c == upperLetter[i])
                return true;
        }
        for(int i = 0; i < number.length; i++) {
            if (c == number[i])
                return true;
        }
        return false;
    }
}
