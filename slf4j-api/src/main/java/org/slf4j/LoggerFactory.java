/*
 * Copyright (c) 2004-2008 QOS.ch
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
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
 * @author Robert Elliot
 */
public final class LoggerFactory {

  static final String NO_STATICLOGGERBINDER_URL = "http://www.slf4j.org/codes.html#StaticLoggerBinder";
  static final String NULL_LF_URL = "http://www.slf4j.org/codes.html#null_LF";
  static final String VERSION_MISMATCH = "http://www.slf4j.org/codes.html#version_mismatch";
  static final String SUBSTITUTE_LOGGER_URL = "http://www.slf4j.org/codes.html#substituteLogger";

  static final String UNSUCCESSFUL_INIT_URL = "http://www.slf4j.org/codes.html#unsuccessfulInit";
  static final String UNSUCCESSFUL_INIT_MSG = "org.slf4j.LoggerFactory could not be successfully initialized. See also "
      + UNSUCCESSFUL_INIT_URL;

  static final int UNINITIALIZED = 0;
  static final int ONGOING_INITILIZATION = 1;
  static final int FAILED_INITILIZATION = 2;
  static final int SUCCESSFUL_INITILIZATION = 3;

  static final int GET_SINGLETON_INEXISTENT = 1;
  static final int GET_SINGLETON_EXISTS = 2;

  static int INITIALIZATION_STATE = UNINITIALIZED;
  static int GET_SINGLETON_METHOD = UNINITIALIZED;
  static SubstituteLoggerFactory TEMP_FACTORY = new SubstituteLoggerFactory();

  /**
   * It is our responsibility to track version changes and manage the
   * compatibility list.
   * 
   * <p>
   */
  static private final String[] API_COMPATIBILITY_LIST = new String[] {
      "1.5.5", "1.5.6", "1.5.7" };

  // private constructor prevents instantiation
  private LoggerFactory() {
  }

  /**
   * Force LoggerFactory to consider itself uninitialized.
   * 
   * <p>
   * This method is intended to be called by classes (in the same package) for
   * testing purposes. This method is internal. It can be modified, renamed or
   * removed at any time without notice.
   * 
   * <p>
   * You are strongly discouraged from calling this method in production code.
   */
  static void reset() {
    INITIALIZATION_STATE = UNINITIALIZED;
    GET_SINGLETON_METHOD = UNINITIALIZED;
    TEMP_FACTORY = new SubstituteLoggerFactory();
  }

  private final static void performInitialization() {
    bind();
    versionSanityCheck();
    singleImplementationSanityCheck();

  }

  private final static void bind() {
    try {
      // the next line does the binding
      getSingleton();
      INITIALIZATION_STATE = SUCCESSFUL_INITILIZATION;
      emitSubstitureLoggerWarning();
    } catch (NoClassDefFoundError ncde) {
      INITIALIZATION_STATE = FAILED_INITILIZATION;
      String msg = ncde.getMessage();
      if (msg != null && msg.indexOf("org/slf4j/impl/StaticLoggerBinder") != -1) {
        Util
            .reportFailure("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
        Util.reportFailure("See " + NO_STATICLOGGERBINDER_URL
            + " for further details.");

      }
      throw ncde;
    } catch (Exception e) {
      INITIALIZATION_STATE = FAILED_INITILIZATION;
      // we should never get here
      Util.reportFailure("Failed to instantiate logger ["
          + getSingleton().getLoggerFactoryClassStr() + "]", e);
    }
  }

  private final static void emitSubstitureLoggerWarning() {
    List loggerNameList = TEMP_FACTORY.getLoggerNameList();
    if (loggerNameList.size() == 0) {
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
      String requested = StaticLoggerBinder.REQUESTED_API_VERSION;

      boolean match = false;
      for (int i = 0; i < API_COMPATIBILITY_LIST.length; i++) {
        if (API_COMPATIBILITY_LIST[i].equals(requested)) {
          match = true;
        }
      }
      if (!match) {
        Util.reportFailure("The requested version " + requested
            + " by your slf4j binding is not compatible with "
            + Arrays.toString(API_COMPATIBILITY_LIST));
        Util.reportFailure("See " + VERSION_MISMATCH + " for further details.");
      }
    } catch (java.lang.NoSuchFieldError nsfe) {
      // given our large user base and SLF4J's commitment to backward
      // compatibility, we cannot cry here. Only for implementations
      // which willingly declare a REQUESTED_API_VERSION field do we
      // emit compatibility warnings.
    } catch (Throwable e) {
      // we should never reach here
      Util.reportFailure(
          "Unexpected problem occured during version sanity check", e);
    }
  }

  // We need to use the name of the StaticLoggerBinder class, we can't reference
  // the class itseld.
  private static String STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";

  private static void singleImplementationSanityCheck() {
    try {
      Enumeration paths = LoggerFactory.class.getClassLoader().getResources(
          STATIC_LOGGER_BINDER_PATH);
      List implementationList = new ArrayList();
      while (paths.hasMoreElements()) {
        URL path = (URL) paths.nextElement();
        implementationList.add(path);
      }
      if (implementationList.size() > 1) {
        Util
            .reportFailure("ClassPath contains more than one SLF4J implementation.");
        for(int i = 0; i < implementationList.size(); i++) {
          Util.reportFailure("Found binding under ["+implementationList.get(i)+"]");
        }
        Util.reportFailure("Will pick up one binding at random.");
      }
    } catch (IOException ioe) {
      Util.reportFailure("Error getting resources from path", ioe);
    }
  }

  private final static StaticLoggerBinder getSingleton() {
    if (GET_SINGLETON_METHOD == GET_SINGLETON_INEXISTENT) {
      return StaticLoggerBinder.SINGLETON;
    }

    if (GET_SINGLETON_METHOD == GET_SINGLETON_EXISTS) {
      return StaticLoggerBinder.getSingleton();
    }

    try {
      StaticLoggerBinder singleton = StaticLoggerBinder.getSingleton();
      GET_SINGLETON_METHOD = GET_SINGLETON_EXISTS;
      return singleton;
    } catch (NoSuchMethodError nsme) {
      GET_SINGLETON_METHOD = GET_SINGLETON_INEXISTENT;
      return StaticLoggerBinder.SINGLETON;
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
    ILoggerFactory iLoggerFactory = getILoggerFactory();
    return iLoggerFactory.getLogger(name);
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
    return getLogger(clazz.getName());
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
    if (INITIALIZATION_STATE == UNINITIALIZED) {
      INITIALIZATION_STATE = ONGOING_INITILIZATION;
      performInitialization();

    }
    switch (INITIALIZATION_STATE) {
    case SUCCESSFUL_INITILIZATION:
      return getSingleton().getLoggerFactory();
    case FAILED_INITILIZATION:
      throw new IllegalStateException(UNSUCCESSFUL_INIT_MSG);
    case ONGOING_INITILIZATION:
      // support re-entrant behavior.
      // See also http://bugzilla.slf4j.org/show_bug.cgi?id=106
      return TEMP_FACTORY;
    }
    throw new IllegalStateException("Unreachable code");
  }
}
