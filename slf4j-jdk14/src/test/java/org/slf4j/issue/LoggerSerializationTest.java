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
package org.slf4j.issue;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import junit.framework.Assert;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * See http://bugzilla.slf4j.org/show_bug.cgi?id=261
 * @author Thorbjorn Ravn Andersen
 */
public class LoggerSerializationTest extends TestCase {

  static class LoggerHolder implements Serializable {
 		private static final long serialVersionUID = 1L;

 		private Logger log = LoggerFactory.getLogger(LoggerHolder.class);

 		public String toString() {
 			return "log=" + getLog();
 		}

 		public Logger getLog() {
 			return log;
 		}
 	}

 	public void testCanLoggerBeSerialized() throws IOException,
 			ClassNotFoundException {

 		LoggerHolder lh1 = new LoggerHolder();

 		ByteArrayOutputStream baos = new ByteArrayOutputStream();
 		ObjectOutputStream out = new ObjectOutputStream(baos);
 		out.writeObject(lh1);
 		out.close();

 		lh1 = null;

 		byte[] serializedLoggerHolder = baos.toByteArray();

 		InputStream is = new ByteArrayInputStream(serializedLoggerHolder);
 		ObjectInputStream in = new ObjectInputStream(is);
 		LoggerHolder lh2 = (LoggerHolder) in.readObject();

 		Assert.assertNotNull(lh2);
 		Assert.assertNotNull(lh2.getLog());
 		lh2.getLog().info("You must see this message as a log message");
 	}

}
