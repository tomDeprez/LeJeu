package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FizzBuzzTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4})
    public void getNumberWhenNotModulo3And5(int input) {
      String result = String.valueOf(input);
      FizzBuzz fb = new FizzBuzz();
  
      assertEquals(result, fb.build(input));
    }

    @Test
    public void getFizzWithModulo3(){
      int imput = 3;
      String result = "Fizz";

      FizzBuzz fb = new FizzBuzz();

      assertEquals(result, fb.build(imput));
    }

    @Test
    public void getFizzWithModulo6(){
      int imput = 6;
      String result = "Fizz";

      FizzBuzz fb = new FizzBuzz();

      assertEquals(result, fb.build(imput));
    }

    @Test
    public void getBuzzWithModulo5(){
      int imput = 5;
      String result = "Buzz";

      FizzBuzz fb = new FizzBuzz();

      assertEquals(result, fb.build(imput));
    }

    @ParameterizedTest
    @ValueSource(ints = {15, 30, 45})
    public void getNumberWhenModuloFizzAndBuzz(int input) {
      String result = "FizzBuzz";
      FizzBuzz fb = new FizzBuzz();
  
      assertEquals(result, fb.build(input));
    }
}
