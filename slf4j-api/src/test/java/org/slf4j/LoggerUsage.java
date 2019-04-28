package org.slf4j;

import org.junit.Test;

public class LoggerUsage {

	@Test
	public void test() {
		org.slf4j.Logger logger = LoggerFactory.getLogger("aa");
		logger.atTrace().addKeyValue("a", "n").setCause(new Throwable()).log("aa");
	}

}
