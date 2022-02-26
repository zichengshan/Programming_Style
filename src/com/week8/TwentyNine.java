package com.week8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwentyNine {
    /**
     *
     */
    private static abstract class ActiveWFObject extends Thread{
        Queue<Object[]> q = new LinkedBlockingQueue<>();
        boolean stopMe = false;

        private ActiveWFObject(){
            // start the thread
            this.start();
        }
        @Override
        public void run(){
            while(!stopMe){
                Object[] message = q.poll();
                if(message != null){
                    try {
                        this.dispatch(message);
                        if(message[0].toString().equals("die")){
                            stopMe = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        abstract public void dispatch(Object[] message) throws Exception;
        public void stopThread(){
            this.stopMe = true;
        }
    }
    // Send the Object[] messages to the receiver activeWFObject
    private static void sendMessage(ActiveWFObject activeWFObject, Object[] message){
            activeWFObject.q.offer(message);
    }

    /**
     *
     */
    public class DataStorageManager extends ActiveWFObject {
        List<String> words = new ArrayList<>();
        StopWordManager stopWordManager = null;

        @Override
        public void dispatch(Object[] message) throws Exception {
            if(message[0].equals("init")){
                init(message);
            }
            else if(message[0].equals("send_word_freq")){
                process_words(message[1]);
            }
            else{
                sendMessage(stopWordManager, message);
            }
        }

        /**
         * init() is used to read file, split words and save the words in the arraylist
         * @param message
         * @throws IOException
         */
        private void init(Object[] message) throws IOException {
            File file = new File(String.valueOf(message[1]));
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String strLine = reader.readLine();
            while (strLine != null) {
                String[] wordSplit = strLine.split("[^a-zA-Z0-9]+");
                for (String s : wordSplit) {
                    String w = s.toLowerCase();
                    if(w.length() >= 2)
                        words.add(w);
                }
                strLine = reader.readLine();
            }
            // Need to close the reader once finished
            reader.close();
        }

        /**
         *
         * @param object
         */
        private void process_words(Object object){
            WordFrequencyController wordFrequencyController = (WordFrequencyController) object;
            for(String str : words){
                sendMessage(stopWordManager, new Object[]{"filter", str});
            }
            sendMessage(wordFrequencyController, new Object[]{"top25", object});
        }
    }

    /**
     *
     */
    public class StopWordManager extends ActiveWFObject {
        List<String> stop_words = new ArrayList<>();
        @Override
        public void dispatch(Object[] message) throws Exception {
            if(message[0].equals("init")){
                init(message[1]);
            }
            else if(message[0].equals("filter")){
                filter(message[1]);
            }
        }

        private void init(Object message){

        }

        private void filter(Object message){

        }
    }

    /**
     *
     */
    public class WordFrequencyManager extends ActiveWFObject{

        @Override
        public void dispatch(Object[] message) throws Exception {

        }
    }

    /**
     *
     */
    public class WordFrequencyController extends ActiveWFObject{

        @Override
        public void dispatch(Object[] message) throws Exception {

        }
    }
}
