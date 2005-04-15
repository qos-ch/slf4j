/* 
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.ch
 * 
 * All rights reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 * 
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

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
