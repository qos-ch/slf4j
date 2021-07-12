/**
 * Copyright (c) 2015 QOS.ch
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
 */

package org.slf4j.osgi.logservice.impl;

import org.junit.Assert;
import org.junit.Test;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogService;

import java.util.Vector;

public class LogEntryStoreTest {

  LogEntryStore classUnderTest = new LogEntryStore();

  @Test
  public void testLogEntryStore() throws Exception {
    LogEntry one = new ImmutableLogEntry(null, LogService.LOG_INFO, "one");
    LogEntry two = new ImmutableLogEntry(null, LogService.LOG_INFO, "two");
    LogEntry three = new ImmutableLogEntry(null, LogService.LOG_INFO, "three");

    classUnderTest.addLogEntry(one);
    classUnderTest.addLogEntry(two);
    classUnderTest.addLogEntry(three);

    Vector<LogEntry> actualEntries = classUnderTest.entriesSnapshot();

    Assert.assertEquals(3, actualEntries.size());
    Assert.assertEquals(three, actualEntries.get(0));
    Assert.assertEquals(two, actualEntries.get(1));
    Assert.assertEquals(one, actualEntries.get(2));

    for (int i = 0; i < LogEntryStore.DEFAULT_SIZE; i++) {
      LogEntry entry = new ImmutableLogEntry(null, LogService.LOG_WARNING, Integer.toString(i));
      classUnderTest.addLogEntry(entry);
    }

    actualEntries = classUnderTest.entriesSnapshot();

    Assert.assertEquals(LogEntryStore.DEFAULT_SIZE, actualEntries.size());
    Assert.assertFalse(actualEntries.contains(one));
    Assert.assertFalse(actualEntries.contains(two));
    Assert.assertFalse(actualEntries.contains(three));
  }
}