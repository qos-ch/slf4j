package org.slf4j.ext.events;

/**
 * Created by himavija on 10/9/15.
 * TODO: Annotation processor should check that this can only by applied to POJOs.
 * TODO: Annotation processor should check that the POJO this is applied has a no arg and copy constructor.
 */
public @interface PropertyGroup {
    String name();
}
