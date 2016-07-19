package org.slf4j.impl;

import com.google.common.collect.Maps;
import edu.stanford.nlp.util.logging.Redwood;
import edu.stanford.nlp.util.logging.Redwood.RedwoodChannels;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class RedWoodLoggerFactory implements ILoggerFactory {

  ConcurrentMap<String, Logger> loggerMap;

  public RedWoodLoggerFactory() {
    loggerMap = Maps.newConcurrentMap();
  }

  @Override
  public Logger getLogger(String name) {
    Logger slf4jLogger = loggerMap.get(name);
    if (slf4jLogger != null) {
      return slf4jLogger;
    } else {
      RedwoodChannels logger = Redwood.channels(name);
      Logger newInstance = new RedWoodLoggerAdapter(name, logger);
      Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
      return oldInstance == null ? newInstance : oldInstance;
    }
  }
}
