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

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Arrays;


@RunWith(MockitoJUnitRunner.class)
public class EventAdminSenderTest {

  private static final Long BUNDLE_ID = 5L;
  private static final String BUNDLE_SYMBOLIC_NAME = "symbolic name";
  private static final Long SERVICE_ID = 99L;
  private static final String SERVICE_PID = "service pid";
  private static final String[] SERVICE_OBJECTCLASS = new String[]{"org.service.ClassName"};

  @Mock
  ServiceTracker serviceTracker;
  @Mock
  EventAdmin eventAdmin;

  @InjectMocks
  EventAdminSender classUnderTest;

  @Mock
  Bundle bundle;
  @Mock
  ServiceReference serviceReference;
  @Captor
  ArgumentCaptor<Event> eventArgument;

  @Before
  public void SetUp() {
    Mockito.when(bundle.getBundleId())
            .thenReturn(BUNDLE_ID);
  }

  @Test
  public void testWithNoEventAdminAvailable() throws Exception {
    LogEntry entry = new ImmutableLogEntry(bundle, LogService.LOG_INFO, "message");

    classUnderTest.logged(entry);

    Mockito.verifyZeroInteractions(eventAdmin);
  }

  @Test
  public void testLoggedWithMinimalProperties() {
    setupEventAdminAvailable();

    LogEntry entry = new ImmutableLogEntry(bundle, LogService.LOG_DEBUG, "message");

    classUnderTest.logged(entry);

    Event actual = captureEvent();
    Assert.assertEquals(EventAdminSender.LOG_TOPIC_BASE + "LOG_DEBUG", actual.getTopic());

    Mockito.reset(eventAdmin);
    entry = new ImmutableLogEntry(bundle, LogService.LOG_DEBUG + 1, "message");

    classUnderTest.logged(entry);

    actual = captureEvent();
    Assert.assertEquals(EventAdminSender.LOG_TOPIC_BASE + "LOG_OTHER", actual.getTopic());
    Assert.assertEquals(bundle, actual.getProperty("bundle"));
    Assert.assertEquals(BUNDLE_ID, actual.getProperty("bundle.id"));
    Assert.assertEquals(5, actual.getProperty("log.level"));
    Assert.assertEquals(entry.getMessage(), actual.getProperty("message"));
    Assert.assertEquals(entry.getTime(), actual.getProperty("timestamp"));
  }

  @Test
  public void testLoggedWithAllProperties() {
    setupEventAdminAvailable();
    setupBundleSymbolicName();
    setupServiceReference();

    Throwable expectedException = new NullPointerException("exception message");
    LogEntry entry = new ImmutableLogEntry(bundle, serviceReference, LogService.LOG_ERROR, "message", expectedException);

    classUnderTest.logged(entry);

    Event actual = captureEvent();
    Assert.assertEquals(EventAdminSender.LOG_TOPIC_BASE + "LOG_ERROR", actual.getTopic());
    Assert.assertEquals(BUNDLE_SYMBOLIC_NAME, actual.getProperty("bundle.symbolicName"));
    Throwable actualException = (Throwable) actual.getProperty("exception");
    Assert.assertSame(expectedException, actualException);
    Assert.assertEquals(actual.getProperty("exception.class"), expectedException.getClass().getName());
    Assert.assertEquals(actual.getProperty("exception.message"), expectedException.getMessage());
    ServiceReference actualReference = (ServiceReference) actual.getProperty("service");
    Assert.assertSame(serviceReference, actualReference);
    Assert.assertEquals(SERVICE_ID, actual.getProperty("service.id"));
    Assert.assertEquals(SERVICE_PID, actual.getProperty("service.pid"));
    Assert.assertTrue(Arrays.equals(SERVICE_OBJECTCLASS, (String[]) actual.getProperty("service.objectClass")));
  }


  private void setupEventAdminAvailable() {
    Mockito.when(serviceTracker.getService())
            .thenReturn(eventAdmin);
  }

  private void setupBundleSymbolicName() {
    Mockito.when(bundle.getSymbolicName())
            .thenReturn(BUNDLE_SYMBOLIC_NAME);
  }

  private void setupServiceReference() {
    Mockito.when(serviceReference.getProperty("service.id"))
            .thenReturn(SERVICE_ID);
    Mockito.when(serviceReference.getProperty("service.pid"))
            .thenReturn(SERVICE_PID);
    Mockito.when(serviceReference.getProperty("service.objectClass"))
            .thenReturn(SERVICE_OBJECTCLASS);
  }

  private Event captureEvent() {
    Mockito.verify(eventAdmin).postEvent(eventArgument.capture());
    return eventArgument.getValue();
  }
}