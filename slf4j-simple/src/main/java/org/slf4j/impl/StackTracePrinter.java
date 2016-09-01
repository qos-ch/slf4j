package org.slf4j.impl;

import java.io.PrintStream;

public interface StackTracePrinter {

  void printStackTrace(Throwable t, PrintStream ps);

}
