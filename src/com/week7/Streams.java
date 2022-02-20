package com.week7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Reference:
 * https://www.technicalkeeda.com/java-8-tutorials/word-frequency-count-in-java-8
 * https://www.programmergirl.com/java-8-word-frequency-count-map/
 * https://howtodoinjava.com/java8/java-streams-by-examples/
 */

public class Streams {
    public static void main(String[] args) throws IOException {
        List<String> stop_words =
                Files.lines(Paths.get("../stop_words.txt"))
                .flatMap(line -> Arrays.stream(line.trim().split(",")))
                .collect(Collectors.toList());

        Files.lines(Paths.get(args[0])).flatMap(line -> Arrays.stream(line.trim().split("[^a-zA-Z0-9]+")))
        .map(String :: toLowerCase)
        .filter(word -> word.length() >= 2)
        .filter(word -> !stop_words.contains(word))
        .collect(Collectors.toMap(word -> word, word -> 1, Integer :: sum))
                // sort the map
        .entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldVal, newVal) -> oldVal,
                LinkedHashMap::new
            ))
                // print the top 25 most frequent word
        .entrySet().stream().limit(25).forEach(System.out::println);
    }
}
