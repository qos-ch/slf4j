package org.slf4j;

import org.slf4j.impl.SimpleLoggerFA;

public class SimpleLoggerFAFactory {

	public static LoggerFactoryAdapter getInstance() {
		return new SimpleLoggerFA();
	}
}
