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
package org.slf4j;

import java.io.Closeable;
import java.util.Deque;
import java.util.Map;

import org.slf4j.helpers.*;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * This class hides and serves as a substitute for the underlying logging
 * system's MDC implementation.
 * 
 * <p>
 * If the underlying logging system offers MDC functionality, then SLF4J's MDC,
 * i.e. this class, will delegate to the underlying system's MDC. Note that at
 * this time, only two logging systems, namely log4j and logback, offer MDC
 * functionality. For java.util.logging which does not support MDC,
 * {@link BasicMDCAdapter} will be used. For other systems, i.e. slf4j-simple
 * and slf4j-nop, {@link NOPMDCAdapter} will be used.
 *
 * <p>
 * Thus, as a SLF4J user, you can take advantage of MDC in the presence of log4j,
 * logback, or java.util.logging, but without forcing these systems as
 * dependencies upon your users.
 * 
 * <p>
 * For more information on MDC please see the <a
 * href="http://logback.qos.ch/manual/mdc.html">chapter on MDC</a> in the
 * logback manual.
 * 
 * <p>
 * Please note that all methods in this class are static.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 1.4.1
 */
public class MDC {

    static final String NULL_MDCA_URL = "http://www.slf4j.org/codes.html#null_MDCA";
    private static final String MDC_ADAPTER_CANNOT_BE_NULL_MESSAGE = "MDCAdapter cannot be null. See also " + NULL_MDCA_URL;
    static final String NO_STATIC_MDC_BINDER_URL = "http://www.slf4j.org/codes.html#no_static_mdc_binder";
    static MDCAdapter MDC_ADAPTER;

    /**
     * An adapter to remove the key when done.
     */
    public static class MDCCloseable implements Closeable {
        private final String key;

        private MDCCloseable(String key) {
            this.key = key;
        }

        public void close() {
            MDC.remove(this.key);
        }
    }

    private MDC() {
    }

    private static MDCAdapter getMDCAdapterGivenByProvider() {
        SLF4JServiceProvider provider = LoggerFactory.getProvider();
        if(provider != null) {
            // If you wish to change the mdc adapter, setting the MDC.MDCAdapter variable might be insufficient.
            // Keep in mind that the provider *might* perform additional internal mdcAdapter assignments that
            // you would also need to replicate/adapt.

            // obtain and attach the MDCAdapter from the provider

            final MDCAdapter anAdapter = provider.getMDCAdapter();
            emitTemporaryMDCAdapterWarningIfNeeded(provider);
            return anAdapter;
        } else {
            Reporter.error("Failed to find provider.");
            Reporter.error("Defaulting to no-operation MDCAdapter implementation.");
            return new NOPMDCAdapter();
        }
    }

    private static void emitTemporaryMDCAdapterWarningIfNeeded(SLF4JServiceProvider provider) {
        boolean isSubstitute = provider instanceof SubstituteServiceProvider;
        if(isSubstitute) {
            Reporter.info("Temporary mdcAdapter given by SubstituteServiceProvider.");
            Reporter.info("This mdcAdapter will be replaced after backend initialization has completed.");
        }
    }

    /**
     * Put a diagnostic context value (the <code>val</code> parameter) as identified with the
     * <code>key</code> parameter into the current thread's diagnostic context map. The
     * <code>key</code> parameter cannot be null. The <code>val</code> parameter
     * can be null only if the underlying implementation supports it.
     * 
     * <p>
     * This method delegates all work to the MDC of the underlying logging system.
     *
     * @param key non-null key 
     * @param val value to put in the map
     * 
     * @throws IllegalArgumentException
     *           in case the "key" parameter is null
     */
    public static void put(String key, String val) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_ADAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        getMDCAdapter().put(key, val);
    }

    /**
     * Put a diagnostic context value (the <code>val</code> parameter) as identified with the
     * <code>key</code> parameter into the current thread's diagnostic context map. The
     * <code>key</code> parameter cannot be null. The <code>val</code> parameter
     * can be null only if the underlying implementation supports it.
     *
     * <p>
     * This method delegates all work to the MDC of the underlying logging system.
     * <p>
     * This method return a <code>Closeable</code> object who can remove <code>key</code> when
     * <code>close</code> is called.
     *
     * <p>
     * Useful with Java 7 for example :
     * <code>
     *   try(MDC.MDCCloseable closeable = MDC.putCloseable(key, value)) {
     *     ....
     *   }
     * </code>
     *
     * @param key non-null key
     * @param val value to put in the map
     * @return a <code>Closeable</code> who can remove <code>key</code> when <code>close</code>
     * is called.
     *
     * @throws IllegalArgumentException
     *           in case the "key" parameter is null
     */
    public static MDCCloseable putCloseable(String key, String val) throws IllegalArgumentException {
        put(key, val);
        return new MDCCloseable(key);
    }

    /**
     * Get the diagnostic context identified by the <code>key</code> parameter. The
     * <code>key</code> parameter cannot be null.
     * 
     * <p>
     * This method delegates all work to the MDC of the underlying logging system.
     *
     * @param key a key
     * @return the string value identified by the <code>key</code> parameter.
     * @throws IllegalArgumentException
     *           in case the "key" parameter is null
     */
    public static String get(String key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }

        mdcAdapterNullCheck();
        return getMDCAdapter().get(key);
    }

    /**
     * Remove the diagnostic context identified by the <code>key</code> parameter using
     * the underlying system's MDC implementation. The <code>key</code> parameter
     * cannot be null. This method does nothing if there is no previous value
     * associated with <code>key</code>.
     *
     * @param key  a key
     * @throws IllegalArgumentException
     *           in case the "key" parameter is null
     */
    public static void remove(String key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }

        mdcAdapterNullCheck();
        getMDCAdapter().remove(key);
    }

    /**
     * Clear all entries in the MDC of the underlying implementation.
     */
    public static void clear() {
        mdcAdapterNullCheck();
        getMDCAdapter().clear();
    }

    /**
     * Return a copy of the current thread's context map, with keys and values of
     * type String. Returned value may be null.
     * 
     * @return A copy of the current thread's context map. May be null.
     * @since 1.5.1
     */
    public static Map<String, String> getCopyOfContextMap() {
        mdcAdapterNullCheck();
        return getMDCAdapter().getCopyOfContextMap();
    }

    /**
     * Set the current thread's context map by first clearing any existing map and
     * then copying the map passed as parameter. The context map passed as
     * parameter must only contain keys and values of type String.
     * 
     * Null valued argument is allowed (since SLF4J version 2.0.0).
     * 
     * @param contextMap
     *          must contain only keys and values of type String
     * @since 1.5.1
     */
    public static void setContextMap(Map<String, String> contextMap) {
        mdcAdapterNullCheck();
        getMDCAdapter().setContextMap(contextMap);
    }

    /**
     * Returns the MDCAdapter instance currently in use.
     *
     * Since 2.0.17, if the MDCAdapter instance is null, then this method set it to use
     * the adapter returned by the SLF4JProvider. However, in the vast majority of cases
     * the MDCAdapter will be set earlier (during initialization) by {@link LoggerFactory}.
     *
     * @return the MDcAdapter instance currently in use.
     * @since 1.4.2
     */
    public static MDCAdapter getMDCAdapter() {
        if(MDC_ADAPTER == null) {
            MDC_ADAPTER = getMDCAdapterGivenByProvider();
        }
        return MDC_ADAPTER;
    }

    /**
     * Set MDCAdapter instance to use.
     *
     * @since 2.0.17
     */
    static void setMDCAdapter(MDCAdapter anMDCAdapter) {
        if(anMDCAdapter == null) {
            throw new IllegalStateException(MDC_ADAPTER_CANNOT_BE_NULL_MESSAGE);
        }
        MDC_ADAPTER = anMDCAdapter;
    }

    /**
     * Push a value into the deque(stack) referenced by 'key'.
     *      
     * @param key identifies the appropriate stack
     * @param value the value to push into the stack
     * @since 2.0.0
     */
    static public void pushByKey(String key, String value) {
        mdcAdapterNullCheck();
        getMDCAdapter().pushByKey(key, value);
    }
    
    /**
     * Pop the <b>stack</b> referenced by 'key' and return the value possibly null.
     * 
     * @param key identifies the deque(stack)
     * @return the value just popped. May be null/
     * @since 2.0.0
     */
    static public String popByKey(String key) {
        mdcAdapterNullCheck();
        return getMDCAdapter().popByKey(key);
    }

    /**
     * Returns a copy of the <b>deque(stack)</b> referenced by 'key'. May be null.
     * 
     * @param key identifies the  stack
     * @return copy of stack referenced by 'key'. May be null.
     * 
     * @since 2.0.0
     */
    public Deque<String>  getCopyOfDequeByKey(String key) {
        mdcAdapterNullCheck();
        return getMDCAdapter().getCopyOfDequeByKey(key);
    }

    private static void mdcAdapterNullCheck() {
        if (getMDCAdapter() == null) {
            throw new IllegalStateException(MDC_ADAPTER_CANNOT_BE_NULL_MESSAGE);
        }
    }
}
