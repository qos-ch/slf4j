// TOTO

package org.slf4j;

import org.slf4j.spi.LoggerFactoryBinder;

/**
 * @author ceki
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
class SystemPropBinder implements LoggerFactoryBinder {
  String factoryFactoryClassName = null;

  /**
   * Fetch the appropriate ILoggerFactory as intructed by the system propties.
   * 
   * @return The appropriate ILoggerFactory instance as directed from the system
   *         properties
   */
  public ILoggerFactory getLoggerFactory() {

    try {
      if (getLoggerFactoryClassStr() == null) {
        return null;
      }

      Class factoryFactoryClass = Class.forName(getLoggerFactoryClassStr());
      Class[] EMPTY_CLASS_ARRAY = {};
      java.lang.reflect.Method factoryFactoryMethod = factoryFactoryClass
          .getDeclaredMethod(Constants.LOGGER_FACTORY_FACTORY_METHOD_NAME,
              EMPTY_CLASS_ARRAY);
      ILoggerFactory loggerFactory = (ILoggerFactory) factoryFactoryMethod
          .invoke(null, null);
      return loggerFactory;
    } catch (Exception e) {
      Util.reportFailure("Failed to fetch ILoggerFactory instnace using the "
          + factoryFactoryClassName + " class.", e);

    }

    // we could not get an adapter
    return null;
  }

  public String getLoggerFactoryClassStr() {
    if (factoryFactoryClassName == null) {
      try {
        factoryFactoryClassName = System
            .getProperty(Constants.LOGGER_FACTORY_FACTORY_METHOD_NAME);
      } catch (Exception e) {
        Util.reportFailure("Failed to fetch "
            + Constants.LOGGER_FACTORY_FACTORY_METHOD_NAME
            + " system property.", e);
      }
    }
    return factoryFactoryClassName;
  }
}