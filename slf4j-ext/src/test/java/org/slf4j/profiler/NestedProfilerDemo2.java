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
        int n = 10 * 1000;
        int[] randomArray = riaGenerator.generate(n);

        profiler.startNested(SortAndPruneComposites.NESTED_PROFILER_NAME);

        SortAndPruneComposites pruner = new SortAndPruneComposites(randomArray);
        pruner.sortAndPruneComposites();

        // stop and log
        profiler.stop().log();
    }
}
