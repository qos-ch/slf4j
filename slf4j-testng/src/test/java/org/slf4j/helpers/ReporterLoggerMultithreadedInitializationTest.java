/**
 * Copyright (c) 2004-2016 QOS.ch
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
package org.slf4j.helpers;

import static org.testng.Assert.fail;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

import org.slf4j.LoggerFactoryFriend;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReporterLoggerMultithreadedInitializationTest {
	
	@Test
	public void executeTests() {
		
		TestListenerAdapter tla = new TestListenerAdapter();
		
		TestNG testNG = new TestNG();
		testNG.setTestClasses(new Class[]{MultithreadedEmbeddedTest.class});
		testNG.addListener((ITestNGListener) tla);
		testNG.run();
		
		List<ITestResult> resultList = tla.getFailedTests();
		if (!resultList.isEmpty()) {
			fail(resultList.get(0).getThrowable().getMessage());
		}
		
	}

	private static class MultithreadedEmbeddedTest extends MultithreadedInitializationTest {
	    
	    @BeforeMethod
	    public void setup(Method method) {
	        System.out.println("THREAD_COUNT=" + THREAD_COUNT);
	        LoggerFactoryFriend.reset();
	    }
	    
	    @Test
	    public void multithreadedEmbedded() throws InterruptedException, BrokenBarrierException {
	    	multiThreadedInitialization();
	    }
	
	    @AfterMethod
	    public void tearDown(Method method) {
	        LoggerFactoryFriend.reset();
	    }
	
	    @Override
	    protected long getRecordedEventCount() {
	    	return Reporter.getOutput().size();
	    };
	}
	
}
