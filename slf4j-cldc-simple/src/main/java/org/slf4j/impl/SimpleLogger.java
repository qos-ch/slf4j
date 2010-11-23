/*
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.ch
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

package org.slf4j.impl;

import org.slf4j.Logger;

/**
 * A simple (and direct) implementation that logs messages of level INFO or
 * higher on the console (<code>System.err<code>).
 * 
 * <p>The output includes the relative time in milliseconds, thread
 * name, the level, logger name, and the message followed by the line
 * separator for the host.  In log4j terms it amounts to the "%r [%t]
 * %level %logger - %m%n" pattern. </p>
 * 
 * <p>Sample output follows.</p>
<pre>
176 [main] INFO examples.Sort - Populating an array of 2 elements in reverse order.
225 [main] INFO examples.SortAlgo - Entered the sort method.
304 [main] INFO examples.SortAlgo - Dump of integer array:
317 [main] INFO examples.SortAlgo - Element [0] = 0
331 [main] INFO examples.SortAlgo - Element [1] = 1
343 [main] INFO examples.Sort - The next log statement should be an error message.
346 [main] ERROR examples.SortAlgo - Tried to dump an uninitialized array.
        at org.log4j.examples.SortAlgo.dump(SortAlgo.java:58)
        at org.log4j.examples.Sort.main(Sort.java:64)
467 [main] INFO  examples.Sort - Exiting main method.
</pre>
 * 
 * <p>
 * Modified version to support CLDC 1.1
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @author Marcel Patzlaff
 */
public class SimpleLogger implements Logger {
    /**
     * Mark the time when this class gets loaded into memory.
     */
    private static final long startTime= System.currentTimeMillis();
    private static String TRACE_STR= "TRACE";
    private static String DEBUG_STR= "DEBUG";
    private static String INFO_STR= "INFO";
    private static String WARN_STR= "WARN";
    private static String ERROR_STR= "ERROR";

    private final String name;
    private final int logLevel;

    /**
     * Package access allows only {@link SimpleLoggerFactory} to instantiate
     * SimpleLogger instances.
     */
    SimpleLogger(String name, int logLevel) {
        this.name= name;
        this.logLevel= logLevel;
    }
    
    public String getName() {
        return name;
    }

    public boolean isTraceEnabled() {
        return logLevel >= 5;
    }

    public void trace(String msg) {
        if(isTraceEnabled()) {
            log(TRACE_STR, msg, null);
        }
    }

    public void trace(String msg, Throwable t) {
        if(isTraceEnabled()) {
            log(TRACE_STR, msg, t);
        }
    }

    public boolean isDebugEnabled() {
        return logLevel >= 4;
    }

    public void debug(String msg) {
        if(isDebugEnabled()) {
            log(DEBUG_STR, msg, null);
        }
    }

    public void debug(String msg, Throwable t) {
        if(isDebugEnabled()) {
            log(DEBUG_STR, msg, t);
        }
    }

    /**
     * This is our internal implementation for logging regular
     * (non-parameterized) log messages.
     * 
     * @param level
     * @param message
     * @param t
     */
    private void log(String level, String message, Throwable t) {
        StringBuffer buf= new StringBuffer();

        long millis= System.currentTimeMillis();
        buf.append(millis - startTime);

        buf.append(" [");
        buf.append(Thread.currentThread().getName());
        buf.append("] ");

        buf.append(level);
        buf.append(" ");

        buf.append(name);
        buf.append(" - ");

        buf.append(message);

        System.err.println(buf.toString());
        if (t != null) {
            t.printStackTrace();
        }
    }

    public boolean isInfoEnabled() {
        return logLevel >= 3;
    }

    /**
     * A simple implementation which always logs messages of level INFO
     * according to the format outlined above.
     */
    public void info(String msg) {
        if(isInfoEnabled()) {
            log(INFO_STR, msg, null);
        }
    }

    /**
     * Log a message of level INFO, including an exception.
     */
    public void info(String msg, Throwable t) {
        if(isInfoEnabled()) {
            log(INFO_STR, msg, t);
        }
    }

    public boolean isWarnEnabled() {
        return logLevel >= 2;
    }

    /**
     * A simple implementation which always logs messages of level WARN
     * according to the format outlined above.
     */
    public void warn(String msg) {
        if(isWarnEnabled()) {
            log(WARN_STR, msg, null);
        }
    }

    /**
     * Log a message of level WARN, including an exception.
     */
    public void warn(String msg, Throwable t) {
        if(isWarnEnabled()) {
            log(WARN_STR, msg, t);
        }
    }

    public boolean isErrorEnabled() {
        return logLevel >= 1;
    }

    /**
     * A simple implementation which always logs messages of level ERROR
     * according to the format outlined above.
     */
    public void error(String msg) {
        if(isErrorEnabled()) {
            log(ERROR_STR, msg, null);
        }
    }

    /**
     * Log a message of level ERROR, including an exception.
     */
    public void error(String msg, Throwable t) {
        if(isErrorEnabled()) {
            log(ERROR_STR, msg, t);
        }
    }
}
