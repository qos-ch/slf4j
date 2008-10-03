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

	public static void premain(String agentArgument,
			Instrumentation instrumentation) {

		System.err.println("THIS JAVAAGENT IS NOT RELEASED YET.  DO NOT USE IN PRODUCTION ENVIRONMENTS.");
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
			throw new RuntimeException(
					"Could not load arguments as properties", e);
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
			public void run() {
				long timePassed = System.currentTimeMillis() - start;
				String message = format(STOP_MSG, new Date(), timePassed);
				System.err.println(message);
			}
		};
		Runtime.getRuntime().addShutdownHook(hook);
	}

}