package org.slf4j.ext.mdc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate property of a @Pojo.
 *
 * Created by himavija on 10/9/15.
 * TODO: Annotation processor should check that this can only by applied to primitive types, list, set, map, jsonobjects.
 *       If not then to POJOs that themselves have child @property members.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property {
    boolean dontLogIfDefault() default false;
    String name();
}
