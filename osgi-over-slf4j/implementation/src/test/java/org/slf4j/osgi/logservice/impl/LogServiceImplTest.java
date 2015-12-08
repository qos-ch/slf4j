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
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogService;
import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;


@RunWith(MockitoJUnitRunner.class)
public class LogServiceImplTest {

    static final String MESSAGE = "a log message";
    static final String REFERENCE_NAME = "reference";

    @Mock
    PackageAdmin packageAdmin;
    @Mock
    Log log;
    @Captor
    ArgumentCaptor<LogEntry> logEntryArgument;
    @Mock
    Bundle bundle;
    @Mock
    Logger delegate;

    @InjectMocks
    LogServiceImpl classUnderTest;


    @Test
    public void testLogWithLevelAndMessage() throws Exception {
        int level;

        level = LogService.LOG_ERROR;
        classUnderTest.log(level, MESSAGE);
        Mockito.verify(delegate).error(MESSAGE);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_WARNING;
        classUnderTest.log(level, MESSAGE);
        Mockito.verify(delegate).warn(MESSAGE);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_INFO;
        classUnderTest.log(level, MESSAGE);
        Mockito.verify(delegate).info(MESSAGE);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_DEBUG;
        setupOSGiLogDebugEnabled();
        classUnderTest.log(level, MESSAGE);
        Mockito.verify(delegate).debug(MESSAGE);
        verifyLog_addLogEntry(level, MESSAGE);

        setupOSGiLogDebugDisabled();
        classUnderTest.log(level, MESSAGE);
        Mockito.verify(delegate).debug(MESSAGE);
        Mockito.verify(log, Mockito.never()).addLogEntry(Mockito.any(LogEntry.class));
    }

    @Test
    public void testLogWithLevelAndMessageAndException() throws Exception {
        int level;
        Throwable exception = new NullPointerException();

        level = LogService.LOG_ERROR;
        classUnderTest.log(level, MESSAGE, exception);
        Mockito.verify(delegate).error(MESSAGE, exception);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_WARNING;
        classUnderTest.log(level, MESSAGE, exception);
        Mockito.verify(delegate).warn(MESSAGE, exception);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_INFO;
        classUnderTest.log(level, MESSAGE, exception);
        Mockito.verify(delegate).info(MESSAGE, exception);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_DEBUG;
        setupOSGiLogDebugEnabled();
        classUnderTest.log(level, MESSAGE, exception);
        Mockito.verify(delegate).debug(MESSAGE, exception);
        verifyLog_addLogEntry(level, MESSAGE);

        setupOSGiLogDebugDisabled();
        classUnderTest.log(level, MESSAGE, exception);
        Mockito.verify(delegate).debug(MESSAGE, exception);
        Mockito.verify(log, Mockito.never()).addLogEntry(Mockito.any(LogEntry.class));
    }

    @Test
    public void testLogWithServiceReferenceAndLevelAndMessage() throws Exception {
        ServiceReference reference = setupServiceReference();
        int level;
        String logMessage = String.format("[%s]%s", REFERENCE_NAME, MESSAGE);

        level = LogService.LOG_ERROR;
        setupSlf4jErrorEnabled();
        classUnderTest.log(reference, level, MESSAGE);
        Mockito.verify(delegate).error(logMessage);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_WARNING;
        setupSlf4jWarnEnabled();
        classUnderTest.log(reference, level, MESSAGE);
        Mockito.verify(delegate).warn(logMessage);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_INFO;
        setupSlf4jInfoEnabled();
        classUnderTest.log(reference, level, MESSAGE);
        Mockito.verify(delegate).info(logMessage);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_DEBUG;
        setupOSGiLogDebugEnabled();
        setupSlf4jDebugEnabled();
        classUnderTest.log(reference, level, MESSAGE);
        Mockito.verify(delegate).debug(logMessage);
        verifyLog_addLogEntry(level, MESSAGE);

        setupOSGiLogDebugDisabled();
        setupSlf4jDebugEnabled();
        classUnderTest.log(reference, level, MESSAGE);
        Mockito.verify(delegate).debug(logMessage);
        Mockito.verify(log, Mockito.never()).addLogEntry(Mockito.any(LogEntry.class));
    }

    @Test
    public void testLogWithServiceReferenceAndLevelAndMessageAndException() throws Exception {
        ServiceReference reference = setupServiceReference();
        int level;
        String logMessage = String.format("[%s]%s", REFERENCE_NAME, MESSAGE);
        Throwable exception = new NullPointerException();

        level = LogService.LOG_ERROR;
        setupSlf4jErrorEnabled();
        classUnderTest.log(reference, level, MESSAGE, exception);
        Mockito.verify(delegate).error(logMessage, exception);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_WARNING;
        setupSlf4jWarnEnabled();
        classUnderTest.log(reference, level, MESSAGE, exception);
        Mockito.verify(delegate).warn(logMessage, exception);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_INFO;
        setupSlf4jInfoEnabled();
        classUnderTest.log(reference, level, MESSAGE, exception);
        Mockito.verify(delegate).info(logMessage, exception);
        verifyLog_addLogEntry(level, MESSAGE);

        level = LogService.LOG_DEBUG;
        setupOSGiLogDebugEnabled();
        setupSlf4jDebugEnabled();
        classUnderTest.log(reference, level, MESSAGE, exception);
        Mockito.verify(delegate).debug(logMessage, exception);
        verifyLog_addLogEntry(level, MESSAGE);

        setupOSGiLogDebugDisabled();
        setupSlf4jDebugEnabled();
        classUnderTest.log(reference, level, MESSAGE, exception);
        Mockito.verify(delegate).debug(logMessage, exception);
        Mockito.verify(log, Mockito.never()).addLogEntry(Mockito.any(LogEntry.class));
    }


    private void verifyLog_addLogEntry(int level, String message) {
        Mockito.verify(log).addLogEntry(logEntryArgument.capture());
        LogEntry actual = logEntryArgument.getValue();
        Assert.assertEquals(bundle, actual.getBundle());
        Assert.assertEquals(level, actual.getLevel());
        Assert.assertEquals(message, actual.getMessage());

        Mockito.reset(log, delegate);
    }

    private void setupSlf4jErrorEnabled() {
        Mockito.when(delegate.isErrorEnabled()).thenReturn(true);
    }

    private void setupSlf4jInfoEnabled() {
        Mockito.when(delegate.isInfoEnabled()).thenReturn(true);
    }

    private void setupSlf4jWarnEnabled() {
        Mockito.when(delegate.isWarnEnabled()).thenReturn(true);
    }

    private void setupSlf4jDebugEnabled() {
        Mockito.when(delegate.isDebugEnabled()).thenReturn(true);
    }

    private void setupOSGiLogDebugEnabled() {
        Mockito.when(log.debugEnabled()).thenReturn(true);
    }

    private void setupOSGiLogDebugDisabled() {
        Mockito.when(log.debugEnabled()).thenReturn(false);
    }

    private ServiceReference setupServiceReference() {
        ServiceReference reference = Mockito.mock(ServiceReference.class);
        Mockito.when(reference.getBundle()).thenReturn(bundle);
        Mockito.when(reference.toString()).thenReturn(REFERENCE_NAME);
        return reference;
    }
}