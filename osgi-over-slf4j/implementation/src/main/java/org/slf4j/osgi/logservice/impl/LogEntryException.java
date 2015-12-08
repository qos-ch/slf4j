/**
 * Copyright (c) 2015 QOS.ch
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
 */

package org.slf4j.osgi.logservice.impl;

import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.PackageAdmin;

/**
 * Contains the details of an Exception type logged from another bundle.
 * <p/>
 * Reference: Section 101.5 Log Entry in <a href="https://osgi.org/download/r4v42/r4.cmpn.pdf">OSGi R4.2 Compendium</a>
 *
 * @author Matt Bishop
 */
public class LogEntryException extends Exception {

    private final String name;
    private final String message;
    private final String localizedMessage;


    static Throwable from(Throwable exception, PackageAdmin packageAdmin) {
        Bundle bundle = packageAdmin.getBundle(exception.getClass());
        if (bundle == null) {
            //null bundle means system loaded--not from a bundle. OK to use as-is
            return exception;
        }
        //Came from a Bundle classlosader; make a LogEntryException to stand in it's place. See Section 101.5
        return new LogEntryException(exception, packageAdmin);
    }


    private LogEntryException(Throwable exception, PackageAdmin packageAdmin) {
        name = exception.getClass().getName();
        message = exception.getMessage();
        localizedMessage = exception.getLocalizedMessage();

        setStackTrace(exception.getStackTrace());

        Throwable cause = exception.getCause();
        if (cause != null) {
            cause = from(cause, packageAdmin);
            initCause(cause);
        }
    }

    public String getOriginalClassName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    @Override
    public String toString() {
        return name + ": " + localizedMessage;
    }
}
