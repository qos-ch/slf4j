package org.slf4j.impl;

import java.io.PrintStream;

public class StackTracePrinterSimple implements StackTracePrinter {

  public void printStackTrace(Throwable t, PrintStream ps) {
    t.printStackTrace();
  }

}
