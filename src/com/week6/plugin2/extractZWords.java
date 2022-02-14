package com.week6.plugin2;

import com.week6.framework.IExtract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class extractZWords implements IExtract {
    @Override
    public List<String> extractWords(String filePath) {
        List<String> list = new ArrayList<>();
        try {
            // ge the stops words
            List<String> stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../../stop_words.txt"))).split(","));

            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String strLine = reader.readLine();
            while (strLine != null) {
                String[] wordSplit = strLine.split("[^a-zA-Z0-9]+");
                for (String s : wordSplit) {
                    String w = s.toLowerCase();
                    if(w.length() >= 2 && containZ(w))
                        list.add(w);
                }
                strLine = reader.readLine();
            }
            // Need to close the reader once finished
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return the result
        return list;
    }
    private static boolean containZ(String str){
        char[] chars = str.toCharArray();
        for(char c : chars){
            if(c == 'z')
                return true;
        }
        return false;
    }
}
