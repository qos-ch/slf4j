package org.slf4j;

import org.slf4j.impl.XLoggerFA;


public class XLoggerFAFactory {

	public static LoggerFactoryAdapter getInstance() {
		return new XLoggerFA();
	}
}
