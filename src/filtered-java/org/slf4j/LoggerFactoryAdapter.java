package org.slf4j;

/**
 * LoggerFactoryAdapter interface is used internally by {@link
 * LoggerFactory}.
 * 
 * <p>Only developers wishing to write new UGLI adapters need to worry
 * about this interface.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *
 */
public interface LoggerFactoryAdapter {
  
  /**
   * Return the appropriate named {@link ULogger} instance.
   */
  public ULogger getLogger(String name);
  
  /**
   * Return a {@link ULogger} instance in <code>domain</code>, <code>subDomain</code>. 
   *
   * @param domain
   * @param subDomain
   * @return ULogger instance
   */
  public ULogger getLogger(String domain, String subDomain);  
}
