package org.slf4j.profiler;

import java.util.Random;

public class RandomIntegerArrayGenerator {
  Random rand = new Random(11);
  
  int[] generate(int size) {
    int[] result = new int[size];
    for(int i = 0; i < size; i++) {
      int r = rand.nextInt();
      result[i] = r;
    }
    return result;
  }
}
