package org.slf4j.profiler;

import org.slf4j.Logger;

public final class RegisteredProfiler
{
  private RegisteredProfiler() {
  }

  public static Profiler.Builder build(String name, Logger logger) {
    return Profiler.builder(name)
        .registerWith(ProfilerRegistry.getThreadContextInstance())
        .logger(logger);
  }

  public static Optional findProfiler(Object o) {
    if (null == o) {
      return new DefaultOptional("Profiler", null);
    }
    return findProfiler(o.getClass());
  }

  public static Optional findProfiler(Class clazz) {
    return findProfiler(clazz.getName());
  }

  public static Optional findProfiler(String name) {
    ProfilerRegistry profilerRegistry = ProfilerRegistry
        .getThreadContextInstance();
    Profiler delegate = profilerRegistry.get(name);

    return new DefaultOptional(name, delegate);
  }
}
