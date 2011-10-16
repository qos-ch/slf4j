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
package org.apache.log4j.spi;

import org.apache.log4j.*;

import java.util.Enumeration;

/**
 * A <code>LoggerRepository</code> is used to create and retrieve
 * <code>Loggers</code>. The relation between loggers in a repository
 * depends on the repository but typically loggers are arranged in a
 * named hierarchy.
 * <p/>
 * <p>In addition to the creational methods, a
 * <code>LoggerRepository</code> can be queried for existing loggers,
 * can act as a point of registry for events related to loggers.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since 1.2
 */
public interface LoggerRepository {

    /**
     * Add a {@link HierarchyEventListener} event to the repository.
     */
    public void addHierarchyEventListener(HierarchyEventListener listener);

    /**
     * Returns whether this repository is disabled for a given
     * level. The answer depends on the repository threshold and the
     * <code>level</code> parameter. See also {@link #setThreshold}
     * method.
     */
    boolean isDisabled(int level);

    /**
     * Set the repository-wide threshold. All logging requests below the
     * threshold are immediately dropped. By default, the threshold is
     * set to <code>Level.ALL</code> which has the lowest possible rank.
     */
    public void setThreshold(Level level);

    /**
     * Another form of {@link #setThreshold(Level)} accepting a string
     * parameter instead of a <code>Level</code>.
     */
    public void setThreshold(String val);

    public void emitNoAppenderWarning(Category cat);

    /**
     * Get the repository-wide threshold. See {@link
     * #setThreshold(Level)} for an explanation.
     */
    public Level getThreshold();

    public Logger getLogger(String name);

    public Logger getLogger(String name, LoggerFactory factory);

    public Logger getRootLogger();

    public
    abstract Logger exists(String name);

    public
    abstract void shutdown();

    public Enumeration getCurrentLoggers();

    /**
     * Deprecated. Please use {@link #getCurrentLoggers} instead.
     */
    public Enumeration getCurrentCategories();


    public
    abstract void fireAddAppenderEvent(Category logger, Appender appender);

    public
    abstract void resetConfiguration();

}
