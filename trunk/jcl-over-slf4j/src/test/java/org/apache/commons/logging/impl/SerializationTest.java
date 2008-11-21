package org.apache.commons.logging.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SerializationTest extends TestCase {

  ObjectOutputStream oos;
  
  public SerializationTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    oos = new ObjectOutputStream(baos);
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    oos.close();
  }

  
  public void testSmokeSimple() throws IOException {
    Log log = LogFactory.getLog("testing");
    oos.writeObject(log);
  }
  
  public void testSmokeLocationAware() throws IOException {
    SLF4JLocationAwareLog log = new SLF4JLocationAwareLog(null);
    oos.writeObject(log);
  }
}
