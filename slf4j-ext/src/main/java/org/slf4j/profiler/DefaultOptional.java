package org.slf4j.profiler;

import org.slf4j.Logger;
import org.slf4j.profiler.logging.LoggerOutput;

final class DefaultOptional implements Optional {
  private final String name;
  private final Profiler delegate;

  public DefaultOptional(String name, Profiler delegate) {
    this.name = name;
    this.delegate = delegate;
  }

  public Profiler get() {
    return checkNotNull(delegate, "No Profiler found");
  }

  public Optional orFind(Object o) {
    return null == delegate ? RegisteredProfiler.findProfiler(o) : this;
  }

  public Optional orFind(Class clazz) {
    return null == delegate ? RegisteredProfiler.findProfiler(clazz) : this;
  }

  public Optional orFind(String name) {
    return null == delegate ? RegisteredProfiler.findProfiler(name) : this;
  }

  public Profiler.Builder orBuild(Logger logger) {
    return orBuild(this.name, logger);
  }

  public Profiler.Builder orBuild(String name, Logger logger) {
    return null == delegate ? RegisteredProfiler.build(name, logger)
        : new PassThroughBuilder(get());
  }

	public Profiler orNull()
	{
		return delegate;
	}

	private static <T> T checkNotNull(T ref, String message,
      Object... messageParams) {
    if (null == ref) {
      throw new NullPointerException(String.format(message, messageParams));
    }
    return ref;
  }

  private static final class PassThroughBuilder implements Profiler.Builder {
    private final Profiler profiler;

    private PassThroughBuilder(Profiler profiler) {
      this.profiler = profiler;
    }

    public Profiler.Builder registerWith(ProfilerRegistry profilerRegistry) {
      return this;
    }

    public Profiler.Builder logger(Logger logger) {
      return this;
    }

    public Profiler.Builder loggerOutput(LoggerOutput loggerOutput) {
      return this;
    }

    public Profiler.Builder markerName(String markerName) {
      return this;
    }

    public Profiler build() {
      return profiler;
    }
  }
}
