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

  static final String CODES_PREFIX = "http://www.slf4j.org/codes.html";
    
  static final String NO_STATICLOGGERBINDER_URL = CODES_PREFIX+"#StaticLoggerBinder";
  static final String MULTIPLE_BINDINGS_URL = CODES_PREFIX+"#multiple_bindings";
  static final String NULL_LF_URL = CODES_PREFIX+"#null_LF";
  static final String VERSION_MISMATCH = CODES_PREFIX+"#version_mismatch";
  static final String SUBSTITUTE_LOGGER_URL = CODES_PREFIX+"#substituteLogger";

  static final String UNSUCCESSFUL_INIT_URL = CODES_PREFIX+"#unsuccessfulInit";
  static final String UNSUCCESSFUL_INIT_MSG = "org.slf4j.LoggerFactory could not be successfully initialized. See also "
      + UNSUCCESSFUL_INIT_URL;

  static final int UNINITIALIZED = 0;
  static final int ONGOING_INITILIZATION = 1;
  static final int FAILED_INITILIZATION = 2;
  static final int SUCCESSFUL_INITILIZATION = 3;
  static final int NOP_FALLBACK_INITILIZATION = 4;

  static int INITIALIZATION_STATE = UNINITIALIZED;
  static SubstituteLoggerFactory TEMP_FACTORY = new SubstituteLoggerFactory();
  static NOPLoggerFactory NOP_FALLBACK_FACTORY = new NOPLoggerFactory();

  static final String STATIC_LOGGER_BINDER_CLASS_PROPERTY = "org.slf4j.binder.class";
  static volatile IStaticLoggerBinder staticLoggerBinder;

  /**
   * It is LoggerFactory's responsibility to track version changes and manage
   * the compatibility list.
   * 
   * <p>
   * It is assumed that all versions in the 1.6 are mutually compatible.
   * */
  static private final String[] API_COMPATIBILITY_LIST = new String[] { "1.6" };

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
    TEMP_FACTORY = new SubstituteLoggerFactory();
  }

  private final static void performInitialization() {
    checkBindProperty();
    singleImplementationSanityCheck();
    bind();
    if (INITIALIZATION_STATE == SUCCESSFUL_INITILIZATION) {
      versionSanityCheck();
   
    }
  }

  private static void checkBindProperty() {
    String className = System.getProperty(STATIC_LOGGER_BINDER_CLASS_PROPERTY, null);
    if (className != null) {
      try {
        staticLoggerBinder = (IStaticLoggerBinder)Class.forName(className).newInstance();
      } catch (Exception e) {
        failedBinding(e);
        throw new IllegalStateException("Could not load binding class: " + className, e);
      }
    }
  }

  private static boolean messageContainsOrgSlf4jImplStaticLoggerBinder(String msg) {
    if(msg == null)
      return false;
    if(msg.indexOf("org/slf4j/impl/StaticLoggerBinder") != -1)
      return true;
    if(msg.indexOf("org.slf4j.impl.StaticLoggerBinder") != -1)
      return true;
    return false;
  }

  private static final class StaticLoggerBinderImpl implements IStaticLoggerBinder {
    public ILoggerFactory getLoggerFactory() {
      return StaticLoggerBinder.getSingleton().getLoggerFactory();
    }

    public String getRequestedApiVersion() {
      return StaticLoggerBinder.REQUESTED_API_VERSION;
    }
  }

  private final static void bind() {
    try {
      if ( staticLoggerBinder == null ) {
        // the next line does the binding
        StaticLoggerBinder.getSingleton();
        staticLoggerBinder = new StaticLoggerBinderImpl();
        INITIALIZATION_STATE = SUCCESSFUL_INITILIZATION;
        emitSubstituteLoggerWarning();
      } else {
        INITIALIZATION_STATE = SUCCESSFUL_INITILIZATION;
      }
    } catch (NoClassDefFoundError ncde) {
      String msg = ncde.getMessage();
      if (messageContainsOrgSlf4jImplStaticLoggerBinder(msg)) {
        INITIALIZATION_STATE = NOP_FALLBACK_INITILIZATION;
        Util
            .report("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
        Util.report("Defaulting to no-operation (NOP) logger implementation");
        Util.report("See " + NO_STATICLOGGERBINDER_URL
            + " for further details.");
      } else {
        failedBinding(ncde);
        throw ncde;
      }
    } catch(java.lang.NoSuchMethodError nsme) {
      String msg = nsme.getMessage();
      if (msg != null && msg.indexOf("org.slf4j.impl.StaticLoggerBinder.getSingleton()") != -1) {
        INITIALIZATION_STATE = FAILED_INITILIZATION;
        Util.report("slf4j-api 1.6.x (or later) is incompatible with this binding.");
        Util.report("Your binding is version 1.5.5 or earlier.");
        Util.report("Upgrade your binding to version 1.6.x. or 2.0.x");
      }
      throw nsme;
    } catch (Exception e) {
      failedBinding(e);
      throw new IllegalStateException("Unexpected initialization failure", e);
    }
  }

  static void failedBinding(Throwable t) {
    INITIALIZATION_STATE = FAILED_INITILIZATION;
    Util.report("Failed to instantiate SLF4J LoggerFactory", t);
  }

  private final static void emitSubstituteLoggerWarning() {
    List loggerNameList = TEMP_FACTORY.getLoggerNameList();
    if (loggerNameList.size() == 0) {
      return;
    }
    Util
        .report("The following loggers will not work because they were created");
    Util
        .report("during the default configuration phase of the underlying logging system.");
    Util.report("See also " + SUBSTITUTE_LOGGER_URL);
    for (int i = 0; i < loggerNameList.size(); i++) {
      String loggerName = (String) loggerNameList.get(i);
      Util.report(loggerName);
    }
  }

  private final static void versionSanityCheck() {
    try {
      String requested = staticLoggerBinder.getRequestedApiVersion();

      boolean match = false;
      for (int i = 0; i < API_COMPATIBILITY_LIST.length; i++) {
        if (requested.startsWith(API_COMPATIBILITY_LIST[i])) {
          match = true;
        }
      }
      if (!match) {
        Util.report("The requested version " + requested
            + " by your slf4j binding is not compatible with "
            + Arrays.asList(API_COMPATIBILITY_LIST).toString());
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

  private static void singleImplementationSanityCheck() {
    try {
      ClassLoader loggerFactoryClassLoader = LoggerFactory.class
          .getClassLoader();
      Enumeration paths;
      if (loggerFactoryClassLoader == null) {
        paths = ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH);
      } else {
        paths = loggerFactoryClassLoader
            .getResources(STATIC_LOGGER_BINDER_PATH);
      }
      // use Set instead of list in order to deal with  bug #138
      // LinkedHashSet appropriate here because it preserves insertion order during iteration
      Set implementationSet = new LinkedHashSet();
      while (paths.hasMoreElements()) {
        URL path = (URL) paths.nextElement();
        implementationSet.add(path);
      }
      if (implementationSet.size() > 1) {
        Util.report("Class path contains multiple SLF4J bindings.");
        Iterator iterator = implementationSet.iterator();
        while(iterator.hasNext()) {
          URL path = (URL) iterator.next();
          Util.report("Found binding in [" + path + "]");
        }
        Util.report("See " + MULTIPLE_BINDINGS_URL + " for an explanation.");
      }
    } catch (IOException ioe) {
      Util.report("Error getting resources from path", ioe);
    }
  }

  /**
   * Return a logger named according to the name parameter using the statically
   * bound {@link ILoggerFactory} instance.
   * 
   * @param name
   *          The name of the logger.
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
   *          the returned logger will be named after clazz
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
      return staticLoggerBinder.getLoggerFactory();
    case NOP_FALLBACK_INITILIZATION:
      return NOP_FALLBACK_FACTORY;
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
