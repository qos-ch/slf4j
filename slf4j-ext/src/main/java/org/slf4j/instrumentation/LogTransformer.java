/**
 * 
 */
package org.slf4j.instrumentation;

import static org.slf4j.helpers.MessageFormatter.format;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

public class LogTransformer implements ClassFileTransformer {

	public static class Builder {
		public LogTransformer build() {
			return new LogTransformer(this);
		}

		boolean addEntryExit;

		public Builder addEntryExit(boolean b) {
			addEntryExit = b;
			return this;
		}

		boolean addVariableAssignment;

		public Builder addVariableAssignment(boolean b) {
			System.err.println("cannot currently log variable assignments.");
			addVariableAssignment = b;
			return this;
		}
		boolean verbose;

		public Builder verbose(boolean b) {
			verbose = b;
			return this;
		}
	}


	private LogTransformer(Builder builder) {
		this.addEntryExit = builder.addEntryExit;
		this.addVariableAssignment = builder.addVariableAssignment;
		this.verbose = builder.verbose;
	}

	private static final String _LOG = "_log";
	String[] ignore = { "sun/", "java/", "javax/", "org/slf4j/",
			"ch/qos/logback/" };

	private boolean addEntryExit;
	private boolean addVariableAssignment;
	private boolean verbose;

	public byte[] transform(ClassLoader loader, String className,
			Class<?> clazz, ProtectionDomain domain, byte[] bytes) {

		return transform0(className, clazz, bytes);
	}

	/**
	 * transform0 sees if the className starts with any of the namespaces to
	 * ignore, if so it is returned unchanged. Otherwise it is processed by
	 * doClass(...)
	 * 
	 * @param className
	 * @param clazz
	 * @param bytes
	 * @return
	 */
	private byte[] transform0(String className, Class<?> clazz, byte[] bytes) {
		for (int i = 0; i < ignore.length; i++) {
			if (className.startsWith(ignore[i])) {
				return bytes;
			}
		}
		if (verbose) {
			System.err.println("Loggifying " + className);
		}
		return doClass(className, clazz, bytes);
	}

	/**
	 * The transform(...) method calls doClass(...) if the class name does not
	 * start with any of the prefixes it has been told to ignore.
	 * 
	 * doClass() first creates a class description from the byte codes. If it is
	 * a class (i.e. not an interface) the methods defined have bodies, and a
	 * static final logger object is added with the name of this class as an
	 * argument, and each method then gets processed with doMethod(...) to have
	 * logger calls added.
	 * 
	 * 
	 * 
	 * @param name
	 *            class name (slashes separate, not dots)
	 * @param clazz
	 * @param b
	 * @return
	 */
	private byte[] doClass(String name, Class<?> clazz, byte[] b) {
		ClassPool pool = ClassPool.getDefault();
		CtClass cl = null;
		try {
			cl = pool.makeClass(new ByteArrayInputStream(b));
			if (cl.isInterface() == false) {

				String pattern1 = "private static org.slf4j.Logger {};";
				String loggerDefinition = format(pattern1, _LOG);
				CtField field = CtField.make(loggerDefinition, cl);

				String pattern2 = "org.slf4j.LoggerFactory.getLogger({}.class);";
				String replace = name.replace('/', '.');
				String getLogger = format(pattern2, replace);

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
			String pattern = "Could not instrument {},  exception : {}";
			System.err.println(format(pattern, name, e.getMessage()));
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

		if (addEntryExit) {
			String messagePattern = "if ({}.isDebugEnabled()) {}.info(\">> {}\");";
			Object[] arg1 = new Object[] { _LOG, _LOG, signature };
			String before = format(messagePattern, arg1);
			method.insertBefore(before);

			String messagePattern2 = "if ({}.isDebugEnabled()) {}.info(\"<< {}{}\");";
			Object[] arg2 = new Object[] { _LOG, _LOG, signature, returnValue };
			String after = format(messagePattern2, arg2);
			method.insertAfter(after);
		}
	}
}