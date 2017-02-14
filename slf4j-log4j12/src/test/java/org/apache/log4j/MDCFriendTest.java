package org.apache.log4j;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.slf4j.helpers.Util;

public class MDCFriendTest {

	
    private static Random random = new Random();
	int diff = random.nextInt(1024*8);
	
	@Test
	public void smoke() {
		if(Util.getJavaMajorVersion() < 9)
			return;

		MDCFriend.fixForJava9();
		String key = "MDCFriendTest.smoke"+diff;
		String val = "val"+diff;
		MDC.put(key, val);
		assertEquals(val, MDC.get(key));
		MDC.clear();
		assertNull(MDC.get(key));
		
	}

}
