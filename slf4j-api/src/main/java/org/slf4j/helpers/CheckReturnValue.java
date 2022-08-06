package org.slf4j.helpers;


import org.slf4j.Marker;

import java.lang.annotation.*;

/**
 * <p>Used to annotate methods in the {@link org.slf4j.spi.LoggingEventBuilder} interface
 * which return an instance of LoggingEventBuilder (usually as <code>this</code>). Such
 * methods should be followed by one of the terminating <code>log()</code> methods returning
 * <code>void</code>.</p>
 *
 * <p>Some IDEs such as IntelliJ IDEA support this annotation at compile time.</p>
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0-beta1
 */
@Documented
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckReturnValue {
}
