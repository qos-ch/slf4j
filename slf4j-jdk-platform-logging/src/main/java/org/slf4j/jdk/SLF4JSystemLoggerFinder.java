package org.slf4j.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses SLF4J's {@link LoggerFactory#getLogger(String)} to get a logger
 * that is adapted for {@link System.Logger}.
 */
public class SLF4JSystemLoggerFinder extends System.LoggerFinder {

    @Override
    public System.Logger getLogger(String name, Module module) {
        // JEP 264[1], which introduced the Platform Logging API,
        // contains the following note:
        //
        //  > An implementation of the LoggerFinder service should make it
        //  > possible to distinguish system loggers (used by system classes
        //  > from the Bootstrap Class Loader (BCL)) and application loggers
        //  > (created by an application for its own usage). This distinction
        //  > is important for platform security. The creator of a logger can
        //  > pass the class or module for which the logger is created to the
        //  > LoggerFinder so that the LoggerFinder can figure out which kind
        //  > of logger to return.
        //
        // If backends support this distinction and once `LoggerFactory`'s API 
        // is updated to forward a module, we should do that here.
        //
        // [1] https://openjdk.java.net/jeps/264
        Logger slf4JLogger = LoggerFactory.getLogger(name);
        return new SLF4JSystemLogger(slf4JLogger);
    }

}