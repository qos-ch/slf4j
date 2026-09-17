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

package org.apache.commons.logging.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.Test;

public class InvokeJCLTest {

    @Test
    public void testIsEnabledAPI() {
        // assume that we are running over slf4j-jdk14
        Log log = LogFactory.getLog(InvokeJCLTest.class);
        assertFalse(log.isTraceEnabled());
        assertFalse(log.isDebugEnabled());
        assertTrue(log.isInfoEnabled());
        assertTrue(log.isWarnEnabled());
        assertTrue(log.isErrorEnabled());
        assertTrue(log.isFatalEnabled());
    }

    @Test
    public void testPrintAPI() {
        Log log = LogFactory.getLog(InvokeJCLTest.class);
        Exception e = new Exception("just testing");

        log.trace(null);
        log.trace("trace message");

        log.debug(null);
        log.debug("debug message");

        log.info(null);
        log.info("info  message");

        log.warn(null);
        log.warn("warn message");

        log.error(null);
        log.error("error message");

        log.fatal(null);
        log.fatal("fatal message");

        log.trace(null, e);
        log.trace("trace message", e);

        log.debug(null, e);
        log.debug("debug message", e);

        log.info(null, e);
        log.info("info  message", e);

        log.warn(null, e);
        log.warn("warn message", e);

        log.error(null, e);
        log.error("error message", e);

        log.fatal(null, e);
        log.fatal("fatal message", e);
    }

    @Test
    public void testAvoidConvertingObjectToString() {
        Log log = LogFactory.getLog(InvokeJCLTest.class);
        Exception e = new Exception("just testing");

        TestMessage fatalMsg = new TestMessage("fatal msg");
        TestMessage errorMsg = new TestMessage("error msg");
        TestMessage warnMsg = new TestMessage("warn msg");
        TestMessage infoMsg = new TestMessage("info msg");
        TestMessage debugMsg = new TestMessage("debug msg");
        TestMessage traceMsg = new TestMessage("trace msg");

        log.fatal(fatalMsg);
        log.fatal(fatalMsg, e);
        assertEquals(2, fatalMsg.invokedCount);

        log.error(errorMsg);
        log.error(errorMsg, e);
        assertEquals(2, errorMsg.invokedCount);

        log.warn(warnMsg);
        log.warn(warnMsg, e);
        assertEquals(2, warnMsg.invokedCount);

        log.info(infoMsg);
        log.info(infoMsg, e);
        assertEquals(2, infoMsg.invokedCount);

        log.debug(debugMsg);
        log.debug(debugMsg, e);
        assertEquals(0, debugMsg.invokedCount);

        log.trace(traceMsg);
        log.trace(traceMsg, e);
        assertEquals(0, traceMsg.invokedCount);
    }

    static class TestMessage {

        private final String msg;
        int invokedCount = 0;

        TestMessage(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            invokedCount++;
            return msg;
        }
    }

}
