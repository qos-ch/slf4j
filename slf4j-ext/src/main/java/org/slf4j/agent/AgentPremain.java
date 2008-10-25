package org.slf4j.agent;

import static org.slf4j.helpers.MessageFormatter.format;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Date;
import java.util.Properties;

import org.slf4j.instrumentation.LogTransformer;

public class AgentPremain {

  private static final String START_MSG = "Start at {}";
  private static final String STOP_MSG = "Stop at {}, execution time = {} ms";

  /**
   * JavaAgent premain entry point as specified in the MANIFEST.MF file. See
   * {@link http
   * ://java.sun.com/javase/6/docs/api/java/lang/instrument/package-summary
   * .html} for details.
   * 
   * @param agentArgument
   *          string provided after "=" up to first space
   * @param instrumentation
   *          instrumentation environment provided by the JVM
   */
  public static void premain(String agentArgument,
      Instrumentation instrumentation) {

    System.err.println("THIS JAVAAGENT IS NOT RELEASED YET.  "
        + "DO NOT USE IN PRODUCTION ENVIRONMENTS.");

    LogTransformer.Builder builder = new LogTransformer.Builder();
    builder = builder.addEntryExit(true);

    if (agentArgument != null) {
      Properties args = parseArguments(agentArgument);

      if (args.containsKey("verbose")) {
        builder = builder.verbose(true);
      }

      if (args.containsKey("time")) {
        printStartStopTimes();
      }

      if (args.containsKey("ignore")) {
        builder = builder.ignore(args.getProperty("ignore").split(","));
      }

      if (args.containsKey("level")) {
        builder = builder.level(args.getProperty("level"));
      }

      // ... more agent option handling here
    }

    instrumentation.addTransformer(builder.build());
  }

  private static Properties parseArguments(String agentArgument) {
    Properties p = new Properties();
    try {
      byte[] bytes = agentArgument.replaceAll(";", "\n").getBytes();
      p.load(new ByteArrayInputStream(bytes));
    } catch (IOException e) {
      String s = "Could not load arguments as properties";
      throw new RuntimeException(s, e);
    }
    return p;
  }

  /**
   * Print the start message with the time NOW, and register a shutdown hook
   * which will print the stop message with the time then and the number of
   * milliseconds passed since.
   * 
   */
  private static void printStartStopTimes() {
    final long start = System.currentTimeMillis();
    System.err.println(format(START_MSG, new Date()));

    Thread hook = new Thread() {
      @Override
      public void run() {
        long timePassed = System.currentTimeMillis() - start;
        String message = format(STOP_MSG, new Date(), timePassed);
        System.err.println(message);
      }
    };
    Runtime.getRuntime().addShutdownHook(hook);
  }
}