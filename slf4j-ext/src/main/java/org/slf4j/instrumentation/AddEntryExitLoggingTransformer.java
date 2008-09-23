/**
 * 
 */
package org.slf4j.instrumentation;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;


public class AddEntryExitLoggingTransformer implements
		ClassFileTransformer {
	
	final String def = "private static org.slf4j.Logger " + "_log" + ";";
	final String ifLog = "if (_log.isDebugEnabled())";
	String[] ignore = new String[] { "sun/", "java/", "javax/", "org/slf4j/",
			"ch/qos/logback/" };

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
	// The transform(...) method calls doClass(...) if the class name does not
	// start with any of the prefixes it has been told to ignore (note that the
	// separators are slashes, not dots).

	private byte[] doClass(String name, Class<?> clazz, byte[] b) {
		ClassPool pool = ClassPool.getDefault();
		CtClass cl = null;
		try {
			cl = pool.makeClass(new ByteArrayInputStream(b));
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

	private void doMethod(CtBehavior method) throws NotFoundException,
			CannotCompileException {

		String signature = JavassistHelper.getSignature(method);
		String returnValue = JavassistHelper.returnValue(method);

		method.insertBefore(ifLog + "_log" + ".info(\">> " + signature + ");");

		method.insertAfter(ifLog + "_log" + ".info(\"<< " + signature
				+ returnValue + ");");
	}
}