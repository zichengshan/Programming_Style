package com.week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

public class Sixteen {
    public static void main(String[] args) {

    }
    private static class EventManager{
        static HashMap<String, ArrayList<Consumer<String[]>>> subscriptions = new HashMap<>();
        private static void subscribe(String event_type, Consumer<String[]> handler){
            if(subscriptions.containsKey(event_type)){
                subscriptions.get(event_type).add(handler);
            }
            else{
                subscriptions.put(event_type, new ArrayList<>(Arrays.asList(handler)));
            }
        }
        private static void publish(String[] event){
            String event_type = event[0];
            if(subscriptions.containsKey(event_type)){
                for(Consumer<String[]> h : subscriptions.get(event_type))
                    h.accept(event);
            }
        }
    }
}
