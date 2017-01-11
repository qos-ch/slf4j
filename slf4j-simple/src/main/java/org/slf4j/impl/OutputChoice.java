package org.slf4j.impl;

import java.io.PrintStream;

/**
 * This class encapsulates the user's choice of output target.
 * 
 * 
 * @author ceki
 *
 */
class OutputChoice {

	enum OutputChoiceType {
		SysOut, SysErr, File;
	}

	
	final OutputChoiceType outputChoiceType;
	final PrintStream targetPrintStream;

	OutputChoice(OutputChoiceType outputChoiceType) {
		if (outputChoiceType == OutputChoiceType.File) {
			throw new IllegalArgumentException();
		}
		this.outputChoiceType = outputChoiceType;
		this.targetPrintStream = null;
	}

	OutputChoice(PrintStream printStream) {
		this.outputChoiceType = OutputChoiceType.File;
		this.targetPrintStream = printStream;
	}

	PrintStream getTargetPrintStream() {
		switch (outputChoiceType) {
		case SysOut:
			return System.out;
		case SysErr:
			return System.err;
		case File:
			return targetPrintStream;
		default:
			throw new IllegalArgumentException();
		}

	}

}
