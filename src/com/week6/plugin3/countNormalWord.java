package com.week6.plugin3;

import com.week6.framework.IFreq;

import java.util.*;

public class countNormalWord implements IFreq {
    @Override
    public Map<String, Integer> top25Freq(List<String> words) {
        Map<String, Integer> map = new HashMap<>();
        for(String word : words)
            map.put(word, map.getOrDefault(word, 0) + 1);
        return map;
    }
}
