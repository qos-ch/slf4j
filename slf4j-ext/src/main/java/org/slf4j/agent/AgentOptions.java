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

/**
 * <p>
 * All recognized options in the string passed to the java agent. For
 * "java -javaagent:foo.jar=OPTIONS HelloWorld" this would be "OPTIONS".
 * </p>
 * <p>
 * It is considered to be a list of options separated by (currently) ";", on the
 * form "option=value". The interpretation of "value" is specific to each
 * option.
 * </p>
 */
public class AgentOptions {

    /**
     * List of class prefixes to ignore when instrumenting. Note: Classes loaded
     * before the agent cannot be instrumented.
     */
    public static final String IGNORE = "ignore";
    /**
     * Indicate the SLF4J level that should be used by the logging statements
     * added by the agent. Default is "info".
     */
    public static final String LEVEL = "level";
    /**
     * Indicate that the agent should print out "new java.util.Date()" at the time
     * the option was processed and at shutdown time (using the shutdown hook).
     * 
     */
    public static final String TIME = "time";
    /**
     * Indicate that the agent should log actions to System.err, like adding
     * logging to methods, etc.
     * 
     */
    public static final String VERBOSE = "verbose";

}
