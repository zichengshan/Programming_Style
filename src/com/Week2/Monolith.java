package com.Week2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Monolith {
    private static final List<String> stop_words = new ArrayList<>();
    private static ArrayList<Object[]> wordsFrequency = new ArrayList<>();
    private static final char[] lowerLetter = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static final char[] upperLetter = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final char[] number = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static void main(String[] args) throws IOException {
        // get all the stop words and store them in stop_words arraylist
        stopWords();
        // Read specific file and read each line of the file
        File file = new File(args[0]);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String strLine = reader.readLine();

        // access each line and find all the words
        while(strLine != null){
            // Adding a space after the strLine prevents the last word from being skipped when processing
            strLine = strLine + " ";
            // if start_char is -1: means a matching character has not been found.
            int start_char = -1;
            for(int i = 0; i < strLine.length(); i++){
                // char c is the current char
                char c = strLine.charAt(i);
                // start_char == - 1 means: have not found the starting char
                if(start_char == -1){
                    if(isAlphanumeric(c))
                        start_char = i;
                }
                else{
                    if(!isAlphanumeric(c)){
                        String word  = stringBuilder(strLine, start_char, i-1);
                        // Put the value of start_char back to -1 to search a new start in the next loop
                        start_char = -1;
                        // update the term frequency
                        if(!isStopWord(word) && word.length() >= 2){
                            boolean found = false;
                            for(int j = 0; j < wordsFrequency.size(); j++){
                                if(wordsFrequency.get(j)[0].equals(word)){
                                    wordsFrequency.get(j)[1] = (int)wordsFrequency.get(j)[1] + 1;
                                    found = true;
                                    break;
                                }
                            }
                            if(!found)
                                wordsFrequency.add(new Object[]{word, 1});
                        }
                    }
                }
            }
            strLine = reader.readLine();
        }

        // sort the wordsFrequency arraylist
        sortFunction();

        // print the result
        for(int i = 0; i < 25; i++){
            String string = (String)wordsFrequency.get(i)[0];
            int fre = (int)wordsFrequency.get(i)[1];
            System.out.println(string + " - " + fre);
        }
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

    /**
     * stringBuilder() function has two goals:
     * 1.Get a sub-string from the target string
     * 2.Convert uppercase letters to lowercase letters
     * @param strLine
     * @param start
     * @param end
     * @return
     */
    private static String stringBuilder(String strLine, int start, int end){
        String res = "";
        for(int i = start; i <= end; i++){
            char c = strLine.charAt(i);
            for(int j = 0; j < upperLetter.length; j++){
                if(c == upperLetter[j]){
                    c = lowerLetter[j];
                    break;
                }
            }
            res = res + c;
        }
        return res;
    }

    /**
     * isStopWord() function is used to find whether the given string is in the stop_words list
     * @param str
     * @return
     */
    private static boolean isStopWord(String str){
        for(int i = 0; i < stop_words.size();i++){
            if(str.equals(stop_words.get(i)))
                return true;
        }
        return false;
    }

    /**
     * sortFunction() function is used to sort wordsFrequency arraylist by using Bubble Sort
     */
    private static void sortFunction(){
        for (int i = 0; i < wordsFrequency.size() - 1; i++){
            for (int j = 0; j < wordsFrequency.size() - i - 1; j++){
                if ((int)wordsFrequency.get(j)[1] < (int)wordsFrequency.get(j+1)[1]){
                    Object[] temp = wordsFrequency.get(j);
                    wordsFrequency.set(j, wordsFrequency.get(j+1));
                    wordsFrequency.set(j+1,temp);
                }
            }
        }
    }
}
