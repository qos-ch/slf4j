/**
 * Copyright (c) 2004-2016 QOS.ch
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
package org.slf4j.basicTests;

import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactoryFriend;
import org.slf4j.testHarness.MultithreadedInitializationTest;

/**
 * Checks that when no binding is present, proper clean up is performed by LoggerFactory.
 * 
 *  See SLF4J-469
 * 
 * @author David Harsha
 */
public class NoBindingMultithreadedInitializationTest extends MultithreadedInitializationTest {
    final String loggerName = this.getClass().getName();

    @Before
    public void setup() {
        LoggerFactoryFriend.reset();
    }

    @After
    public void tearDown() throws Exception {
        LoggerFactoryFriend.reset();
    }

    @Override
    protected long getRecordedEventCount() {
        return eventCount.get();
    }
}
