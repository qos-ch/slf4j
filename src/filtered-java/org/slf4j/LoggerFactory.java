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

// WARNING
// WARNING Modifications MUST be made to the original file found at
// WARNING $SLF4J_HOME/src/filtered-java/org/slf4j/LoggerFactory.java
// WARNING

/**
 * The <code>LoggerFactory</code> can produce Loggers for various logging APIs,
 * most notably for log4j, JDK 1.4 logging. Other implemenations such as
 * {@link org.slf4j.impl.NOPLogger NOPLogger} and
 * {@link org.slf4j.impl.DumbLogger DumbLogger} are also supported.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class LoggerFactory {
  static LoggerFactoryAdapter adapter;

  // 
  // WARNING Modify the original in
  //         $SLF4J_HOME/src/filtered-java/org/slf4j/
  
  static {
    String adapterClassStr = "org.slf4j.impl.@IMPL@LoggerFA";
    System.out.println("SLF4J built for "+adapterClassStr);
    try {
      adapter = new org.slf4j.impl.@IMPL@LoggerFA();
    } catch (Exception e) {
      // unless there was a problem with the build or the JVM we will never
      // get exceptions
      System.err.println(
        "Could not instantiate instance of class [" + adapterClassStr + "]");
      e.printStackTrace();
    }
  }

  public static ULogger getLogger(String name) {
    return adapter.getLogger(name);
  }

  public static ULogger getLogger(String domainName, String subDomainName) {
    return adapter.getLogger(domainName, subDomainName);
  }

  public static ULogger getLogger(Class clazz) {
    return adapter.getLogger(clazz.getName());
  }

  public static ULogger getLogger(Class clazz, String subDomainName) {
    return adapter.getLogger(clazz.getName(), subDomainName);
  }
}
