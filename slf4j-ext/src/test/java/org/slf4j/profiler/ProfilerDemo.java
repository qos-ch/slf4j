package org.slf4j.profiler;



/**
 * 
 * This demo illustrates usage of SLF4J profilers.
 * 
 * <p>
 * We have been given the task of generating a large number, say N, 
 * of random integers. We need to transform that array into a smaller array
 * containing only prime numbers. The new array has to be sorted.
 * 
 * <p>
 * While tackling this problem, we would like to measure the
 * time spent in each subtask.
 * 
 * <p>
 * A typical output for this demo would be:
 <pre>
 + Profiler [DEMO]
|-- elapsed time                       [RANDOM]     0.089  seconds.
|---+ Profiler [SORT_AND_PRUNE]
&nbsp;&nbsp;&nbsp;&nbsp;|-- elapsed time                         [SORT]     0.221  seconds.
&nbsp;&nbsp;&nbsp;&nbsp;|-- elapsed time             [PRUNE_COMPOSITES]    11.567  seconds.
&nbsp;&nbsp;&nbsp;&nbsp;|-- Subtotal                   [SORT_AND_PRUNE]    11.788  seconds.
|-- elapsed time               [SORT_AND_PRUNE]    11.788  seconds.
|-- Total                                [DEMO]    11.877  seconds.
</pre>
 * 
 * @author Ceki Gulcu
 */
public class ProfilerDemo {
  
  public static void main(String[] args) {
    Profiler profiler = new Profiler("DEMO");
    ProfilerRegistry profilerRegistry = ProfilerRegistry.getThreadContextInstance();
    profiler.registerWith(profilerRegistry);
    
    profiler.start("RANDOM");
    RandomIntegerArrayGenerator riag = new RandomIntegerArrayGenerator();
    int n = 100*1000;
    int[] randomArray = riag.generate(n);
    
    profiler.startNested(SortAndPruneComposites.NESTED_PROFILER_NAME);
    SortAndPruneComposites pruner = new SortAndPruneComposites(randomArray);
    pruner.sortAndPruneComposites();
    profiler.stop().print();
  }
}
