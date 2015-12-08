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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.PackageAdmin;

import java.sql.SQLException;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class LogEntryExceptionTest {

  @Mock
  PackageAdmin packageAdmin;
  @Mock
  Bundle bundle;

  @Test
  public void testFromWithSystemException() throws Exception {
    Throwable expected = new SQLException("The message");

    Throwable actual = LogEntryException.from(expected, packageAdmin);

    Assert.assertSame(expected, actual);
  }

  @Test
  public void testFromWithBundleException() throws Exception {
    Mockito.when(packageAdmin.getBundle(SQLException.class))
            .thenReturn(bundle);
    Throwable expected = new SQLException("The message");

    Throwable actual = LogEntryException.from(expected, packageAdmin);

    Assert.assertNotSame(expected, actual);
    Assert.assertTrue(Arrays.equals(expected.getStackTrace(), actual.getStackTrace()));
    Assert.assertEquals(expected.getMessage(), actual.getMessage());
    Assert.assertEquals(expected.getLocalizedMessage(), actual.getLocalizedMessage());
  }
}