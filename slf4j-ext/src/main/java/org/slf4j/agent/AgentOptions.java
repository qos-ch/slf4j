package org.slf4j.agent;

/**
 * <p>
 * All recognized options in the string passed to the java agent. For
 * "java -javaagent:foo.jar=OPTIONS HelloWorld" this would be "OPTIONS".
 * </p>
 * <p>
 * It is considered to be a list of options separated by (currently) ";", on the
 * form "option=value". The interpretation of "value" is specific to each
 * option.
 * </p>
 */
public class AgentOptions {

  /**
   * List of class prefixes to ignore when instrumenting. Note: Classes loaded
   * before the agent cannot be instrumented.
   */
  public static final String IGNORE = "ignore";
  /**
   * Indicate the SLF4J level that should be used by the logging statements
   * added by the agent. Default is "info".
   */
  public static final String LEVEL = "level";
  /**
   * Indicate that the agent should print out "new java.util.Date()" at the time
   * the option was processed and at shutdown time (using the shutdown hook).
   * 
   */
  public static final String TIME = "time";
  /**
   * Indicate that the agent should log actions to System.err, like adding
   * logging to methods, etc.
   * 
   */
  public static final String VERBOSE = "verbose";

}
