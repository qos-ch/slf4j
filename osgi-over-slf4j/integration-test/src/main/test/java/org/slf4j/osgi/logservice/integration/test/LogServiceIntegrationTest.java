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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

import javax.inject.Inject;

/**
 * Tests the following:
 *  Registering a LogListener
 *  Using LogService to send LogEntries; tests receiving them asynchronously (use Waiter)
 *  Using LogReaderService to get the list of LogEntries
 *  Tests that EventAdmin gets events.
 *  Tests unregistering a LogListener
 */
@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy
public class LogServiceIntegrationTest {

    @Inject
    LogService logService;
    @Inject
    LogReaderService logReaderService;
    @Inject
    EventAdmin eventAdmin;


    @Configuration
    public Option[] configure() {
        return CoreOptions.options(
                CoreOptions.junitBundles(),
                CoreOptions.mavenBundle("org.slf4j", "osgi-over-slf4j")
        );
    }

    @Test
    public void testLogMessage() {
        System.out.println(logService);
        logService.log(LogService.LOG_INFO, "a message");
    }
}
