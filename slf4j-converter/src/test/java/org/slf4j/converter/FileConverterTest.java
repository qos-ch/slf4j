package org.slf4j.converter;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.slf4j.converter.line.EmptyRuleSet;
import org.slf4j.converter.line.LineConverter;

public class FileConverterTest extends TestCase {

  public FileConverterTest(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void test() {
  }

  
  public void XtestNOP() throws IOException {
    InplaceFileConverter fc = new InplaceFileConverter(new LineConverter(new EmptyRuleSet()));
    byte[] ba = fc.readFile(new File("c:/varargs.txt"));
    fc.convert(new File("c:/varargs.txt"), ba);
  }
}
