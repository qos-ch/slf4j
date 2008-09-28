package org.slf4j.agent;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.instrumentation.AddEntryExitLoggingTransformer;

public class AgentMain {

	public static void premain(String agentArgument,
			Instrumentation instrumentation) {

		if (agentArgument != null) {
			String[] args = agentArgument.split(",");
			Set<String> argSet = new HashSet<String>(Arrays.asList(args));

			if (argSet.contains("time")) {
				final long start = System.currentTimeMillis();
				System.out.println("Start at " + new Date());
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						System.out.println("Stop at " + new Date()
								+ ", execution time = "
								+ (System.currentTimeMillis() - start) + " ms");
					}
				});
			}
			// ... more agent option handling here
		}

		instrumentation.addTransformer(new AddEntryExitLoggingTransformer());
	}

}