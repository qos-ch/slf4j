/* 
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.CH
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

import junit.framework.TestCase;


/**
 * Test whether invoking the SLF4J API causes problems or not.
 * 
 * @author Ceki Gulcu
 *
 */
public class InvocationTest extends TestCase {

  public InvocationTest (String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void test1() {
    Logger logger = LoggerFactory.getLogger("test1");
    logger.debug("Hello world.");
  }
  
  public void test2() {
    Integer i1 = new Integer(1);
    Integer i2 = new Integer(2);
    Integer i3 = new Integer(3);
    Exception e = new Exception("This is a test exception.");
    Logger logger = LoggerFactory.getLogger("test2");
    
    logger.debug("Hello world 1.");
    logger.debug("Hello world {}", i1);
    logger.debug("val={} val={}", i1, i2);
    logger.debug("val={} val={} val={}", new Object[]{i1, i2, i3});
    
    logger.debug("Hello world 2", e);
    logger.info("Hello world 2.");
 
    
    logger.warn("Hello world 3.");
    logger.warn("Hello world 3", e);
 
  
    logger.error("Hello world 4.");
    logger.error("Hello world {}", new Integer(3)); 
    logger.error("Hello world 4.", e);
  }
  
  public void testNull() {
    Logger logger = LoggerFactory.getLogger("testNull");
    logger.debug(null);
    logger.info(null);
    logger.warn(null);
    logger.error(null);
    
    Exception e = new Exception("This is a test exception.");
    logger.debug(null, e);
    logger.info(null, e);
    logger.warn(null, e);
    logger.error(null, e);
  }
  
  public void testMarker() {
    Logger logger = LoggerFactory.getLogger("testMarker");
    Marker blue = MarkerFactory.getMarker("BLUE");
    logger.debug(blue, "hello");
    logger.info(blue, "hello");
    logger.warn(blue, "hello");
    logger.error(blue, "hello");
    
    logger.debug(blue, "hello {}", "world");
    logger.info(blue, "hello {}", "world");
    logger.warn(blue, "hello {}", "world");
    logger.error(blue, "hello {}", "world");

    logger.debug(blue, "hello {} and {} ", "world", "universe");
    logger.info(blue, "hello {} and {} ", "world", "universe");
    logger.warn(blue, "hello {} and {} ", "world", "universe");
    logger.error(blue, "hello {} and {} ", "world", "universe");
  }
  
  public void testMDC() {
    MDC.put("k", "v");
    assertNotNull(MDC.get("k"));
    assertEquals("v", MDC.get("k"));

    MDC.remove("k");
    assertNull(MDC.get("k"));

    MDC.put("k1", "v1");
    assertEquals("v1", MDC.get("k1"));
    MDC.clear();
    assertNull(MDC.get("k1"));

    try {
      MDC.put(null, "x");
      fail("null keys are invalid");
    } catch (IllegalArgumentException e) {
    }
  }
}
