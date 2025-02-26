package com.example;

import java.util.HashMap;
import java.util.Map;

public class FizzBuzz {

    private final Map<Integer, String> fizzBuzzMap = new HashMap<Integer, String>() {{
        put(3, "Fizz");
        put(5, "Buzz");
    }};

    public String build(int input) {
        StringBuilder result = new StringBuilder();

        fizzBuzzMap.forEach((key, value) -> {
            if (input % key == 0) {
                result.append(value);
            }
        });

        return result.isEmpty() ? String.valueOf(input) : result.toString();
    }
}
