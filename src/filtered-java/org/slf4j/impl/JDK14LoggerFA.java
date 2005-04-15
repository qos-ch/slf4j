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

package org.slf4j.impl;

import org.slf4j.LoggerFactoryAdapter;
import org.slf4j.ULogger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class JDK14LoggerFA implements LoggerFactoryAdapter {
  Map map;

  public JDK14LoggerFA() {
    map = new HashMap();
  }

  /* (non-Javadoc)
   * @see org.slf4j.LoggerFactoryAdapter#getLogger(java.lang.String)
   */
  public ULogger getLogger(String name) {
    ULogger ulogger = (ULogger) map.get(name);
    if (ulogger == null) {
      Logger logger = Logger.getLogger(name);
      ulogger = new JDK14Logger(logger);
      map.put(name, ulogger);
    }
    return ulogger;
  }

  /* (non-Javadoc)
   * @see org.slf4j.LoggerFactoryAdapter#getLogger(java.lang.String, java.lang.String)
   */
  public ULogger getLogger(String domainName, String subDomainName) {
    return getLogger(domainName);
  }
}
