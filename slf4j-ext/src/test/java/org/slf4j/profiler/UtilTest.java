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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilTest {

    @Test
    public void testSelectDurationUnitForDisplay() throws InterruptedException {
        assertEquals(DurationUnit.NANOSECOND, Util.selectDurationUnitForDisplay(10));
        assertEquals(DurationUnit.NANOSECOND, Util.selectDurationUnitForDisplay(9 * Util.NANOS_IN_ONE_MICROSECOND));
        assertEquals(DurationUnit.MICROSECOND, Util.selectDurationUnitForDisplay(11 * Util.NANOS_IN_ONE_MICROSECOND));
        assertEquals(DurationUnit.MICROSECOND, Util.selectDurationUnitForDisplay(9 * Util.NANOS_IN_ONE_MILLISECOND));
        assertEquals(DurationUnit.MILLISSECOND, Util.selectDurationUnitForDisplay(11 * Util.NANOS_IN_ONE_MILLISECOND));
        assertEquals(DurationUnit.SECOND, Util.selectDurationUnitForDisplay(11 * Util.NANOS_IN_ONE_SECOND));
    }

}
