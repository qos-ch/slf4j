/*
 * Copyright (c) 2004-2005 SLF4J.ORG
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


// WARNING
// WARNING Modifications MUST be made to the original file found at
// WARNING $SLF4J_HOME/src/filtered-java/org/slf4j/LoggerFactory.java
// WARNING

/**
 * The <code>LoggerFactory</code> can produce Loggers for various logging APIs,
 * most notably for log4j, JDK 1.4 logging. Other implemenations such as
 * {@link org.slf4j.impl.NOPLogger NOPLogger} and
 * {@link org.slf4j.impl.SimpleLogger SimpleLogger} are also supported.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class LoggerFactory {
  static LoggerFactoryAdapter adapter;

  // 
  // WARNING Do not modify copies but the original in
  //         $SLF4J_HOME/src/filtered-java/org/slf4j/
  //
  static {
    String adapterClassStr = "org.slf4j.impl.@IMPL@LoggerFA";
    System.out.println("SLF4J built for " + adapterClassStr);

    adapter = getFactoryAdapterFromSystemProperties();

    // if could not get an adapter from the system properties,  bind statically
    if (adapter != null) {
       System.out.println("However, SLF4J will use ["+adapter.getClass().getName()
       		+ "] adapter from system properties.");
    } else {
      try {
        adapter = new org.slf4j.impl.@IMPL@LoggerFA();
      } catch (Exception e) {
        // we should never get here
        reportFailure(
          "Could not instantiate instance of class [" + adapterClassStr + "]",
          e);
      }
    }
  }

  /**
   * Fetch the appropriate adapter as intructed by the system propties.
   * 
   * @return The appropriate LoggerFactoryAdapter as directed from the 
   * system properties
   */
  private static LoggerFactoryAdapter getFactoryAdapterFromSystemProperties() {
    String faFactoryClassName = null;

    try {
      faFactoryClassName = System.getProperty(Constants.LOGGER_FA_FACTORY_PROPERTY);
      if (faFactoryClassName == null) {
        return null;
      }

      Class faFactoryClass = Class.forName(faFactoryClassName);
      Class[] EMPTY_CLASS_ARRAY = {  };
      java.lang.reflect.Method faFactoryMethod =
        faFactoryClass.getDeclaredMethod(
          Constants.FA_FACTORY_METHOD_NAME, EMPTY_CLASS_ARRAY);
      LoggerFactoryAdapter adapter =
        (LoggerFactoryAdapter) faFactoryMethod.invoke(null, null);
      return adapter;
    } catch (Throwable t) {
      if (faFactoryClassName == null) {
        reportFailure(
          "Failed to fetch " + Constants.LOGGER_FA_FACTORY_PROPERTY
          + " system property.", t);
      } else {
        reportFailure(
          "Failed to fetch LoggerFactoryAdapter using the "
          + faFactoryClassName + " class.", t);
      }
    }

    // we could not get an adapter
    return null;
  }

  static void reportFailure(String msg, Throwable t) {
    System.err.println(msg);
    System.err.println("Reported exception follows.");
    t.printStackTrace();
  }

  /**
   * Return a logger named according to the name parameter using the 
   * previously bound  {@link LoggerFactoryAdapter adapter}.
   * @param name The name of the logger.
   * @return logger
   */
  public static Logger getLogger(String name) {
    return adapter.getLogger(name);
  }

  public static Logger getLogger(String domainName, String subDomainName) {
    return adapter.getLogger(domainName, subDomainName);
  }

  public static Logger getLogger(Class clazz) {
    return adapter.getLogger(clazz.getName());
  }

  public static Logger getLogger(Class clazz, String subDomainName) {
    return adapter.getLogger(clazz.getName(), subDomainName);
  }
}
