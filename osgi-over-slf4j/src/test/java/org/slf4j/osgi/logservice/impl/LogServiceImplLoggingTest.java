/*
 * Copyright (c) 2004-2005 QOS.ch
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

package org.slf4j.osgi.logservice.impl;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.reflect.Whitebox.invokeMethod;
import static org.powermock.reflect.Whitebox.setInternalState;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.osgi.logservice.impl.LogServiceImpl;

@RunWith(PowerMockRunner.class)
public class LogServiceImplLoggingTest {

	private LogService logService;
	private Bundle bundle;
	private ServiceReference sr;
	private Logger loggerMock;

	private final String TEST_MESSAGE = "TEST";
	private final Exception TEST_EXCEPTION = new Exception("TEST");

	@Before
	public void setUp() throws Exception {
		bundle = mock(Bundle.class);
		sr = mock(ServiceReference.class);
		loggerMock = mock(Logger.class);
		logService = new LogServiceImpl(bundle);
		setInternalState(logService, "delegate", loggerMock);
	}

	@Test
	public void testLogIntStringDebug() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(LogService.LOG_DEBUG, TEST_MESSAGE);

		// then
		verify(loggerMock).debug(TEST_MESSAGE);
	}

	@Test
	public void testLogIntStringError() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(LogService.LOG_ERROR, TEST_MESSAGE);

		// then
		verify(loggerMock).error(TEST_MESSAGE);
	}

	@Test
	public void testLogIntStringInfo() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(LogService.LOG_INFO, TEST_MESSAGE);

		// then
		verify(loggerMock).info(TEST_MESSAGE);
	}

	@Test
	public void testLogIntStringWarning() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(LogService.LOG_WARNING, TEST_MESSAGE);

		// then
		verify(loggerMock).warn(TEST_MESSAGE);
	}

	@Test
	public void testLogIntStringDefault() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(Integer.MAX_VALUE, TEST_MESSAGE);

		// then
		verifyZeroInteractions(loggerMock);
	}

	@Test
	public void testLogIntStringThrowableDebug() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(LogService.LOG_DEBUG, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verify(loggerMock).debug(TEST_MESSAGE, TEST_EXCEPTION);
	}

	@Test
	public void testLogIntStringThrowableError() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(LogService.LOG_ERROR, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verify(loggerMock).error(TEST_MESSAGE, TEST_EXCEPTION);
	}

	@Test
	public void testLogIntStringThrowableInfo() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(LogService.LOG_INFO, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verify(loggerMock).info(TEST_MESSAGE, TEST_EXCEPTION);
	}

	@Test
	public void testLogIntStringThrowableWarning() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(LogService.LOG_WARNING, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verify(loggerMock).warn(TEST_MESSAGE, TEST_EXCEPTION);
	}

	@Test
	public void testLogIntStringThrowableDefault() throws IllegalArgumentException, IllegalAccessException {
		// when
		logService.log(Integer.MAX_VALUE, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verifyZeroInteractions(loggerMock);
	}

	@Test
	public void testLogServiceReferenceIntStringDebug() throws Exception {
		// setup
		when(loggerMock.isDebugEnabled()).thenReturn(true);

		// when
		logService.log(sr, LogService.LOG_DEBUG, TEST_MESSAGE);

		// then
		verify(loggerMock).debug((String) invokeMethod(logService, "createMessage", sr, TEST_MESSAGE));
	}

	@Test
	public void testLogServiceReferenceIntStringError() throws Exception {
		// setup
		when(loggerMock.isErrorEnabled()).thenReturn(true);

		// when
		logService.log(sr, LogService.LOG_ERROR, TEST_MESSAGE);

		// then
		verify(loggerMock).error((String) invokeMethod(logService, "createMessage", sr, TEST_MESSAGE));
	}

	@Test
	public void testLogServiceReferenceIntStringInfo() throws Exception {
		// setup
		when(loggerMock.isInfoEnabled()).thenReturn(true);

		// when
		logService.log(sr, LogService.LOG_INFO, TEST_MESSAGE);

		// then
		verify(loggerMock).info((String) invokeMethod(logService, "createMessage", sr, TEST_MESSAGE));
	}

	@Test
	public void testLogServiceReferenceIntStringWarning() throws Exception {
		// setup
		when(loggerMock.isWarnEnabled()).thenReturn(true);

		// when
		logService.log(sr, LogService.LOG_WARNING, TEST_MESSAGE);

		// then
		verify(loggerMock).warn((String) invokeMethod(logService, "createMessage", sr, TEST_MESSAGE));
	}

	@Test
	public void testLogServiceReferenceIntStringDefault() throws Exception {
		// when
		logService.log(sr, Integer.MAX_VALUE, TEST_MESSAGE);

		// then
		verifyZeroInteractions(loggerMock);
	}

	@Test
	public void testLogServiceReferenceIntStringThrowableDebug() throws Exception {
		// setup
		when(loggerMock.isDebugEnabled()).thenReturn(true);

		// when
		logService.log(sr, LogService.LOG_DEBUG, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verify(loggerMock).debug((String) invokeMethod(logService, "createMessage", sr, TEST_MESSAGE), TEST_EXCEPTION);
	}

	@Test
	public void testLogServiceReferenceIntStringThrowableError() throws Exception {
		// setup
		when(loggerMock.isErrorEnabled()).thenReturn(true);

		// when
		logService.log(sr, LogService.LOG_ERROR, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verify(loggerMock).error((String) invokeMethod(logService, "createMessage", sr, TEST_MESSAGE), TEST_EXCEPTION);
	}

	@Test
	public void testLogServiceReferenceIntStringThrowableInfo() throws Exception {
		// setup
		when(loggerMock.isInfoEnabled()).thenReturn(true);

		// when
		logService.log(sr, LogService.LOG_INFO, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verify(loggerMock).info((String) invokeMethod(logService, "createMessage", sr, TEST_MESSAGE), TEST_EXCEPTION);
	}

	@Test
	public void testLogServiceReferenceIntStringThrowableWarning() throws Exception {
		// setup
		when(loggerMock.isWarnEnabled()).thenReturn(true);

		// when
		logService.log(sr, LogService.LOG_WARNING, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verify(loggerMock).warn((String) invokeMethod(logService, "createMessage", sr, TEST_MESSAGE), TEST_EXCEPTION);
	}

	@Test
	public void testLogServiceReferenceIntStringThrowableDefault() throws Exception {
		// when
		logService.log(sr, Integer.MAX_VALUE, TEST_MESSAGE, TEST_EXCEPTION);

		// then
		verifyZeroInteractions(loggerMock);
	}
}
