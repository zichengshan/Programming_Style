package com.week4;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.*;

public class Sixteen {
    public static void main(String[] args) {
        EventManager em = new EventManager();
        new DataStorage(em);
        new StopWordFilter(em);
        new WordFrequencyCounter(em);
        new WordFrequencyApplication(em);
        new ZCounter(em);
        em.publish(new String[]{"run",args[0]});
    }

    private static class EventManager{
        static HashMap<String,ArrayList<Consumer<String[]>>> subscriptions = new HashMap<>();

        private static void subscribe(String event_type, Consumer<String[]> handler){
            if (subscriptions.containsKey(event_type)){
                subscriptions.get(event_type).add(handler);
            }
            else{
                subscriptions.put(event_type,new ArrayList<>(Arrays.asList(handler)));
            }
        }

        private static void publish(String[] event){
            String event_type = event[0];
            if (subscriptions.containsKey(event_type)){
                for (Consumer<String[]> h: subscriptions.get(event_type)){
                    h.accept(event);
                }
            }
        }
    }

    private static class DataStorage{
        static private EventManager event_manager;
        static private ArrayList<String> terms = new ArrayList<>();

        DataStorage(EventManager em){
            event_manager = em;
            event_manager.subscribe("load", (String[] event)->load(event));
            event_manager.subscribe("start", (String[] event)->produce_words(event));
        }

        private static void load(String[] event){
            String path_to_file = event[1];
            // read words in file
            try {
                File file = new File(path_to_file);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String strLine = reader.readLine();
                while (strLine != null) {
                    String[] wordSplit = strLine.split("[^a-zA-Z0-9]+");
                    for (String s : wordSplit) {
                        String w = s.toLowerCase();
                        if(w.length() >= 2)
                            terms.add(w);
                    }
                    strLine = reader.readLine();
                }
                // Need to close the reader once finished
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void produce_words(String[] event){
            for (String term : terms){
                event_manager.publish(new String[]{"word",term});
            }
            event_manager.publish(new String[]{"eof"});
        }
    }

    private static class StopWordFilter{
        private static EventManager event_manager;
        private static ArrayList<String> stop_words = new ArrayList<>();

        StopWordFilter(EventManager em){
            event_manager = em;
            event_manager.subscribe("load", (String[] event)->load(event));
            event_manager.subscribe("word", (String[] event)->is_stop_word(event));
        }

        private static void load(String[] event){
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

        private static void is_stop_word(String[] event){
            String word = event[1];
            if (!stop_words.contains(word)){
                event_manager.publish(new String[]{"valid_word", word});
            }
        }
    }

    private static class WordFrequencyCounter{
        static private EventManager event_manager;
        static private HashMap<String,Integer> map = new HashMap<>();

        WordFrequencyCounter(EventManager em){
            event_manager = em;
            event_manager.subscribe("valid_word", (String[] event)->increment_count(event));
            event_manager.subscribe("print", (String[] event)->print_freqs(event));
        }

        private static void increment_count(String[] event){
            String word = event[1];
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        /**
         * find the top K frequent terms in target file
         * use min heap
         * @param event
         */
        private static void print_freqs(String[] event){
            Queue<String> heap = new PriorityQueue<>((n1, n2) -> map.get(n1) - map.get(n2));
            String[] res = new String[25];
            for(String s : map.keySet()){
                heap.add(s);
                if(heap.size() > 25)
                    heap.poll();
            }

            for(int i = 24; i >= 0; --i){
                String word = heap.poll();
                res[i] = word;
            }

            for(int i = 0; i < 25; i++)
                System.out.println(res[i] + " - " + map.get(res[i]));
            System.out.println("----------------");
        }
    }

    private static class ZCounter{
        static private EventManager event_manager;
        static int num;

        ZCounter(EventManager em){
            event_manager = em;
            event_manager.subscribe("valid_word", (String[] event)->count(event));
            event_manager.subscribe("print", (String[] event)->printZNum(event));
        }

        private static void count(String[] event){
            String word = event[1];
            for(char w: word.toCharArray())
                if(w == 'z')
                    num++;
        }

        private static void printZNum(String[] event){
            System.out.println("Number of words with z: " + num);
        }
    }

    private static class WordFrequencyApplication{
        static private EventManager event_manager;

        WordFrequencyApplication(EventManager em){
            event_manager = em;
            event_manager.subscribe("run", (String[] event)->run(event));
            event_manager.subscribe("eof", (String[] event)->stop(event));
        }

        private static void run(String[] event){
            String path_to_file = event[1];
            event_manager.publish(new String[]{"load", path_to_file});
            event_manager.publish(new String[]{"start"});
        }

        private static void stop(String[] event){
            event_manager.publish(new String[]{"print"});
        }
    }

}