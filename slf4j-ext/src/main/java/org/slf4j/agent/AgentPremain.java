package org.slf4j.agent;

import static org.slf4j.helpers.MessageFormatter.format;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.instrumentation.LogTransformer;

public class AgentPremain {

	private static final String START_MSG = "Start at {}";
	private static final String STOP_MSG = "Stop at {}, execution time = {} ms";

	public static void premain(String agentArgument,
			Instrumentation instrumentation) {

		LogTransformer.Builder builder = new LogTransformer.Builder();
		builder = builder.addEntryExit(true);
		
		if (agentArgument != null) {
			String[] args = agentArgument.split(",");
			Set<String> argSet = new HashSet<String>(Arrays.asList(args));

			if (argSet.contains("verbose")) {
				builder = builder.verbose(true);
			}
			
			if (argSet.contains("time")) {
				printStartStopTimes();
			}
			
			// ... more agent option handling here
		}

		instrumentation.addTransformer(builder.build());
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