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
package org.slf4j.agent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Date;
import java.util.Properties;

import org.slf4j.instrumentation.LogTransformer;

/**
 * Entry point for slf4j-ext when used as a Java agent.
 *
 */
public class AgentPremain {

    /**
     * JavaAgent premain entry point as specified in the MANIFEST.MF file. See
     * <a href="http://java.sun.com/javase/6/docs/api/java/lang/instrument/package-summary.html">http://java.sun.com/javase/6/docs/api/java/lang/instrument/package-summary.html</a> for details.
     *
     * @param agentArgument
     *            string provided after "=" up to first space
     * @param instrumentation
     *            instrumentation environment provided by the JVM
     */
    public static void premain(String agentArgument, Instrumentation instrumentation) {

        // We cannot do sanity checks for slf4j here as the jars loaded
        // by the application are not visible here.

        LogTransformer.Builder builder = new LogTransformer.Builder();
        builder = builder.addEntryExit(true);

        if (agentArgument != null) {
            Properties args = parseArguments(agentArgument, ",");

            if (args.containsKey(AgentOptions.VERBOSE)) {
                builder = builder.verbose(true);
            }

            if (args.containsKey(AgentOptions.TIME)) {
                printStartStopTimes();
            }

            if (args.containsKey(AgentOptions.IGNORE)) {
                String ignore = args.getProperty(AgentOptions.IGNORE);
                builder = builder.ignore(ignore.split(":"));
            }

            if (args.containsKey(AgentOptions.LEVEL)) {
                builder = builder.level(args.getProperty(AgentOptions.LEVEL));
            }
        }

        instrumentation.addTransformer(builder.build());
    }

    /**
     * Consider the argument string to be a property file (by converting the
     * splitter character to line feeds), and then reading it like any other
     * property file.
     *
     *
     * @param agentArgument
     *            string given by instrumentation framework
     * @param separator
     *            String to convert to line feeds
     * @return argument converted to properties
     */
    private static Properties parseArguments(String agentArgument, String separator) {
        Properties p = new Properties();
        try {
            String argumentAsLines = agentArgument.replaceAll(separator, "\n");
            p.load(new ByteArrayInputStream(argumentAsLines.getBytes()));
        } catch (IOException e) {
            String s = "Could not load arguments as properties";
            throw new RuntimeException(s, e);
        }
        return p;
    }

    /**
     * Print the start message to System.err with the time NOW, and register a
     * shutdown hook which will print the stop message to System.err with the
     * time then and the number of milliseconds passed since.
     *
     */
    private static void printStartStopTimes() {
        final long start = System.currentTimeMillis();

        System.err.println("Start at " + new Date());

        Thread hook = new Thread(() -> {
            long timePassed = System.currentTimeMillis() - start;
            System.err.println("Stop at " + new Date() + ", execution time = " + timePassed + " ms");
        });
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
