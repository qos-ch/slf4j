package org.slf4j.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import org.slf4j.instrumentation.JavassistHelper;

public class AgentMain implements ClassFileTransformer {

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
		instrumentation.addTransformer(new AgentMain());
	}

	// Determine name of logger

	String def = "private static org.slf4j.Logger " + "_log" + ";";
	String ifLog = "if (_log.isDebugEnabled())";

	String[] ignore = new String[] { "sun/", "java/", "javax/", "org/slf4j/",
			"ch/qos/logback/" };

	//
	// The transform(...) method calls doClass(...) if the class name does not
	// start with any of the prefixes it has been told to ignore (note that the
	// separators are slashes, not dots).

	public byte[] transform(ClassLoader loader, String className, Class<?> clazz,
			java.security.ProtectionDomain domain, byte[] bytes) {

		for (int i = 0; i < ignore.length; i++) {
			if (className.startsWith(ignore[i])) {
				return bytes;
			}
		}
		//System.out.println("Adding to " + className);
		return doClass(className, clazz, bytes);
	}

	//
	// The doClass(...) method uses javassist to analyze the byte stream. If it
	// is a real class, a logger field is added and initialized to the name of
	// the class. Each non-empty method is then processed with doMethod(...).
	// The finally-clause ensures that the class definition is removed again
	// from the javassist pools to keep memory usage down.
	//
	// Note: The logger variable has been named _log. In a production version an
	// unused variable name should be found and used.

	private byte[] doClass(String name, Class<?> clazz, byte[] b) {
		ClassPool pool = ClassPool.getDefault();
		CtClass cl = null;
		try {
			cl = pool.makeClass(new java.io.ByteArrayInputStream(b));
			if (cl.isInterface() == false) {

				CtField field = CtField.make(def, cl);
				String getLogger = "org.slf4j.LoggerFactory.getLogger("
						+ name.replace('/', '.') + ".class);";
				cl.addField(field, getLogger);
				System.out.println(getLogger);

				CtBehavior[] methods = cl.getDeclaredBehaviors();
				for (int i = 0; i < methods.length; i++) {
					if (methods[i].isEmpty() == false) {
						doMethod(methods[i]);
					}
				}
				b = cl.toBytecode();
			}
		} catch (Exception e) {
			System.err.println("Could not instrument  " + name
					+ ",  exception : " + e.getMessage());
		} finally {
			if (cl != null) {
				cl.detach();
			}
		}
		return b;
	}

	// The doMethod(...) class creates "_log.info(...)" snippets to insert at
	// the beginning and end of each method. Both contain the parameters (as
	// they may have changed), and the end method statement contain the return
	// value for non-void methods (which is available as $_ in the javassist
	// compiler).

	private void doMethod(CtBehavior method) throws NotFoundException,
			CannotCompileException {

		String signature = JavassistHelper.getSignature(method);
		String returnValue = JavassistHelper.returnValue(method);

		method.insertBefore(ifLog + "_log" + ".info(\">> " + signature + ");");

		method.insertAfter(ifLog + "_log" + ".info(\"<< " + signature
				+ returnValue + ");");
	}
}