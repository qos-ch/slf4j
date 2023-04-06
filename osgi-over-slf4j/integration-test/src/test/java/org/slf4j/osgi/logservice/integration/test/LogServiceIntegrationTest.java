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

package org.slf4j.osgi.logservice.integration.test;

import junit.framework.Assert;
import net.jodah.concurrentunit.Waiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

import javax.inject.Inject;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Tests the following:
 * Registering a LogListener
 * Use LogService to log messages; tests receiving them asynchronously
 * Use LogReaderService to get the list of LogEntries
 * Tests that EventAdmin gets events
 * Tests that Framework Events get logged
 * Tests unregistering a LogListener
 */
@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class)
public class LogServiceIntegrationTest {

  private static final String MESSAGE = "a message";

  @Inject
  LogService logService;
  @Inject
  LogReaderService logReaderService;
  @Inject
  BundleContext bundleContext;


  Waiter waiter = new Waiter();


  @Configuration
  public Option[] configure() {
    return CoreOptions.options(
            CoreOptions.cleanCaches(),
            CoreOptions.systemPackages(
                    "javax.net.ssl",
                    "javax.xml.parsers",
                    "org.w3c.dom",
                    "org.xml.sax"),
            CoreOptions.junitBundles(),
            CoreOptions.mavenBundle("org.slf4j", "slf4j-simple").noStart(), //Fragments onto slf4j-api
            CoreOptions.mavenBundle("org.slf4j", "slf4j-api"),
            CoreOptions.mavenBundle("org.slf4j", "osgi-over-slf4j"),
            CoreOptions.mavenBundle("org.apache.felix", "org.apache.felix.eventadmin", "1.3.2"),
            CoreOptions.wrappedBundle(CoreOptions.mavenBundle("net.jodah", "concurrentunit", "0.4.2"))
    );
  }

  @Test
  public void testLogMessage() throws Exception {

    LogListener logListener = new LogListener() {
      public void logged(LogEntry entry) {
        waiter.assertEquals(LogService.LOG_INFO, entry.getLevel());
        waiter.assertEquals(MESSAGE, entry.getMessage());
        waiter.resume();
      }
    };
    logReaderService.addLogListener(logListener);

    logService.log(LogService.LOG_INFO, MESSAGE);

    waiter.await(1000);

    //shutting down Pax Exam will cause listener to fail test
    logReaderService.removeLogListener(logListener);
  }

  @Test
  public void testFrameworkEventsLogged() throws Exception {
    LogListener logListener = new LogListener() {
      public void logged(LogEntry entry) {
        waiter.assertEquals(LogService.LOG_INFO, entry.getLevel());
        waiter.assertEquals("ServiceEvent REGISTERED", entry.getMessage());
        waiter.resume();
      }
    };
    logReaderService.addLogListener(logListener);

    EventHandler eventHandler = new EventHandler() {
      public void handleEvent(Event event) {
        waiter.assertEquals(10, event.getProperty("log.level"));
        waiter.assertEquals(MESSAGE, event.getProperty("message"));
        waiter.resume();
      }
    };

    String[] topics = new String[] { "org/osgi/service/log/LogEntry/LOG_OTHER" };
    Dictionary<String, Object> properties = new Hashtable<String, Object>();
    properties.put(EventConstants.EVENT_TOPIC, topics);

    //triggers a framework event
    ServiceRegistration registration = bundleContext.registerService(EventHandler.class.getName(), eventHandler, properties);

    //remove logListener so only EventHandler is triggered with log()
    logReaderService.removeLogListener(logListener);

    //triggers EventAdmin Log event
    logService.log(10, MESSAGE);

    waiter.await(1000, 2);

    registration.unregister();
  }

  @Test
  public void testGetLogEntries() {
    for (int i = 10; i > 0; i--) {
      logService.log(LogService.LOG_INFO, Integer.toString(i));
    }

    Enumeration logEnum = logReaderService.getLog();

    for (int i = 1; i <= 10; i ++) {
      LogEntry entry = (LogEntry) logEnum.nextElement();
      Assert.assertEquals(Integer.toString(i), entry.getMessage());
    }
  }
}
