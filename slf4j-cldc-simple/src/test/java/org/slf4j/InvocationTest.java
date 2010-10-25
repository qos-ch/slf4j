/* 
 * Copyright (c) 2004-2007 QOS.CH
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

package org.slf4j;

import java.io.PrintStream;

import junit.framework.TestCase;

/**
 * Test whether invoking the SLF4J API causes problems or not.
 * 
 * @author Ceki Gulcu
 * 
 */
public class InvocationTest extends TestCase {

    PrintStream old= System.err;

    public InvocationTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        System.setErr(new SilentPrintStream(old));
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        System.setErr(old);
    }

    public void test1() {
        Logger logger= LoggerFactory.getLogger("test1");
        logger.debug("Hello world.");
    }

    public void test2() {
        Exception e= new Exception("This is a test exception.");
        Logger logger= LoggerFactory.getLogger("test2");

        logger.debug("Hello world 1.");

        logger.debug("Hello world 2", e);
        logger.info("Hello world 2.");

        logger.warn("Hello world 3.");
        logger.warn("Hello world 3", e);

        logger.error("Hello world 4.");
        logger.error("Hello world 4.", e);
    }

    public void testNull() {
        Logger logger= LoggerFactory.getLogger("testNull");
        logger.debug(null);
        logger.info(null);
        logger.warn(null);
        logger.error(null);

        Exception e= new Exception("This is a test exception.");
        logger.debug(null, e);
        logger.info(null, e);
        logger.warn(null, e);
        logger.error(null, e);
    }
}
