package org.slf4j.ext.events;

/**
 * Created by himavija on 10/9/15.
 * TODO: Annotation processor should check that this can only by applied to primitive types, list, set, map, jsonobjects.
 *       If not then to POJOs that themselves have child @property members.
 */
public @interface Property {
    boolean dontLogIfDefault() default false;
    String name();
}
