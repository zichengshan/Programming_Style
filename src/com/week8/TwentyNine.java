package com.week8;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class TwentyNine {
    // ActiveWFObject is a super class
    public static abstract class ActiveWFObject extends Thread {
        Queue<Object[]> q = new LinkedBlockingQueue<>();
        Boolean stopMe = false;

        public ActiveWFObject() {
            this.start();
        }

        /**
         * stopThread() is used to stop the thread
         */
        public void stopThread() {
            stopMe = true;
        }

        @Override
        public void run() {
            while (!stopMe) {
                Object[] message = q.poll();
                try {
                    if (message != null) {
                        dispatch(message);
                        if (message[0].equals("die"))
                            stopMe = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        abstract public void dispatch(Object[] message) throws IOException, Exception;
    }

    // send the messages to the shared Queue
    private static void sendMessage(ActiveWFObject activeWFObject, Object[] message) {
        activeWFObject.q.add(message);
    }

    /**
     * DataStorageManager is used to read file and split words
     */
    public static class DataStorageManager extends ActiveWFObject {
        List<String> wordsRow = new ArrayList<>();
        StopWordsManager stopWordManager = null;

        @Override
        public void dispatch(Object[] message) throws IOException, InterruptedException {
            if (message[0].equals("init"))
                init(message);
            else if (message[0].equals("send_word_freq"))
                processWords(message[1]);
            else
                // if there is no corresponding order, continue to pass down
                sendMessage(stopWordManager, message);
        }

        private void init(Object[] message) throws IOException, InterruptedException {
            File file = new File(String.valueOf(message[1]));
            stopWordManager = (StopWordsManager) message[2];
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String strLine = reader.readLine();
            while (strLine != null) {
                String[] wordSplit = strLine.split("[^a-zA-Z0-9]+");
                for (String s : wordSplit) {
                    String w = s.toLowerCase();
                    if (w.length() >= 2)
                        wordsRow.add(w);
                }
                strLine = reader.readLine();
            }
            // Need to close the reader once finished
            reader.close();

        }

        /**
         *
         * @param message
         * @throws InterruptedException
         */
        private void processWords(Object message) {
            WordFrequencyController wordFrequencyController = (WordFrequencyController) message;
            // send a copy of list words
            sendMessage(this.stopWordManager, new Object[]{"filter", new ArrayList<String>(wordsRow)});
            sendMessage(this.stopWordManager, new Object[]{"top25", wordFrequencyController});
        }
    }


    /**
     * StopWordsManager is used to read the stop_words.txt and split it
     * Then send the list after filtered
     */
    private static class StopWordsManager extends ActiveWFObject {
        List<String> stop_words = null;
        WordFrequencyManager wordFrequencyManager = null;

        @Override
        public void dispatch(Object[] message) throws IOException {
            if (message[0].equals("init"))
                init(message[1]);
            else if (message[0].equals("filter")) {
                filter(message[1]);
            } else
                // if there is no corresponding order, continue to pass down
                sendMessage(this.wordFrequencyManager, message);
        }

        private void init(Object message) throws IOException {
            wordFrequencyManager = (WordFrequencyManager) message;
            // save the stop words in stop_words list
            stop_words = Arrays.asList(new String(Files.readAllBytes(Paths.get("../stop_words.txt"))).split(","));
        }

        /**
         * filter invalid words
         * @param message
         */
        private void filter(Object message) {
            List<String> wordsRow = (List<String>) message;
            // create a new list to store the valid words
            List<String> validWords = new ArrayList<>();
            for(String str : wordsRow){
                if(!stop_words.contains(str))
                    validWords.add(str);
            }
            sendMessage(wordFrequencyManager, new Object[]{"words", new ArrayList<String>(validWords)});
        }
    }

    /**
     * WordFrequencyManager is used to count the frequency af each word sort the map.
     */
    private static class WordFrequencyManager extends ActiveWFObject {
        Map<String, Integer> map = new HashMap<>();

        @Override
        public void dispatch(Object[] message) {
            if (message[0].equals("words"))
                mapWords(message[1]);
            else if (message[0].equals("top25"))
                top25(message[1]);
        }

        private void mapWords(Object message) {
            // map the valid words
            List<String> words = (List<String>) message;
            for(String word : words)
                map.put(word, map.getOrDefault(word, 0) + 1);
        }

        private void top25(Object message) {
            WordFrequencyController wordFrequencyController = (WordFrequencyController) message;
            // sort the map
            ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            Collections.sort(list, (o1, o2) -> { return -o1.getValue().compareTo(o2.getValue());});
            sendMessage(wordFrequencyController, new Object[]{"top25", list});
        }
    }

    /**
     * WordFrequencyController is used to print the outcome and control the start and end of the process
     */
    private static class WordFrequencyController extends ActiveWFObject {
        DataStorageManager dataStorageManager = null;

        @Override
        public void dispatch(Object[] message) throws Exception {
            if (message[0].equals("run"))
                run(message[1]);
            else if (message[0].equals("top25"))
                display(message[1]);
        }

        private void run(Object message) {
            dataStorageManager = (DataStorageManager) message;
            sendMessage(dataStorageManager, new Object[]{"send_word_freq", this});
        }

        private void display(Object message) {
            List<Map.Entry<String, Integer>> list = (ArrayList<Map.Entry<String, Integer>>) message;
            int k = 0;
            for (Map.Entry<String, Integer> m : list) {
                if (k++ == 25) {break;}
                System.out.println(m.getKey() + " : " + m.getValue());
            }
            sendMessage(dataStorageManager, new Object[]{"die"});
            stopThread();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        WordFrequencyManager wordFrequencyManager = new WordFrequencyManager();
        StopWordsManager stopWordsManager = new StopWordsManager();
        sendMessage(stopWordsManager, new Object[]{"init", wordFrequencyManager});

        DataStorageManager dataStorageManager = new DataStorageManager();
        sendMessage(dataStorageManager, new Object[]{"init", args[0], stopWordsManager});

        WordFrequencyController wordFrequencyController = new WordFrequencyController();
        sendMessage(wordFrequencyController, new Object[]{"run", dataStorageManager});

        // # Wait for the active objects to finish
        wordFrequencyManager.join();
        stopWordsManager.join();
        dataStorageManager.join();
        wordFrequencyController.join();
    }
}