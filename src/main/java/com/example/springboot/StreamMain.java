package com.example.springboot;

import java.util.List;
import java.util.stream.Collectors;

public class StreamMain {
    public static void main(String[] args) {
        final List<String> cities = List.of("Kazan", "Moscow", "New York", "London");
        // TODO:
        //  1. Stream - lazy:
        //    1. Creation collection -> stream
        //    2. Intermediate stream -> stream
        //    3. Terminal stream -> not stream
        //  2. Functional Interface -> Lambda
        final int reduced = cities
                .stream()
                .filter(o -> o.length() <= 6)
                .mapToInt(o -> o.length())
                .sum();
        System.out.println(reduced);
    }
}
