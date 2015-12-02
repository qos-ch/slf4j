package org.slf4j.ext.mdc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by himavija on 11/17/15.
 * //TODO Must check that the annotated class has a private constructor.
 * //TODO Must check that the annotated class implements newInstance method. Refer org.slf4j.ext.mdc.RootNode.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RootPojo {
  String name();

  /**
   * Default is "."
   * @return
   */
  String separatorForFQN() default ".";
}
