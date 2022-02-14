package com.week6.plugin4;

import com.week6.framework.IFreq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class countFirstLetter implements IFreq {
    @Override
    public Map<String, Integer> top25Freq(List<String> words) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words){
            String w = word.toLowerCase();
            if(Character.isLetter(w.charAt(0))){
                String firstChar = String.valueOf(w.charAt(0));
                map.put(firstChar, map.getOrDefault(firstChar, 0) + 1);
            }
        }
        return map;
    }
}
