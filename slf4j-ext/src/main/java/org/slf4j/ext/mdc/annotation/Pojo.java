package org.slf4j.ext.mdc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A POJO is made up of one or more @Property.
 * Created by himavija on 10/9/15.
 * TODO: Annotation processor should check that this can only by applied to POJOs.
 * TODO: Annotation processor should check that the POJO this is applied has a no arg and copy constructor.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Pojo {
    String name();
}
