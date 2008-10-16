/* 
 * Copyright (c) 2004-2007 QOS.ch
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
 */

package org.slf4j;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.helpers.SubstituteLoggerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * The <code>LoggerFactory</code> is a utility class producing Loggers for
 * various logging APIs, most notably for log4j, logback and JDK 1.4 logging.
 * Other implementations such as {@link org.slf4j.impl.NOPLogger NOPLogger} and
 * {@link org.slf4j.impl.SimpleLogger SimpleLogger} are also supported.
 * 
 * <p>
 * <code>LoggerFactory</code> is essentially a wrapper around an
 * {@link ILoggerFactory} instance bound with <code>LoggerFactory</code> at
 * compile time.
 * 
 * <p>
 * Please note that all methods in <code>LoggerFactory</code> are static.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public final class LoggerFactory {

  static ILoggerFactory loggerFactory;

  static final String NO_STATICLOGGERBINDER_URL = "http://www.slf4j.org/codes.html#StaticLoggerBinder";
  static final String NULL_LF_URL = "http://www.slf4j.org/codes.html#null_LF";
  static final String VERSION_MISMATCH = "http://www.slf4j.org/codes.html#version_mismatch";
  static final String SUBSTITUTE_LOGGER_URL = "http://www.slf4j.org/codes.html#substituteLogger";

  static private final String EXPECTED_VERSION = "1.5.4";

  // private constructor prevents instantiation
  private LoggerFactory() {
  }

  static {
    staticInitialize();
    versionSanityCheck();
  }

  private final static void staticInitialize() {
    try {
      // support re-entrant behavior.
      // See also http://bugzilla.slf4j.org/show_bug.cgi?id=106
      List loggerNameList = new ArrayList();
      loggerFactory = new SubstituteLoggerFactory(loggerNameList);
      loggerFactory = StaticLoggerBinder.SINGLETON.getLoggerFactory();
      emitSubstitureLoggerWarning(loggerNameList);
    } catch (NoClassDefFoundError ncde) {
      loggerFactory = null; // undo SubstituteLoggerFactory assignment
      String msg = ncde.getMessage();
      if (msg != null && msg.indexOf("org/slf4j/impl/StaticLoggerBinder") != -1) {
        Util
            .reportFailure("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
        Util.reportFailure("See " + NO_STATICLOGGERBINDER_URL
            + " for further details.");

      }
      throw ncde;
    } catch (Exception e) {
      loggerFactory = null; // undo SubstituteLoggerFactory assignment
      // we should never get here
      Util.reportFailure("Failed to instantiate logger ["
          + StaticLoggerBinder.SINGLETON.getLoggerFactoryClassStr() + "]", e);
    }
  }

  private final static void emitSubstitureLoggerWarning(List loggerNameList) {
    if(loggerNameList.size() == 0) {
      return;
    }
    Util
        .reportFailure("The following loggers will not work becasue they were created");
    Util
        .reportFailure("during the default configuration phase of the underlying logging system.");
    Util.reportFailure("See also " + SUBSTITUTE_LOGGER_URL);
    for (int i = 0; i < loggerNameList.size(); i++) {
      String loggerName = (String) loggerNameList.get(i);
      Util.reportFailure(loggerName);
    }
  }

  private final static void versionSanityCheck() {
    try {
      String actualVer = StaticLoggerBinder.VERSION;
      if (!EXPECTED_VERSION.equals(actualVer)) {
        Util.reportFailure("The version " + actualVer
            + " of your slf4j-binding differs from " + EXPECTED_VERSION
            + ", the expected version.");
        Util.reportFailure("See " + VERSION_MISMATCH + " for further details.");
      }
    } catch (java.lang.NoSuchFieldError nsfe) {
      Util
          .reportFailure("The version of your slf4j-binding is probably older than 1.5.4, and differs from "
              + EXPECTED_VERSION + ", the expected version.");
      Util.reportFailure("See " + VERSION_MISMATCH + " for further details.");
    } catch (Throwable e) {
      Util
          .reportFailure("An unexpected problem occured while checking the version of your slf4j-binding");
      e.printStackTrace();
    }
  }

  /**
   * Return a logger named according to the name parameter using the statically
   * bound {@link ILoggerFactory} instance.
   * 
   * @param name
   *                The name of the logger.
   * @return logger
   */
  public static Logger getLogger(String name) {
    if (loggerFactory == null) {
      throw new IllegalStateException(
          "Logging factory implementation cannot be null. See also "
              + NULL_LF_URL);
    }
    return loggerFactory.getLogger(name);
  }

  /**
   * Return a logger named corresponding to the class passed as parameter, using
   * the statically bound {@link ILoggerFactory} instance.
   * 
   * @param clazz
   *                the returned logger will be named after clazz
   * @return logger
   */
  public static Logger getLogger(Class clazz) {
    if (loggerFactory == null) {
      throw new IllegalStateException(
          "Logging factory implementation cannot be null. See also "
              + NULL_LF_URL);
    }
    return loggerFactory.getLogger(clazz.getName());
  }

  /**
   * Return the {@link ILoggerFactory} instance in use.
   * 
   * <p>
   * ILoggerFactory instance is bound with this class at compile time.
   * 
   * @return the ILoggerFactory instance in use
   */
  public static ILoggerFactory getILoggerFactory() {
    return loggerFactory;
  }
}
