/**
 * Copyright (c) 2004-2021 QOS.ch
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
package org.slf4j.jdk.platform.logging;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages instances of {@link SLF4JPlatformLogger}.
 * 
 * @since 1.3.0
 * @author Ceki
 *
 */
public class SLF4JPlatformLoggerFactory {
    ConcurrentMap<String, SLF4JPlatformLogger> loggerMap = new ConcurrentHashMap<>();
    
    /**
     * Return an appropriate {@link SLF4JPlatformLogger} instance by name.
     */
    public SLF4JPlatformLogger getLogger(String loggerName) {
        
        
        SLF4JPlatformLogger spla = loggerMap.get(loggerName);
        if (spla != null) {
            return spla;
        } else {
            Logger slf4jLogger = LoggerFactory.getLogger(loggerName);
            SLF4JPlatformLogger newInstance = new SLF4JPlatformLogger(slf4jLogger);
            SLF4JPlatformLogger oldInstance = loggerMap.putIfAbsent(loggerName, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }
}
