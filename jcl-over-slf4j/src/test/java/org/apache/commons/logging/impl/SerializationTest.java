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
package org.apache.commons.logging.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.impl.JDK14LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

public class SerializationTest {

    ObjectInputStream ois;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos;

    @Before
    public void setUp() throws Exception {
        oos = new ObjectOutputStream(baos);
    }

    @After
    public void tearDown() throws Exception {
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

    @Test
    public void testSLF4JLog() throws Exception {
        JDK14LoggerFactory factory = new JDK14LoggerFactory();
        SLF4JLog log = new SLF4JLog(factory.getLogger("x"));
        oos.writeObject(log);
        verify();
    }

    @Test
    public void testSmoke() throws Exception {
        Log log = LogFactory.getLog("testing");
        oos.writeObject(log);
        verify();
    }

    @Test
    public void testLocationAware() throws Exception {
        JDK14LoggerFactory factory = new JDK14LoggerFactory();
        SLF4JLocationAwareLog log = new SLF4JLocationAwareLog((LocationAwareLogger) factory.getLogger("x"));
        oos.writeObject(log);
        verify();
    }
}
