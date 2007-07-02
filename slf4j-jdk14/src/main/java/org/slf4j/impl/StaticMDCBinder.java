package org.slf4j.impl;

import org.slf4j.helpers.NOPMakerAdapter;
import org.slf4j.spi.MDCAdapter;


/**
 * This implementation is bound to {@link NOPMakerAdapter}.
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
     return new NOPMakerAdapter();
  }
  
  public String  getMDCAdapterClassStr() {
    return NOPMakerAdapter.class.getName();
  }
}
