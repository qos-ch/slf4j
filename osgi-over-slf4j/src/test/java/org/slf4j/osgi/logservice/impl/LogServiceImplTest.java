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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.powermock.reflect.Whitebox.invokeMethod;

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
public class LogServiceImplTest {

	private LogService logService;
	private Bundle bundle;
	private ServiceReference sr;

	private final String TEST_MESSAGE = "TEST";

	@Before
	public void setUp() throws Exception {
		bundle = mock(Bundle.class);
		sr = mock(ServiceReference.class);
		logService = new LogServiceImpl(bundle);
	}

	@Test
	public void testLogServiceImpl() {
		// when
		Logger logServiceLogger = getInternalState(logService, "delegate");

		// then
		assertNotNull(logServiceLogger);
		assertThat(logServiceLogger, instanceOf(Logger.class));
	}

	@Test
	public void testLogServiceCreateMessage() throws Exception {
		// when
		String resultString = invokeMethod(logService, "createMessage", sr, TEST_MESSAGE);

		// then
		assertEquals('[' + sr.toString() + ']' + TEST_MESSAGE, resultString);
	}

	@Test
	public void testLogServiceCreateMessageNullSr() throws Exception {
		// when
		ServiceReference srNull = null;
		String resultString = invokeMethod(logService, "createMessage", srNull, TEST_MESSAGE);

		// then
		assertEquals(getInternalState(LogServiceImpl.class, "UNKNOWN") + TEST_MESSAGE, resultString);
	}
}
