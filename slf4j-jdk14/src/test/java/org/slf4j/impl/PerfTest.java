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
package org.slf4j.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.BogoPerf;

@Ignore
public class PerfTest {

    static long REFERENCE_BIPS = 9000;

    @Test
    public void issue63() {

        int LEN = 1000 * 1000 * 10;
        debugLoop(LEN); // warm up
        double avg = debugLoop(LEN);
        long referencePerf = 93;
        BogoPerf.assertDuration(avg, referencePerf, REFERENCE_BIPS);

        // when the code is guarded by a logger.isLoggable condition,
        // duration is about 16 *micro*seconds for 1000 iterations
        // when it is not guarded the figure is 90 milliseconds,
        // i.e. a ration of 1 to 5000
    }

    double debugLoop(int len) {
        Logger logger = LoggerFactory.getLogger(PerfTest.class);
        long start = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            logger.debug("hello");
        }

        long end = System.currentTimeMillis();

        long duration = end - start;
        return duration;
    }

}
