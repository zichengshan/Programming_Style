package com.week8;

import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class TwentyNineTest {
    // a thread supper class for all actors
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

    // sending message to receiver (placing it in the queue of the receiver)
    private static void sendMessage(ActiveWFObject activeWFObject, Object[] message) {
        activeWFObject.q.add(message);
    }

    // DataStorageManager creates object that reads targetFile
    public static class DataStorageManager extends ActiveWFObject {
        List<String> words = new ArrayList<>();
        StopWordsManager stopWordManager = null;

        @Override
        public void dispatch(Object[] message) throws IOException {
            if (message[0].equals("init"))
                init(new Object[]{message[1], message[2]});
            else if (message[0].equals("send_word_freq"))
                processWords(message[1]);
            else
                // don't know what to do -> forward to next actor
                sendMessage(stopWordManager, message);
        }

        private void init(Object[] message) throws IOException {
            File file = new File(String.valueOf(message[0]));
            stopWordManager = (StopWordsManager) message[1];
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String strLine = reader.readLine();
            while (strLine != null) {
                String[] wordSplit = strLine.split("[^a-zA-Z0-9]+");
                for (String s : wordSplit) {
                    String w = s.toLowerCase();
                    if (w.length() >= 2)
                        words.add(w);
                }
                strLine = reader.readLine();
            }
            // Need to close the reader once finished
            reader.close();
        }

        private void processWords(Object message) {
            WordFrequencyController recipient = (WordFrequencyController) message;
            for (String word : words)
                sendMessage(this.stopWordManager, new Object[]{"filter", word});
//             when DataStorageManager runs out of words, it sends "top25" to StopWordsManager
            sendMessage(this.stopWordManager, new Object[]{"top25", recipient});
        }
    }


    /**
     *
     */
    private static class StopWordsManager extends ActiveWFObject {
        private List<String> stopWords = null;
        private WordFrequencyManager wordFrequencyManager = null;

        @Override
        public void dispatch(Object[] message) {
            if (message[0].equals("init"))
                init(message[1]);
            else if (message[0].equals("filter")) {
                filter(message[1]);
            } else
                sendMessage(this.wordFrequencyManager, message);
        }

        private void init(Object message) {
            try {
                System.out.println("[StopWordsManager]   init");
                this.wordFrequencyManager = (WordFrequencyManager) message;
                File stopWordsFile = new File("../stop_words.txt");
                Scanner sw = new Scanner(stopWordsFile);
                stopWords = Arrays.asList(sw.nextLine().split(","));
                sw.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                System.out.println("File not found.");
                e.printStackTrace();
            }
        }

        private void filter(Object message) {
            String word = (String) message;
            if (!stopWords.contains(word))
                sendMessage(this.wordFrequencyManager, new Object[]{"word", word});
        }
    }

    // WordFrequencyManager creates object that counts word frequency and sort for top 25
    private static class WordFrequencyManager extends ActiveWFObject {
        private HashMap<String, Integer> wordFreqs = null;

        WordFrequencyManager() {
            System.out.println("[WordFrequencyManager]   init");
            wordFreqs = new HashMap<>();
        }

        @Override
        public void dispatch(Object[] message) {
            if (message[0].equals("word"))
                incrementCount(message[1]);
            else if (message[0].equals("top25"))
                top25(message[1]);
        }

        private void incrementCount(Object message) {
            String word = (String) message;

            if (wordFreqs.containsKey(word))
                wordFreqs.replace(word, wordFreqs.get(word) + 1);
            else
                wordFreqs.put(word, 1);
        }

        private void top25(Object message) {
            WordFrequencyController recipient = (WordFrequencyController) message;
            ArrayList<Map.Entry<String, Integer>> freqsSorted = new ArrayList<>(wordFreqs.entrySet());
            freqsSorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));
            sendMessage(recipient, new Object[]{"top25", freqsSorted});
        }
    }

    // WordFrequencyController run the program and output result
    private static class WordFrequencyController extends ActiveWFObject {
        DataStorageManager dataStorageManager = null;

        @Override
        public void dispatch(Object[] message) throws Exception {
            if (message[0].equals("run"))
                run(message[1]);
            else if (message[0].equals("top25"))
                display(message[1]);
            else
                throw new Exception("Message not understood " + message[0]);
        }

        private void run(Object message) {
            this.dataStorageManager = (DataStorageManager) message;
            sendMessage(this.dataStorageManager, new Object[]{"send_word_freq", this});
        }

        private void display(Object message) {
            ArrayList<Map.Entry<String, Integer>> freqsSorted = (ArrayList<Map.Entry<String, Integer>>) message;
            int count = 0;
            for (Map.Entry<String, Integer> entry : freqsSorted) {
                count++;
                System.out.println(entry.getKey() + "  -  " + entry.getValue());
                if (count == 25)
                    break;
            }
            sendMessage(this.dataStorageManager, new Object[]{"die"});
            this.stopThread();
        }
    }


    public static void main(String[] args) throws InterruptedException {
//        String targetFile = args[0];
        String targetFile = "../pride-and-prejudice.txt";
        WordFrequencyManager wfm = new WordFrequencyManager();

        StopWordsManager swm = new StopWordsManager();
        sendMessage(swm, new Object[]{"init", wfm});

        DataStorageManager dsm = new DataStorageManager();
        sendMessage(dsm, new Object[]{"init", targetFile, swm});

        WordFrequencyController wfc = new WordFrequencyController();
        sendMessage(wfc, new Object[]{"run", dsm});

        // Wait for the active objects to finish
        Thread[] threads = new ActiveWFObject[]{wfm, swm, dsm, wfc};
        for (Thread t : threads)
            t.join();
    }
}