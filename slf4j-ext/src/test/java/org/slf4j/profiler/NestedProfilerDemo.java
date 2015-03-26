/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
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
public class NestedProfilerDemo {

    public static void main(String[] args) {
        // create a profiler called "DEMO"
        Profiler profiler = new Profiler("DEMO");

        // register this profiler in the thread context's profiler registry
        ProfilerRegistry profilerRegistry = ProfilerRegistry.getThreadContextInstance();
        profiler.registerWith(profilerRegistry);

        // start a stopwatch called "RANDOM"
        profiler.start("RANDOM");
        RandomIntegerArrayGenerator riaGenerator = new RandomIntegerArrayGenerator();
        int n = 10 * 1000;
        int[] randomArray = riaGenerator.generate(n);

        // create and start a nested profiler called "SORT_AND_PRUNE"
        // By virtue of its parent-child relationship with the "DEMO"
        // profiler, and the previous registration of the parent profiler,
        // this nested profiler will be automatically registered
        // with the thread context's profiler registry
        profiler.startNested(SortAndPruneComposites.NESTED_PROFILER_NAME);

        SortAndPruneComposites pruner = new SortAndPruneComposites(randomArray);
        pruner.sortAndPruneComposites();

        // stop and print the "DEMO" printer
        profiler.stop().print();
    }
}
