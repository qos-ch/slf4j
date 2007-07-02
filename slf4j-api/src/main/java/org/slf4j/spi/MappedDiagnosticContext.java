package org.slf4j.spi;

public interface MappedDiagnosticContext {

  /**
   * Put a context value (the <code>val</code> parameter) as identified with
   * the <code>key</code> parameter into the current thread's context map.
   * 
   * <p>If the current thread does not have a context map it is created as a side
   * effect of this call.
   */
  public void put(String key, String val);

  /**
   * Get the context identified by the <code>key</code> parameter.
   * 
   * @return the string value identified by the <code>key</code> parameter.
   */
  public String get(String key);

  /**
   * Remove the the context identified by the <code>key</code> parameter.
   */
  public void remove(String key);

  /**
   * Clear all entries in the MDC.
   */
  public void clear();

}
