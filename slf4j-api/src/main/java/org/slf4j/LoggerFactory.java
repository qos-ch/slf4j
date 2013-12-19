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
package org.slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.helpers.SubstituteLoggerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * The <code>LoggerFactory</code> is a utility class producing Loggers for
 * various logging APIs, most notably for log4j, logback and JDK 1.4 logging.
 * Other implementations such as {@link org.slf4j.impl.NOPLogger NOPLogger} and
 * {@link org.slf4j.impl.SimpleLogger SimpleLogger} are also supported.
 * <p/>
 * <p/>
 * <code>LoggerFactory</code> is essentially a wrapper around an
 * {@link ILoggerFactory} instance bound with <code>LoggerFactory</code> at
 * compile time.
 * <p/>
 * <p/>
 * Please note that all methods in <code>LoggerFactory</code> are static.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Robert Elliot
 */
public final class LoggerFactory {

  static final String CODES_PREFIX = "http://www.slf4j.org/codes.html";

  static final String NO_STATICLOGGERBINDER_URL = CODES_PREFIX + "#StaticLoggerBinder";
  static final String MULTIPLE_BINDINGS_URL = CODES_PREFIX + "#multiple_bindings";
  static final String NULL_LF_URL = CODES_PREFIX + "#null_LF";
  static final String VERSION_MISMATCH = CODES_PREFIX + "#version_mismatch";
  static final String SUBSTITUTE_LOGGER_URL = CODES_PREFIX + "#substituteLogger";

  static final String UNSUCCESSFUL_INIT_URL = CODES_PREFIX + "#unsuccessfulInit";
  static final String UNSUCCESSFUL_INIT_MSG = "org.slf4j.LoggerFactory could not be successfully initialized. See also "
          + UNSUCCESSFUL_INIT_URL;

  static final int UNINITIALIZED = 0;
  static final int ONGOING_INITIALIZATION = 1;
  static final int FAILED_INITIALIZATION = 2;
  static final int SUCCESSFUL_INITIALIZATION = 3;
  static final int NOP_FALLBACK_INITIALIZATION = 4;

  static int INITIALIZATION_STATE = UNINITIALIZED;
  static SubstituteLoggerFactory TEMP_FACTORY = new SubstituteLoggerFactory();
  static NOPLoggerFactory NOP_FALLBACK_FACTORY = new NOPLoggerFactory();

  /**
   * It is LoggerFactory's responsibility to track version changes and manage
   * the compatibility list.
   * <p/>
   * <p/>
   * It is assumed that all versions in the 1.6 are mutually compatible.
   */
  static private final String[] API_COMPATIBILITY_LIST = {"1.6", "1.7"};

  // private constructor prevents instantiation
  private LoggerFactory() {
  }

  /**
   * Force LoggerFactory to consider itself uninitialized.
   * <p/>
   * <p/>
   * This method is intended to be called by classes (in the same package) for
   * testing purposes. This method is internal. It can be modified, renamed or
   * removed at any time without notice.
   * <p/>
   * <p/>
   * You are strongly discouraged from calling this method in production code.
   */
  static void reset() {
    INITIALIZATION_STATE = UNINITIALIZED;
    TEMP_FACTORY = new SubstituteLoggerFactory();
  }

  private final static void performInitialization() {
    bind();
    if (INITIALIZATION_STATE == SUCCESSFUL_INITIALIZATION) {
      versionSanityCheck();
    }
  }

  private static boolean messageContainsOrgSlf4jImplStaticLoggerBinder(String msg) {
    if (msg == null)
      return false;
    if (msg.contains("org/slf4j/impl/StaticLoggerBinder"))
      return true;
    if (msg.contains("org.slf4j.impl.StaticLoggerBinder"))
      return true;
    return false;
  }

  private final static void bind() {
    try {
      Set<URL> staticLoggerBinderPathSet = findPossibleStaticLoggerBinderPathSet();
      reportMultipleBindingAmbiguity(staticLoggerBinderPathSet);
      // the next line does the binding
      StaticLoggerBinder.getSingleton();
      INITIALIZATION_STATE = SUCCESSFUL_INITIALIZATION;
      reportActualBinding(staticLoggerBinderPathSet);
      emitSubstituteLoggerWarning();
    } catch (NoClassDefFoundError ncde) {
      String msg = ncde.getMessage();
      if (messageContainsOrgSlf4jImplStaticLoggerBinder(msg)) {
        INITIALIZATION_STATE = NOP_FALLBACK_INITIALIZATION;
        Util.report("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
        Util.report("Defaulting to no-operation (NOP) logger implementation");
        Util.report("See " + NO_STATICLOGGERBINDER_URL
                + " for further details.");
      } else {
        failedBinding(ncde);
        throw ncde;
      }
    } catch (java.lang.NoSuchMethodError nsme) {
      String msg = nsme.getMessage();
      if (msg != null && msg.contains("org.slf4j.impl.StaticLoggerBinder.getSingleton()")) {
        INITIALIZATION_STATE = FAILED_INITIALIZATION;
        Util.report("slf4j-api 1.6.x (or later) is incompatible with this binding.");
        Util.report("Your binding is version 1.5.5 or earlier.");
        Util.report("Upgrade your binding to version 1.6.x.");
      }
      throw nsme;
    } catch (Exception e) {
      failedBinding(e);
      throw new IllegalStateException("Unexpected initialization failure", e);
    }
  }

  static void failedBinding(Throwable t) {
    INITIALIZATION_STATE = FAILED_INITIALIZATION;
    Util.report("Failed to instantiate SLF4J LoggerFactory", t);
  }

  private final static void emitSubstituteLoggerWarning() {
    List loggerNameList = TEMP_FACTORY.getLoggerNameList();
    if (loggerNameList.isEmpty()) {
      return;
    }
    Util.report("The following loggers will not work because they were created");
    Util.report("during the default configuration phase of the underlying logging system.");
    Util.report("See also " + SUBSTITUTE_LOGGER_URL);
    for (Object loggerName : loggerNameList) {
      Util.report((String) loggerName);
    }
  }

  private final static void versionSanityCheck() {
    try {
      String requested = StaticLoggerBinder.REQUESTED_API_VERSION;

      boolean match = false;
      for (String compatVersion : API_COMPATIBILITY_LIST) {
        if (requested.startsWith(compatVersion)) {
          match = true;
        }
      }
      if (!match) {
        Util.report("The requested version " + requested
                + " by your slf4j binding is not compatible with "
                + Arrays.toString(API_COMPATIBILITY_LIST));
        Util.report("See " + VERSION_MISMATCH + " for further details.");
      }
    } catch (java.lang.NoSuchFieldError nsfe) {
      // given our large user base and SLF4J's commitment to backward
      // compatibility, we cannot cry here. Only for implementations
      // which willingly declare a REQUESTED_API_VERSION field do we
      // emit compatibility warnings.
    } catch (Throwable e) {
      // we should never reach here
      Util.report("Unexpected problem occured during version sanity check", e);
    }
  }

  // We need to use the name of the StaticLoggerBinder class, but we can't reference
  // the class itself.
  private static String STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";

  private static Set<URL> findPossibleStaticLoggerBinderPathSet() {
    // use Set instead of list in order to deal with  bug #138
    // LinkedHashSet appropriate here because it preserves insertion order during iteration
    Set<URL> staticLoggerBinderPathSet = new LinkedHashSet<URL>();
    try {
      ClassLoader loggerFactoryClassLoader = LoggerFactory.class
              .getClassLoader();
      Enumeration<URL> paths;
      if (loggerFactoryClassLoader == null) {
        paths = ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH);
      } else {
        paths = loggerFactoryClassLoader
                .getResources(STATIC_LOGGER_BINDER_PATH);
      }
      while (paths.hasMoreElements()) {
        URL path = paths.nextElement();
        staticLoggerBinderPathSet.add(path);
      }
    } catch (IOException ioe) {
      Util.report("Error getting resources from path", ioe);
    }
    return staticLoggerBinderPathSet;
  }

  private static boolean isAmbiguousStaticLoggerBinderPathSet(Set<URL> staticLoggerBinderPathSet) {
    return staticLoggerBinderPathSet.size() > 1;
  }

  /**
   * Prints a warning message on the console if multiple bindings were found on the class path.
   * No reporting is done otherwise.
   *
   */
  private static void reportMultipleBindingAmbiguity(Set<URL> staticLoggerBinderPathSet) {
    if (isAmbiguousStaticLoggerBinderPathSet(staticLoggerBinderPathSet)) {
      Util.report("Class path contains multiple SLF4J bindings.");
      for (URL path : staticLoggerBinderPathSet) {
        Util.report("Found binding in [" + path + "]");
      }
      Util.report("See " + MULTIPLE_BINDINGS_URL + " for an explanation.");
    }
  }

  private static void reportActualBinding(Set<URL> staticLoggerBinderPathSet) {
    if (isAmbiguousStaticLoggerBinderPathSet(staticLoggerBinderPathSet)) {
      Util.report("Actual binding is of type ["+StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr()+"]");
    }
  }


  /**
   * Return a logger named according to the name parameter using the statically
   * bound {@link ILoggerFactory} instance.
   *
   * @param name The name of the logger.
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
   * @param clazz the returned logger will be named after clazz
   * @return logger
   */
  public static Logger getLogger(Class clazz) {
    return getLogger(clazz.getName());
  }

  /**
   * Return the {@link ILoggerFactory} instance in use.
   * <p/>
   * <p/>
   * ILoggerFactory instance is bound with this class at compile time.
   *
   * @return the ILoggerFactory instance in use
   */
  public static ILoggerFactory getILoggerFactory() {
    if (INITIALIZATION_STATE == UNINITIALIZED) {
      INITIALIZATION_STATE = ONGOING_INITIALIZATION;
      performInitialization();
    }
    switch (INITIALIZATION_STATE) {
      case SUCCESSFUL_INITIALIZATION:
        return StaticLoggerBinder.getSingleton().getLoggerFactory();
      case NOP_FALLBACK_INITIALIZATION:
        return NOP_FALLBACK_FACTORY;
      case FAILED_INITIALIZATION:
        throw new IllegalStateException(UNSUCCESSFUL_INIT_MSG);
      case ONGOING_INITIALIZATION:
        // support re-entrant behavior.
        // See also http://bugzilla.slf4j.org/show_bug.cgi?id=106
        return TEMP_FACTORY;
    }
    throw new IllegalStateException("Unreachable code");
  }
}
