package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerBinder implements LoggerFactoryBinder {

  private static final StaticLoggerBinder instance = new StaticLoggerBinder();
  private final ILoggerFactory loggerFactory;
  private static final String loggerFactoryClassStr = RedWoodLoggerFactory.class.getName();


  public static StaticLoggerBinder getSingleton() {
    return instance;
  }

  private StaticLoggerBinder() {
    loggerFactory = new RedWoodLoggerFactory();
  }

  @Override
  public ILoggerFactory getLoggerFactory() {
    return loggerFactory;
  }

  @Override
  public String getLoggerFactoryClassStr() {
    return loggerFactoryClassStr;
  }
}
