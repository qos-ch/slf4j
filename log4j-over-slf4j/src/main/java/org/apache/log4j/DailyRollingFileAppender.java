package org.apache.log4j;

import java.io.IOException;

/**
 * This class is a minimal implementation of the original Log4J class.
 *
 * @author Bojan Vukasovic <bojanv55@gmail.com>
 * */
public class DailyRollingFileAppender extends FileAppender {

	private String datePattern = "'.'yyyy-MM-dd";

	public DailyRollingFileAppender(){
		super();
	}

	public DailyRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException {
		super();
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String pattern) {
		datePattern = pattern;
	}

	public void activateOptions() {
	}
}
