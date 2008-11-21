package org.slf4j.impl;

import org.slf4j.spi.MDCAdapter;


/**
 * This class is only a stub. Real implementations are found in 
 * each SLF4J binding project, e.g. slf4j-nop, slf4j-log4j12 etc.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class StaticMDCBinder {

  
  /**
   * The unique instance of this class.
   */
  public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

  private StaticMDCBinder() {
    throw new UnsupportedOperationException("This code should never make it into the jar");
  }
  
  /**
   * Currently this method always returns an instance of 
   * {@link StaticMDCBinder}.
   */
  public MDCAdapter getMDCA() {
    throw new UnsupportedOperationException("This code should never make it into the jar");
  }
  
  public String  getMDCAdapterClassStr() {
    throw new UnsupportedOperationException("This code should never make it into the jar");
  }
}
