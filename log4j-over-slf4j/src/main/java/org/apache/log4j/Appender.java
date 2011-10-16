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
package org.apache.log4j;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Implement this interface for your own strategies for outputting log
 * statements.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public interface Appender {

    /**
     * Add a filter to the end of the filter list.
     *
     * @since 0.9.0
     */
    void addFilter(Filter newFilter);

    /**
     * Returns the head Filter. The Filters are organized in a linked list
     * and so all Filters on this Appender are available through the result.
     *
     * @return the head Filter or null, if no Filters are present
     * @since 1.1
     */
    public Filter getFilter();

    /**
     * Clear the list of filters by removing all the filters in it.
     *
     * @since 0.9.0
     */
    public void clearFilters();

    /**
     * Release any resources allocated within the appender such as file
     * handles, network connections, etc.
     * <p/>
     * <p>It is a programming error to append to a closed appender.
     *
     * @since 0.8.4
     */
    public void close();

    /**
     * Log in <code>Appender</code> specific way. When appropriate,
     * Loggers will call the <code>doAppend</code> method of appender
     * implementations in order to log.
     */
    public void doAppend(LoggingEvent event);


    /**
     * Get the name of this appender. The name uniquely identifies the
     * appender.
     */
    public String getName();


    /**
     * Set the {@link ErrorHandler} for this appender.
     *
     * @since 0.9.0
     */
    public void setErrorHandler(ErrorHandler errorHandler);

    /**
     * Returns the {@link ErrorHandler} for this appender.
     *
     * @since 1.1
     */
    public ErrorHandler getErrorHandler();

    /**
     * Set the {@link Layout} for this appender.
     *
     * @since 0.8.1
     */
    public void setLayout(Layout layout);

    /**
     * Returns this appenders layout.
     *
     * @since 1.1
     */
    public Layout getLayout();


    /**
     * Set the name of this appender. The name is used by other
     * components to identify this appender.
     *
     * @since 0.8.1
     */
    public void setName(String name);

    /**
     * Configurators call this method to determine if the appender
     * requires a layout. If this method returns <code>true</code>,
     * meaning that layout is required, then the configurator will
     * configure an layout using the configuration information at its
     * disposal.  If this method returns <code>false</code>, meaning that
     * a layout is not required, then layout configuration will be
     * skipped even if there is available layout configuration
     * information at the disposal of the configurator..
     * <p/>
     * <p>In the rather exceptional case, where the appender
     * implementation admits a layout but can also work without it, then
     * the appender should return <code>true</code>.
     *
     * @since 0.8.4
     */
    public boolean requiresLayout();
}
