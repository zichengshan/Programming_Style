package com.week8;

/**
 * Reference: https://www.tutorialspoint.com/map_reduce/map_reduce_introduction.htm
 */
//
//import java.util.HashMap;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Map;
//import java.util.LinkedHashMap;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.io.BufferedReader;
//import java.io.FileReader;
//public class ThirtyTwo{
//
//    static class Element  {
//        String word;
//        int value;
//
//        Element(String word, int value){
//            this.word = word;
//            this.value = value;
//        }
//    }
//    public static List<String> partition(String data_str,int nlines){
//        List<String> lines;
//        lines = Arrays.asList(data_str.split("\n"));
//        List<String> partitionword = new ArrayList<String>();
////        Iterator<String> stringIterator = lines.iterator(); //https://crunchify.com/how-to-iterate-through-java-list-4-way-to-iterate-through-loop/
//        String words = "";
//        int count = 0;
//        int i = 0;
//        while (i < lines.size()) {
//            if (count<nlines){
//                words +=" "+ lines.get(i);
//                count+=1;
//            }
//            else if (count==nlines){
//                words +=" "+ lines.get(i);
//                partitionword.add(words);
//                count = 0;
//                words = "";
//            }
//            i++;
//        }
//        return partitionword;
//    }
//
//    public static HashMap<String,Integer> split_words(String data_str) {
//        List<String> stop_words = new ArrayList<String>();
//        try{
//            // read stop words
//            stop_words = Files.lines(Paths.get("../stop_words.txt"))
//                    .flatMap(line -> Arrays.stream(line.split(",")))
//                    .collect(Collectors.toList());
//        }catch(IOException e){
//            System.out.println(e);
//        }
//        ArrayList<String> words = new ArrayList<>();
//        // read word by word without punctuations
//        String line = data_str;
//        line = line.replaceAll("\\p{Punct}", " ").toLowerCase();
//        String s[] = line.split(" ");
//        for (int i = 0; i < s.length; i++) {
//            if (s[i].length() > 1) {
//                if (!stop_words.contains(s[i])) {
//                    words.add(s[i]);
//                }
//            }
//        }
//
//        // append words
//        // [(w1, 1), (w2, 1), ..., (wn, 1)]
//        HashMap<String,Integer> tempmap = new HashMap<String,Integer>();
//        ArrayList<ArrayList<Element>> splitwordlist = new ArrayList<>();
//        for (String w: words){
//            ArrayList<Element> elements = new ArrayList<Element>();
//            elements.add(new Element(w, 1));
//            splitwordlist.add(elements);
//            for (Element element: elements){
//                if (tempmap.containsKey(element.word)){
//                    tempmap.put(element.word,tempmap.get(element.word)+1);
//                }
//                else{
//                    tempmap.put(element.word,1);
//                }
//            }
//        }
//        return tempmap;
//    }
//    public static HashMap<String,Integer> regroup(ArrayList<HashMap<String,Integer>> splitwordlist){
//        // mapping with multiple iterators
//        HashMap<String,Integer> mapping = new HashMap<>();
//        for(HashMap<String,Integer> temp: splitwordlist){
//            for (String word : temp.keySet()) {
//                if (mapping.containsKey(word)){
//                    mapping.put(word,mapping.get(word)+temp.get(word));
//                }else{
//                    mapping.put(word,temp.get(word));
//                }
//            }
//        }
//        return mapping;
//    }
//    public static  HashMap<String, Integer> sort(HashMap<String,Integer> frequencyMap){
//        Set<Map.Entry<String, Integer>> set = frequencyMap.entrySet();
//        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
//        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//            public int compare(Map.Entry<String, Integer> o1,
//                               Map.Entry<String, Integer> o2) {
//                return o2.getValue().compareTo(o1.getValue());
//            }
//        });
//
//        int top = 0;
//        HashMap<String, Integer> topMap = new LinkedHashMap<>();
//        for (Map.Entry<String, Integer> e : list) {
//            topMap.put(e.getKey(),e.getValue());
//            top++;
//            if (top> 24)
//                break;
//        }
//        for (Map.Entry<String, Integer> entry : topMap.entrySet()) {
//            System.out.println( entry.getKey() + " - "+ entry.getValue());
//        }
//        return topMap;
//
//
//    }
//
//
//    public static String read_file(String inputfile)throws IOException{
//        // ref: https://www.journaldev.com/875/java-read-file-to-string
//        BufferedReader reader = new BufferedReader(new FileReader (inputfile));
//        String  line = null;
//        StringBuilder stringBuilder = new StringBuilder();
//        String ls = System.getProperty("line.separator");
//        try {
//            while((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//                stringBuilder.append(ls);
//            }
//
//            return stringBuilder.toString();
//        } finally {
//            reader.close();
//        }
//    }
//
//
//
//
//    public static void main(String[] args)throws IOException{
//        String input_file = read_file(args[0]);
////        List<String> partition_res = partition(input_file,200);
////        int count = 0;
//        ArrayList<HashMap<String,Integer>> tempsplitlist = new ArrayList<>();
//        for (String partitionwd: partition(input_file,200)){
//            tempsplitlist.add((split_words(partitionwd)));
//        }
//        HashMap<String,Integer> regrouplist = new HashMap<>();
//        regrouplist = regroup(tempsplitlist);
//
//        HashMap<String, Integer> finalmap = new HashMap<>();
//        finalmap = sort(regrouplist);
//    }
//}
