package com.week7;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Iterators {
    public static void main(String[] args) throws IOException {
        Iterator<Character> chars = new readFile(args[0]);
        Iterator<String> words = new allWords(chars);
        while(words.hasNext()){
            System.out.println(words.next());
        }
    }
}

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

        if(!flag)
            return false;

        if(prior.hasNext()){
            temp = prior.next();
        }else{
            flag = false;
//            return false;
        }

        while (Character.isLetterOrDigit(temp) && flag) {
            stringBuilder.append(Character.toLowerCase(temp));
//            System.out.println(temp);
            if(prior.hasNext())
                temp = prior.next();
            else{
                flag = false;
                nextString = stringBuilder.toString();
                return true;
            }
        }
        nextString = stringBuilder.toString();
        return true;
//        return prior.hasNext();
    }

    @Override
    public String next() {
        return nextString;
//        StringBuilder stringBuilder = new StringBuilder();
//        char temp = prior.next();
//        temp = prior.next();
//        while (Character.isLetterOrDigit(temp)) {
//            stringBuilder.append(Character.toLowerCase(temp));
////            System.out.println(temp);
//            if(prior.hasNext())
//                temp = prior.next();
//            else
//                return stringBuilder.toString();
//        }
//        return stringBuilder.toString();
    }
}

class filterWords implements Iterator {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }
}
