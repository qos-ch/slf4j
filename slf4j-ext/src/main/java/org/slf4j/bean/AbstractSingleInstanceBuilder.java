package org.slf4j.bean;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractSingleInstanceBuilder<T> implements
    BeanBuilder<T> {
  private final String provides;
  private final AtomicReference<T> beanRef;
  private final AtomicBoolean init;

  protected abstract T initialBeanState();

  protected AbstractSingleInstanceBuilder(Class provides) {
    this.provides = provides.getSimpleName();
    beanRef = new AtomicReference<T>(null);
    init = new AtomicBoolean(false);
  }

  private void init() {
    if (init.compareAndSet(false, true)) {
      beanRef.set(initialBeanState());
    }
  }

  protected T bean() {
    init();
    return checkNotNull(beanRef.get(), "%s has already been built", provides);
  }

  public final T build() {
    init();
    return checkNotNull(beanRef.getAndSet(null), "%s has already been built", provides);
  }

  private static <T> T checkNotNull(T ref, String message,
      Object... messageParams) {
    if (null == ref) {
      throw new NullPointerException(String.format(message, messageParams));
    }
    return ref;
  }
}
