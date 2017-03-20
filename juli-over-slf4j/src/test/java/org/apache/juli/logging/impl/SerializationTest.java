/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.apache.juli.logging.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.juli.logging.impl.SLF4JLocationAwareLog;
import org.apache.juli.logging.impl.SLF4JLog;
import org.slf4j.impl.JDK14LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

public class SerializationTest extends TestCase {

  ObjectInputStream ois;

  ByteArrayOutputStream baos = new ByteArrayOutputStream();

  ObjectOutputStream oos;

  public SerializationTest(final String name) {
    super(name);
  }

  @Override
  protected void setUp() throws Exception {
    this.oos = new ObjectOutputStream(this.baos);
    super.setUp();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    this.oos.close();
  }

  public void verify() throws IOException, ClassNotFoundException {
    final ByteArrayInputStream bis = new ByteArrayInputStream(
        this.baos.toByteArray());
    this.ois = new ObjectInputStream(bis);

    final Log resuscitatedLog = (Log) this.ois.readObject();
    // tests that the "private transient Logger logger" field is non-null
    resuscitatedLog.debug("");
    resuscitatedLog.isDebugEnabled();
  }

  public void testSLF4JLog() throws Exception {
    final JDK14LoggerFactory factory = new JDK14LoggerFactory();
    final SLF4JLog log = new SLF4JLog(factory.getLogger("x"));
    this.oos.writeObject(log);
    verify();
  }

  public void testSmoke() throws Exception {
    final Log log = LogFactory.getLog("testing");
    this.oos.writeObject(log);
    verify();
  }

  public void testLocationAware() throws Exception {
    final JDK14LoggerFactory factory = new JDK14LoggerFactory();
    final SLF4JLocationAwareLog log = new SLF4JLocationAwareLog(
        (LocationAwareLogger) factory.getLogger("x"));
    this.oos.writeObject(log);
    verify();
  }
}
