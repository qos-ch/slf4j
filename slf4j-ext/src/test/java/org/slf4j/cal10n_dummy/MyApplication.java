package org.slf4j.cal10n_dummy;

import java.util.Locale;

import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;

public class MyApplication {

  // create a message conveyor for a given locale
  IMessageConveyor messageConveyor = new MessageConveyor(Locale.JAPAN);
  
  // create the LogLoggerFactory
  LocLoggerFactory llFactory_uk = new LocLoggerFactory(messageConveyor);
  
  // create a locLogger
  LocLogger locLogger = llFactory_uk.getLocLogger(this.getClass());
  

  public void applicationStart() {
    locLogger.info(Production.APPLICATION_STARTED);
    // ..
  }
  
  public void applicationStop() {
    locLogger.info(Production.APPLICATION_STOPPED);
    // ...
  }
}
