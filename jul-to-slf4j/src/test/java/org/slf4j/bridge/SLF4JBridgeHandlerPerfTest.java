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
package org.slf4j.bridge;

import java.util.logging.Handler;
import java.util.logging.LogManager;

import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class SLF4JBridgeHandlerPerfTest {

    static String LOGGER_NAME = "yay";
    static int RUN_LENGTH = 100 * 1000;

    // set to false to test enabled logging performance
    boolean disabledLogger = true;

    FileAppender fileAppender;
    org.apache.log4j.Logger log4jRoot;
    java.util.logging.Logger julRootLogger = LogManager.getLogManager().getLogger("");

    java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(LOGGER_NAME);
    org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LOGGER_NAME);

    Handler[] existingHandlers;

    @Before
    public void setUp() throws Exception {
        fileAppender = new FileAppender(new PatternLayout("%r [%t] %p %c %x - %m%n"), "target/test-output/toto.log");

        existingHandlers = julRootLogger.getHandlers();
        for (int i = 0; i < existingHandlers.length; i++) {
            julRootLogger.removeHandler(existingHandlers[i]);
        }
        log4jRoot = org.apache.log4j.Logger.getRootLogger();
        log4jRoot.addAppender(fileAppender);
    }

    @After
    public void tearDown() throws Exception {
        SLF4JBridgeHandler.uninstall();
        fileAppender.close();
        log4jRoot.getLoggerRepository().resetConfiguration();
        for (int i = 0; i < existingHandlers.length; i++) {
            julRootLogger.addHandler(existingHandlers[i]);
        }
    }

    double julLoggerLoop() {
        long start = System.nanoTime();
        for (int i = 0; i < RUN_LENGTH; i++) {
            julLogger.info("jul");
        }
        long end = System.nanoTime();
        return (end - start) * 1.0 / RUN_LENGTH;
    }

    double slf4jLoggerLoop() {
        long start = System.nanoTime();
        for (int i = 0; i < RUN_LENGTH; i++) {
            slf4jLogger.info("slf4j");
        }
        long end = System.nanoTime();
        return (end - start) * 1.0 / RUN_LENGTH;
    }

    @Test
    public void testPerf() {
        SLF4JBridgeHandler.install();

        if (disabledLogger) {
            log4jRoot.setLevel(org.apache.log4j.Level.ERROR);
        }
        julLoggerLoop();
        double julAvg = julLoggerLoop();
        System.out.println("Average cost per call (JUL->SLF4J->log4j): " + julAvg + " nanos");

        slf4jLoggerLoop();
        double slf4jAvg = slf4jLoggerLoop();
        System.out.println("Average cost per call (SLF4J->log4j): " + slf4jAvg + " nanos");
        System.out.println("Ratio " + (julAvg / slf4jAvg));
    }
}
