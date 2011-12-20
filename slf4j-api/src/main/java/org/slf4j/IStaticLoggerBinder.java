package org.slf4j;

/**
 * Alternate method of specifying a logger binding. Set
 * the system property "org.slf4j.binder.class" to the FQCN of your
 * logger binding class
 */
public interface IStaticLoggerBinder {
  /**
   * Return the logger factory singleton
   *
   * @return factory
   */
  public ILoggerFactory getLoggerFactory();

  /**
   * Declare the version of the SLF4J API this implementation is compiled against.
   * The value of this field is usually modified with each release.
   *
   * @return version
   */
  public String getRequestedApiVersion();
}
