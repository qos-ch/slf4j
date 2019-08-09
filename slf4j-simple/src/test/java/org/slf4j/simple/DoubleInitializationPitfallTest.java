package org.slf4j.simple;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.MarkerFactory;

public class DoubleInitializationPitfallTest {

	
	@Test
	public void verifyImpactOfMarkerFactory() {
		ILoggerFactory firstFactory = LoggerFactory.getILoggerFactory();
		MarkerFactory.getMarker("DOUBLE_INIT");
		ILoggerFactory secondFactory = LoggerFactory.getILoggerFactory();
		
		if(firstFactory != secondFactory) {
			fail("MarkerFactory.getMarker causes multiple provider initialization");
		}
	}

	@Test
	public void verifyImpactOfMDC() {
		ILoggerFactory firstFactory = LoggerFactory.getILoggerFactory();
		MDC.put("DoubleInitializationPitfallTest", "a");
		ILoggerFactory secondFactory = LoggerFactory.getILoggerFactory();
		
		if(firstFactory != secondFactory) {
			fail("MarkerFactory.getMarker causes multiple provider initialization");
		}
	}


}
