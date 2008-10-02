package org.slf4j;

import java.io.PrintStream;

public class SilentPrintStream extends PrintStream {

  PrintStream other;

  public SilentPrintStream(PrintStream ps) {
    super(ps);
    other = ps;
  }

  public void print(String s) {
  }

  public void println(String s) {
  }
  
  public void println(Object x) {
  }
}
