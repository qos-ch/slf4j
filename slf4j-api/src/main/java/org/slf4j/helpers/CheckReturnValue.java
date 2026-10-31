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

package org.slf4j.helpers;


import org.slf4j.Marker;

import java.lang.annotation.*;

/**
 * <p>Used to annotate methods in the {@link org.slf4j.spi.LoggingEventBuilder} interface
 * which return an instance of LoggingEventBuilder (usually as <code>this</code>). Such
 * methods should be followed by one of the terminating <code>log()</code> methods returning
 * <code>void</code>.</p>
 * <p></p>
 * <p>IntelliJ IDEA supports a check for annotations named as <code>CheckReturnValue</code>
 * regardless of the containing package. For more information on this feature in IntelliJ IDEA,
 * select File &#8594; Setting &#8594; Editor &#8594; Inspections and then Java &#8594; Probable Bugs &#8594;
 * Result of method call ignored. </p>
 * <p></p>
 *
 * <p>As for Eclipse, this feature has been requested in
 * <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=572496">bug 572496</a></p>
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0-beta1
 */
@Documented
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckReturnValue {
}
