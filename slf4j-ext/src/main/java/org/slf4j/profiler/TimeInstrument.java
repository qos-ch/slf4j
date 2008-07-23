package org.slf4j.profiler;

public interface TimeInstrument {

  /**
   * All time instruments are named entities.
   * @return the name of this instrument
   */
  String getName();
  
  
  TimeInstrumentStatus getStatus();
  void start(String name);
  TimeInstrument stop();

  /**
   * Time elapsed between start and stop, in nanoseconds.
   * 
   * @return time elapsed in nanoseconds
   */
  long elapsedTime();
  
  void print();
}
