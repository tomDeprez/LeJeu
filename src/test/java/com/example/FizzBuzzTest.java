package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FizzBuzzTest {

    @Test
    void testFizznumber1() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("1", fizzBuzz.FizzBuzzFun(1));
    }

    @Test
    void testFizznumber2() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("2", fizzBuzz.FizzBuzzFun(2));
    }

    @Test
    void testFizznumberFizz3() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("2", fizzBuzz.FizzBuzzFun(3));
    }

    @Test
    void testFizznumberFizz4() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertEquals("2", fizzBuzz.FizzBuzzFun(4));
    }

}
