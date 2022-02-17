package com.week7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
//        File file = new File("../stop_words.txt");
//
//        FileReader fr = new FileReader(file);
//            int content;
//            while ((content = fr.read()) != -1) {
//                System.out.println((char) content);
//            }
        StringBuilder stringBuilder = new StringBuilder();
        char a = '3';
        a = Character.toLowerCase(a);
        stringBuilder.append(a);
        stringBuilder.append('b');
        System.out.println(stringBuilder.toString());
    }
}
