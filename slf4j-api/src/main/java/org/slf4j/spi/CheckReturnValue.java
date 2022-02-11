package org.slf4j.spi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An internal annotation to help static analysis tools like Errorprone to detect
 * non-terminated fluent methods calls. This class is not intended to publish
 * to SLF4J API users.
 *
 * @see <a href="https://errorprone.info/bugpattern/CheckReturnValue">ErrorProne check</a>
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@interface CheckReturnValue {}
