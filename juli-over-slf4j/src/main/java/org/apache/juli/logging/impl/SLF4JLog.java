package org.apache.juli.logging.impl;

import java.io.ObjectStreamException;
import java.io.Serializable;

import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLog implements Log, Serializable {
  private static final long serialVersionUID = -6717621459536721887L;

  private final String name;

  private transient Logger logger;

  SLF4JLog(final Logger logger) {
    this.name = logger.getName();
    this.logger = logger;
  }

  public boolean isDebugEnabled() {
    return this.logger.isDebugEnabled();
  }

  public boolean isErrorEnabled() {
    return this.logger.isErrorEnabled();
  }

  public boolean isFatalEnabled() {
    return this.logger.isErrorEnabled();
  }

  public boolean isInfoEnabled() {
    return this.logger.isErrorEnabled();
  }

  public boolean isTraceEnabled() {
    return this.logger.isTraceEnabled();
  }

  public boolean isWarnEnabled() {
    return this.logger.isWarnEnabled();
  }

  public void trace(final Object message) {
    this.logger.trace(String.valueOf(message));
  }

  public void trace(final Object message, final Throwable t) {
    this.logger.trace(String.valueOf(message), t);
  }

  public void debug(final Object message) {
    this.logger.debug(String.valueOf(message));
  }

  public void debug(final Object message, final Throwable t) {
    this.logger.debug(String.valueOf(message), t);
  }

  public void info(final Object message) {
    this.logger.info(String.valueOf(message));
  }

  public void info(final Object message, final Throwable t) {
    this.logger.info(String.valueOf(message), t);
  }

  public void warn(final Object message) {
    this.logger.warn(String.valueOf(message));
  }

  public void warn(final Object message, final Throwable t) {
    this.logger.warn(String.valueOf(message), t);
  }

  public void error(final Object message) {
    this.logger.error(String.valueOf(message));
  }

  public void error(final Object message, final Throwable t) {
    this.logger.error(String.valueOf(message), t);
  }

  public void fatal(final Object message) {
    this.logger.error(String.valueOf(message));
  }

  public void fatal(final Object message, final Throwable t) {
    this.logger.error(String.valueOf(message), t);
  }

  /**
   * Replace this instance with a homonymous (same name) logger returned by
   * LoggerFactory. Note that this method is only called during deserialization.
   * 
   * @return logger with same name as returned by LoggerFactory
   * @throws ObjectStreamException
   */
  protected Object readResolve() throws ObjectStreamException {
    final Logger logger = LoggerFactory.getLogger(this.name);
    return new SLF4JLog(logger);
  }
}
