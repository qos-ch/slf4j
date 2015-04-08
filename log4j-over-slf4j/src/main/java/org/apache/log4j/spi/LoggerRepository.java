/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

    public abstract Logger exists(String name);

    public abstract void shutdown();

    @SuppressWarnings("rawtypes")
    public Enumeration getCurrentLoggers();

    /**
     * Deprecated. Please use {@link #getCurrentLoggers} instead.
     */
    @SuppressWarnings("rawtypes")
    public Enumeration getCurrentCategories();

    public abstract void fireAddAppenderEvent(Category logger, Appender appender);

    public abstract void resetConfiguration();

}
