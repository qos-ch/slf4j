package org.slf4j.spi;

/**
 * This interface abstracts the service offered by various MDC
 * implementations.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 1.4.1
 */
public interface MDCAdapter {

  /**
   * Put a context value (the <code>val</code> parameter) as identified with
   * the <code>key</code> parameter into the current thread's context map. 
   * The <code>key</code> parameter cannot be null. The code>val</code> parameter 
   * can be null only if the underlying implementation supports it.
   * 
   * <p>If the current thread does not have a context map it is created as a side
   * effect of this call.
   */
  public void put(String key, String val);

  /**
   * Get the context identified by the <code>key</code> parameter.
   * The <code>key</code> parameter cannot be null.
   * 
   * @return the string value identified by the <code>key</code> parameter.
   */
  public String get(String key);

  /**
   * Remove the the context identified by the <code>key</code> parameter. 
   * The <code>key</code> parameter cannot be null. 
   * 
   * <p>
   * This method does nothing if there is no previous value 
   * associated with <code>key</code>.
   */
  public void remove(String key);

  /**
   * Clear all entries in the MDC.
   */
  public void clear();

}
