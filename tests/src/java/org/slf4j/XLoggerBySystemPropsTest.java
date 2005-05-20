/*
 * Created on May 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.slf4j;

import org.slf4j.impl.XLogger;

import junit.framework.TestCase;

/**
 * @author ceki
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XLoggerBySystemPropsTest extends TestCase {

	public void testBasic() {
		System.setProperty(Constants.LOGGER_FA_FACTORY, "org.slf4j.XLoggerFAFactory");
		
		Logger logger = LoggerFactory.getLogger("foo");
		if(! (logger instanceof XLogger)) {
			fail("returned logger of type "+logger.getClass().getName()+" is not of type SimpleLogger");
		}
	}
}
