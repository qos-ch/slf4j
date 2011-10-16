/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.apache.log4j.helpers;

/**
   This class used to output log statements from within the log4j package.

   <p>Log4j components cannot make log4j logging calls. However, it is
   sometimes useful for the user to learn about what log4j is
   doing. You can enable log4j internal logging by defining the
   <b>log4j.configDebug</b> variable.

   <p>All log4j internal debug calls go to <code>System.out</code>
   where as internal error messages are sent to
   <code>System.err</code>. All internal messages are prepended with
   the string "log4j: ".

   @since 0.8.2
   @author Ceki G&uuml;lc&uuml;
*/
public class LogLog {

  /**
     Defining this value makes log4j print log4j-internal debug
     statements to <code>System.out</code>.

    <p> The value of this string is <b>log4j.debug</b>.

    <p>Note that the search for all option names is case sensitive.  */
  public static final String DEBUG_KEY="log4j.debug";


  /**
     Defining this value makes log4j components print log4j-internal
     debug statements to <code>System.out</code>.

    <p> The value of this string is <b>log4j.configDebug</b>.

    <p>Note that the search for all option names is case sensitive.

    @deprecated Use {@link #DEBUG_KEY} instead.
  */
  public static final String CONFIG_DEBUG_KEY="log4j.configDebug";

  protected static boolean debugEnabled = false;

  /**
     In quietMode not even errors generate any output.
   */
  private static boolean quietMode = false;

  private static final String PREFIX = "log4j: ";
  private static final String ERR_PREFIX = "log4j:ERROR ";
  private static final String WARN_PREFIX = "log4j:WARN ";

  static {
  }

  /**
     Allows to enable/disable log4j internal logging.
   */
  static
  public
  void setInternalDebugging(boolean enabled) {
    debugEnabled = enabled;
  }

  /**
     This method is used to output log4j internal debug
     statements. Output goes to <code>System.out</code>.
  */
  public
  static
  void debug(String msg) {
    if(debugEnabled && !quietMode) {
      System.out.println(PREFIX+msg);
    }
  }

  /**
     This method is used to output log4j internal debug
     statements. Output goes to <code>System.out</code>.
  */
  public
  static
  void debug(String msg, Throwable t) {
    if(debugEnabled && !quietMode) {
      System.out.println(PREFIX+msg);
      if(t != null)
	t.printStackTrace(System.out);
    }
  }


  /**
     This method is used to output log4j internal error
     statements. There is no way to disable error statements.
     Output goes to <code>System.err</code>.
  */
  public
  static
  void error(String msg) {
    if(quietMode)
      return;
    System.err.println(ERR_PREFIX+msg);
  }

  /**
     This method is used to output log4j internal error
     statements. There is no way to disable error statements.
     Output goes to <code>System.err</code>.
  */
  public
  static
  void error(String msg, Throwable t) {
    if(quietMode)
      return;

    System.err.println(ERR_PREFIX+msg);
    if(t != null) {
      t.printStackTrace();
    }
  }

  /**
     In quite mode no LogLog generates strictly no output, not even
     for errors.

     @param quietMode A true for not
  */
  public
  static
  void setQuietMode(boolean quietMode) {
    LogLog.quietMode = quietMode;
  }

  /**
     This method is used to output log4j internal warning
     statements. There is no way to disable warning statements.
     Output goes to <code>System.err</code>.  */
  public
  static
  void warn(String msg) {
    if(quietMode)
      return;

    System.err.println(WARN_PREFIX+msg);
  }

  /**
     This method is used to output log4j internal warnings. There is
     no way to disable warning statements.  Output goes to
     <code>System.err</code>.  */
  public
  static
  void warn(String msg, Throwable t) {
    if(quietMode)
      return;

    System.err.println(WARN_PREFIX+msg);
    if(t != null) {
      t.printStackTrace();
    }
  }
}
