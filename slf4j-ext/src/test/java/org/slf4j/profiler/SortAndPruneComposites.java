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
        int[] result = pruneComposites(sortedArray);

        return result;
    }

    private int[] sort() {
        int[] sortedArray = new int[originalArrrayLength];
        System.arraycopy(originalArray, 0, sortedArray, 0, originalArrrayLength);
        Arrays.sort(sortedArray);
        return sortedArray;
    }

    int[] pruneComposites(int[] sortedArray) {
        ArrayList<Integer> primesArray = new ArrayList<>();
        for (int i = 0; i < originalArrrayLength; i++) {
            int n = sortedArray[i];
            if (isPrime(n)) {
                primesArray.add(n);
            }
        }
        int resultSize = primesArray.size();
        int[] result = new int[resultSize];

        for (int i = 0; i < resultSize; i++) {
            result[i] = primesArray.get(i);
        }
        return result;
    }

    public boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
