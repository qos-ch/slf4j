package org.slf4j.profiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 
 * This demo illustrates usage of SLF4J profilers. It is almost identical to
 * the first NestProfilerDemo, except that it uses a logger instead of
 * printing its output on the console.
 * 

 * @author Ceki Gulcu
 */
public class NestedProfilerDemo2 {

  static Logger logger = LoggerFactory.getLogger(NestedProfilerDemo2.class);
  
  public static void main(String[] args) {
    Profiler profiler = new Profiler("DEMO");
    // associate a logger with the profiler
    profiler.setLogger(logger);
    
    ProfilerRegistry profilerRegistry = ProfilerRegistry.getThreadContextInstance();
    profiler.registerWith(profilerRegistry);
    
    profiler.start("RANDOM");
    RandomIntegerArrayGenerator riaGenerator = new RandomIntegerArrayGenerator();
    int n = 10*1000;
    int[] randomArray = riaGenerator.generate(n);
    
    profiler.startNested(SortAndPruneComposites.NESTED_PROFILER_NAME);
    
    SortAndPruneComposites pruner = new SortAndPruneComposites(randomArray);
    pruner.sortAndPruneComposites();
    
    // stop and log
    profiler.stop().log();
  }
}
