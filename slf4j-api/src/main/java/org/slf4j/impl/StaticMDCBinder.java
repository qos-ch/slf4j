/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.impl;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;

/**
 * This implementation is bound to {@link NOPMDCAdapter}.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Thomas Pérennou (ServiceLoader use)
 */
public class StaticMDCBinder {

  /**
   * The unique instance of this class.
   */
  public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();
  private final MDCAdapter mdcAdapter;

  private StaticMDCBinder () {
    Iterator<MDCAdapter> mdcAdapterIterator = ServiceLoader.load(MDCAdapter.class).iterator();
    mdcAdapter = mdcAdapterIterator.hasNext()? mdcAdapterIterator.next(): new NOPMDCAdapter();
    if (mdcAdapterIterator.hasNext()) {
      throw new IllegalStateException ("SLF4J contains more than one declared MDCAdapter");
    }
  }

  /**
   * Currently this method always returns an instance of 
   * {@link StaticMDCBinder}.
   */
  public MDCAdapter getMDCA() {
    return mdcAdapter;
  }

  public String getMDCAdapterClassStr() {
    return mdcAdapter.getClass().getName();
  }
}
