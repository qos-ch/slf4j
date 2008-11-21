package org.slf4j.profiler;


/**
 * This interface sets the methods that must be implemented by 
 * {@link Profiler} and {@link  StopWatch} classes. It settles the 
 * general feel of the profiler package.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *
 */
public interface TimeInstrument {

  /**
   * All time instruments are named entities.
   * @return the name of this instrument
   */
  String getName();
  
  
  TimeInstrumentStatus getStatus();
  
  /**
   * Start tis time instrument.
   * 
   * @param name
   */
  void start(String name);
  
  /**
   * Stop this time instrument.
   * 
   * @return this
   */
  TimeInstrument stop();

  /**
   * Time elapsed between start and stop, in nanoseconds.
   * 
   * @return time elapsed in nanoseconds
   */
  long elapsedTime();
  
  /**
   * Print information about this time instrument on the console.
   */
  void print();
  
  /**
   * If the time instrument has an associated logger, then log information about 
   * this time instrument. Note that {@link StopWatch} instances cannot log while {@link Profiler}
   * instances can.
   */
  void log();
}
