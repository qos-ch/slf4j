package org.slf4j;


/**
 * 
 * The main user inteface to logging. It is expected that logging
 * takes places through concerete implemetations of the ULogger
 * interface.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public interface ULogger {

  /**
   * Is the logger instance enabled for the DEBUG level?
   * @return 
   */
  public boolean isDebugEnabled();
  
  /**
   * Log a message object with the DEBUG level. 
   * @param msg - the message object to be logged
   */
  public void debug(Object msg);
  
  
  /**
   * Log a parameterized message object at the DEBUG level. 
   * 
   * <p>This form is useful in avoiding the superflous object creation
   * problem when invoking this method while it is disabled.
   * </p>
   * @param parameterizedMsg - the parameterized message object
   * @param param1 - the parameter 
   */
  public void debug(Object parameterizedMsg, Object param1);
  
  /**
   * Log a parameterized message object at the DEBUG level. 
   * 
   * <p>This form is useful in avoiding the superflous object creation
   * problem when invoking this method while it is disabled.
   * </p>
   * @param parameterizedMsg - the parameterized message object
   * @param param1 - the first parameter 
   * @param param2 - the second parameter 
   */
  public void debug(String parameterizedMsg, Object param1, Object param2);
  public void debug(Object msg, Throwable t);


  public boolean isInfoEnabled();
  public void info(Object msg);
  public void info(Object parameterizedMsg, Object param1);
  public void info(String parameterizedMsg, Object param1, Object param2);
  public void info(Object msg, Throwable t);


  public boolean isWarnEnabled();
  public void warn(Object msg);
  public void warn(Object parameterizedMsg, Object param1);
  public void warn(String parameterizedMsg, Object param1, Object param2);
  public void warn(Object msg, Throwable t);


  public boolean isErrorEnabled();
  public void error(Object msg);
  public void error(Object parameterizedMsg, Object param1);
  public void error(String parameterizedMsg, Object param1, Object param2);
  public void error(Object msg, Throwable t);

}
