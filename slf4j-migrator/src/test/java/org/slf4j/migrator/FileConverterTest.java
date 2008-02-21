package org.slf4j.migrator;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.slf4j.migrator.InplaceFileConverter;
import org.slf4j.migrator.internal.NopProgressListener;
import org.slf4j.migrator.line.EmptyRuleSet;

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
    InplaceFileConverter fc = new InplaceFileConverter(new EmptyRuleSet(), new NopProgressListener());
    fc.convert(new File("c:/varargs.txt"));
  }
}
