package org.apache.commons.logging.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.impl.JDK14LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

public class SerializationTest extends TestCase {

  ObjectInputStream ois;
  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  ObjectOutputStream oos;

  public SerializationTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    oos = new ObjectOutputStream(baos);
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    oos.close();
  }

  public void verify() throws IOException, ClassNotFoundException {
    ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
    ois = new ObjectInputStream(bis);

    Log resuscitatedLog = (Log) ois.readObject();
    // tests that the "private transient Logger logger" field is non-null
    resuscitatedLog.debug("");
    resuscitatedLog.isDebugEnabled();
  }

  public void testSLF4JLog() throws Exception {
    JDK14LoggerFactory factory = new JDK14LoggerFactory();
    SLF4JLog log = new SLF4JLog(factory.getLogger("x"));
    oos.writeObject(log);
    verify();
  }

  public void testSmoke() throws Exception {
    Log log = LogFactory.getLog("testing");
    oos.writeObject(log);
    verify();
  }

  public void testLocationAware() throws Exception {
    JDK14LoggerFactory factory = new JDK14LoggerFactory();
    SLF4JLocationAwareLog log = new SLF4JLocationAwareLog(
        (LocationAwareLogger) factory.getLogger("x"));
    oos.writeObject(log);
    verify();
  }
}
