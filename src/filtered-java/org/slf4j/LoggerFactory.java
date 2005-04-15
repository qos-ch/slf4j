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
  //         $SLF4J_HOME/src/filtered-java/org/apache/slf4j/
  
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
