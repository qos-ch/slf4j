package org.slf4j;

import org.junit.Test;

public class LoggerUsage {

	@Test
	public void test() {
		org.slf4j.Logger logger = LoggerFactory.getLogger("aa");
		logger.atTrace().setCause(new Throwable()).addKeyValue("a", "n").log("aa");;
	}

}
