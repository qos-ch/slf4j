package org.slf4j.profiler;

import java.util.ArrayList;
import java.util.Arrays;

public class SortAndPruneComposites {

  static String NESTED_PROFILER_NAME = "SORT_AND_PRUNE";
  
  final int[] originalArray;
  final int originalArrrayLength;
  
  public SortAndPruneComposites(int[] randomArray) {
    this.originalArray = randomArray;
    this.originalArrrayLength = randomArray.length;
   
  }
  
  public int[] sortAndPruneComposites() {
    // retrieve previously registered profiler named "SORT_AND_PRUNE"
    ProfilerRegistry profilerRegistry = ProfilerRegistry.getThreadContextInstance();
    Profiler sortProfiler = profilerRegistry.get(NESTED_PROFILER_NAME);

    // start a new stopwatch called SORT
    sortProfiler.start("SORT");
    int[] sortedArray = sort();
    // start a new stopwatch called PRUNE_COMPOSITES
    sortProfiler.start("PRUNE_COMPOSITES");
    int result[] = pruneComposites(sortedArray);
    
    return result;
  }

  private int[] sort() {
    int[] sortedArray = new int[originalArrrayLength];
    System.arraycopy(originalArray, 0, sortedArray, 0, originalArrrayLength);
    Arrays.sort(sortedArray);
    return sortedArray;
  }
  
  int[] pruneComposites(int[] sortedArray) {
    ArrayList<Integer> primesArray = new ArrayList<Integer>();
    for(int i = 0; i < originalArrrayLength; i++) {
      int n = sortedArray[i];
      if(isPrime(n)) {
        primesArray.add(n);
      }
    }
    int resultSize = primesArray.size();
    int[] result = new int[resultSize];
    
    for(int i = 0; i < resultSize; i++) {
      result[i] = primesArray.get(i);
    }
    return result;
  }

  public boolean isPrime(int n) {
    if(n < 2) {
      return false;
    }
    if(n%2 == 0) {
      return false;
    }
    for(int i = 3; i*i <=n; i += 2) {
      if(n%i ==0) {
        return false;
      }
    }
    return true;
  }
}
