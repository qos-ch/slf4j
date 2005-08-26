/*
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.ch
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


package org.apache.commons.logging;

import junit.framework.TestCase;


public class InvokeJCLWithNOPTest extends TestCase {

  public void testIsEnabledAPI() {
    // assume that we are running over slf4j-nop
    Log log = LogFactory.getLog(InvokeJCLWithNOPTest.class);
    assertFalse(log.isTraceEnabled());
    assertFalse(log.isDebugEnabled());
    assertFalse(log.isInfoEnabled());
    assertFalse(log.isWarnEnabled());
    assertFalse(log.isErrorEnabled());
    assertFalse(log.isFatalEnabled());
  }
  
  public void testPrintAPI() {
    Log log = LogFactory.getLog(InvokeJCLWithNOPTest.class);
    Exception e = new Exception("just testing");
    log.trace("trace message");
    log.debug("debug message");
    log.info("info  message");
    log.warn("warn message");
    log.error("error message");
    log.fatal("fatal message");
    
    log.trace("trace message", e);
    log.debug("debug message", e);
    log.info("info  message", e);
    log.warn("warn message", e);
    log.error("error message", e);
    log.fatal("fatal message", e);
  }
}
