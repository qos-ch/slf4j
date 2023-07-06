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
package org.dummy;

import static org.junit.Assert.assertEquals;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.junit.Test;

public class Bug131 {

    @Test
    public void testBug131() {

        ListHandler listHandler = new ListHandler();
        java.util.logging.Logger root = java.util.logging.Logger.getLogger("");
        root.addHandler(listHandler);
        root.setLevel(Level.FINEST);
        Logger log4jLogger = Logger.getLogger("a");
        Category log4jCategory = Logger.getLogger("b");

        int n = 0;

        log4jLogger.trace("msg" + (n++));
        log4jLogger.debug("msg" + (n++));
        log4jLogger.info("msg" + (n++));
        log4jLogger.warn("msg" + (n++));
        log4jLogger.error("msg" + (n++));
        log4jLogger.fatal("msg" + (n++));

        log4jCategory.debug("msg" + (n++));
        log4jCategory.info("msg" + (n++));
        log4jCategory.warn("msg" + (n++));
        log4jCategory.error("msg" + (n++));
        log4jCategory.fatal("msg" + (n++));

        assertEquals(n, listHandler.list.size());

        for (int i = 0; i < n; i++) {
            LogRecord logRecord = (LogRecord) listHandler.list.get(i);
            assertEquals("testBug131", logRecord.getSourceMethodName());
        }
    }
}
