package org.slf4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Random;

import org.apache.log4j.MDC;
import org.junit.Test;

public class MDCTest {

	
    private static Random random = new Random();
	int diff = random.nextInt(1024*8);
	
	@Test
	public void smoke() {
		String key = "MDCFriendTest.smoke"+diff;
		String val = "val"+diff;
		MDC.put(key, val);
		assertEquals(val, MDC.get(key));
		MDC.clear();
		assertNull(MDC.get(key));
		
	}

}
