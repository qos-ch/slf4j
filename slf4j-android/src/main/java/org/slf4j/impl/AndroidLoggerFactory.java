/*
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
package org.slf4j.impl;

import android.util.Log;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * AndroidLoggerFactory is an implementation of {@link ILoggerFactory} returning
 * the appropriately named {@link AndroidLoggerFactory} instance.
 *
 * @author Andrey Korzhevskiy
 */
public class AndroidLoggerFactory implements ILoggerFactory {
    private final ConcurrentMap<String, Logger> loggerMap;

    static final int TAG_MAX_LENGTH = 23; // tag names cannot be longer on Android platform
    // see also android/system/core/include/cutils/property.h
    // and android/frameworks/base/core/jni/android_util_Log.cpp

    public AndroidLoggerFactory() {
        loggerMap = new ConcurrentHashMap<String, Logger>();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.slf4j.ILoggerFactory#getLogger(java.lang.String)
     */
    public Logger getLogger(String name) {
        final String passedName = name;
        name = forceValidName(passedName); // fix for bug #173
        if (!passedName.equals(name)) {
            Log.i(AndroidLoggerFactory.class.getSimpleName(),
                    "Logger name '" + passedName + "' exceeds maximum length of " + TAG_MAX_LENGTH +
                            " characters, using '" + name + "' instead.");
        }

        Logger logger = loggerMap.get(name);
        if (logger == null) {
            Logger newInstance = new AndroidLoggerAdapter(name);
            Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            logger = oldInstance == null ? newInstance : oldInstance;
        }
        return logger;
    }

    /**
     * Trim name in case it exceeds maximum length of {@value #TAG_MAX_LENGTH} characters.
     */
    private static String forceValidName(String name) {
        if (name != null && name.length() > TAG_MAX_LENGTH) {
            final StringTokenizer st = new StringTokenizer(name, ".");
            if (st.hasMoreTokens()) { // note that empty tokens are skipped, i.e., "aa..bb" has tokens "aa", "bb"
                final StringBuilder sb = new StringBuilder();
                String token;
                do {
                    token = st.nextToken();
                    if (token.length() == 1) { // token of one character appended as is
                        sb.append(token);
                        sb.append('.');
                    } else if (st.hasMoreTokens()) { // truncate all but the last token
                        sb.append(token.charAt(0));
                        sb.append("*.");
                    } else { // last token (usually class name) appended as is
                        sb.append(token);
                    }
                } while (st.hasMoreTokens());

                name = sb.toString();
            }

            // Either we had no useful dot location at all or name still too long.
            // Take leading part and append '*' to indicate that it was truncated
            if (name.length() > TAG_MAX_LENGTH) {
                name = name.substring(0, TAG_MAX_LENGTH - 1) + '*';
            }
        }
        return name;
    }
}
