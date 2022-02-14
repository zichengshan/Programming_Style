package com.week6.framework;

import java.util.List;
import java.util.Map;

public interface IFreq {
    Map<String, Integer> top25Freq (List<String> words);
}
