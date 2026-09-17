/*
 * Copyright (C) 2004-2026, QOS.ch (Switzerland)
 * All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *  without limitation  the rights to  use, copy, modify,  merge, publish,
 *  distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  permit persons to whom the Software  is furnished to do so, subject to
 *  the following conditions:
 *
 *  The  above  copyright  notice  and  this permission  notice  shall  be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 *  EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 *  MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

// Contributors:  Kitching Simon <Simon.Kitching@orange.ch>

package org.apache.log4j;

// Contributors:  Kitching Simon <Simon.Kitching@OOOrange.ch>

/**
   <b>Refrain from using this class directly, use
   the {@link Level} class instead</b>.

   @author Ceki G&uuml;lc&uuml; */
public class Priority {

    transient int level;
    transient String levelStr;
    transient int syslogEquivalent;

    public final static int OFF_INT = Integer.MAX_VALUE;
    public final static int FATAL_INT = 50000;
    public final static int ERROR_INT = 40000;
    public final static int WARN_INT = 30000;
    public final static int INFO_INT = 20000;
    public final static int DEBUG_INT = 10000;
    // public final static int FINE_INT = DEBUG_INT;
    public final static int ALL_INT = Integer.MIN_VALUE;

    /**
     * @deprecated Use {@link Level#FATAL} instead.
     */
    final static public Priority FATAL = new Level(FATAL_INT, "FATAL", 0);

    /**
     * @deprecated Use {@link Level#ERROR} instead.
     */
    @Deprecated
    final static public Priority ERROR = new Level(ERROR_INT, "ERROR", 3);

    /**
     * @deprecated Use {@link Level#WARN} instead.
     */
    @Deprecated
    final static public Priority WARN = new Level(WARN_INT, "WARN", 4);

    /**
     * @deprecated Use {@link Level#INFO} instead.
     */
    @Deprecated
    final static public Priority INFO = new Level(INFO_INT, "INFO", 6);

    /**
     * @deprecated Use {@link Level#DEBUG} instead.
     */
    @Deprecated
    final static public Priority DEBUG = new Level(DEBUG_INT, "DEBUG", 7);

    /**
      * Default constructor for deserialization.
      */
    protected Priority() {
        level = DEBUG_INT;
        levelStr = "DEBUG";
        syslogEquivalent = 7;
    }

    /**
       Instantiate a level object.
       @param
     */

    /**
     * Instantiate a level object. 
     * 
     * @param level a level as in int
     * @param levelStr  a levelStr
     * @param syslogEquivalent the syslog equivalent level integer
     */
    protected Priority(int level, String levelStr, int syslogEquivalent) {
        this.level = level;
        this.levelStr = levelStr;
        this.syslogEquivalent = syslogEquivalent;
    }

    /**
       Two priorities are equal if their level fields are equal.
       @since 1.2
     */
    public boolean equals(Object o) {
        if (o instanceof Priority) {
            Priority r = (Priority) o;
            return (this.level == r.level);
        } else {
            return false;
        }
    }

    /**
       Return the syslog equivalent of this priority as an integer.
       @return the Syslog Equivalent of this Priority
     */
    public final int getSyslogEquivalent() {
        return syslogEquivalent;
    }

    /**
     * Returns <code>true</code> if this level has a higher or equal
     *  level than the level passed as argument, <code>false</code>
     *  otherwise.  
     *  
     *  <p>You should think twice before overriding the default
     *  implementation of <code>isGreaterOrEqual</code> method.
     *
     * @param r a priority
     * @return a boolean
     */
    public boolean isGreaterOrEqual(Priority r) {
        return level >= r.level;
    }

    /**
       Return all possible priorities as an array of Level objects in
       descending order.
    
       @deprecated This method will be removed with no replacement.
       @return array of all possible priorities
    */
    @Deprecated
    public static Priority[] getAllPossiblePriorities() {
        return new Priority[] { Priority.FATAL, Priority.ERROR, Level.WARN, Priority.INFO, Priority.DEBUG };
    }

    /**
       Returns the string representation of this priority.
     */
    final public String toString() {
        return levelStr;
    }

    /**
     * Returns the integer representation of this level.
     *
     * @return integer representation of this level
     */
    public final int toInt() {
        return level;
    }

    /**
     * @deprecated Please use the {@link Level#toLevel(String)} method instead.
     * 
     * @param sArg a string to convert to a Priority 
     * @return the corresponding Priority 
    */
    @Deprecated
    public static Priority toPriority(String sArg) {
        return Level.toLevel(sArg);
    }

    /**
     * @deprecated Please use the {@link Level#toLevel(int)} method instead.   
     * 
     * @param val an integer to convert to a Priority
     * @return the corresponding Priority
     */
    @Deprecated
    public static Priority toPriority(int val) {
        return toPriority(val, Priority.DEBUG);
    }

    /**
     * @deprecated Please use the {@link Level#toLevel(int, Level)} method instead.   
     * 
     * @param val an integer value
     * @param defaultPriority a default priority value
     * @return corresponding Priority value
     */
    @Deprecated
    public static Priority toPriority(int val, Priority defaultPriority) {
        return Level.toLevel(val, (Level) defaultPriority);
    }

    /**
     * @deprecated Please use the {@link Level#toLevel(String, Level)} method instead.   
     * @param sArg string value
     * @param defaultPriority a default Priority
     * @return a Priority
     */
    @Deprecated
    public static Priority toPriority(String sArg, Priority defaultPriority) {
        return Level.toLevel(sArg, (Level) defaultPriority);
    }
}
