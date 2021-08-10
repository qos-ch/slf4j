package org.slf4j.jdk;

import java.lang.System.Logger;

import java.lang.System.Logger.Level;
import java.lang.System.LoggerFinder;

import org.junit.Test;

public class PlatformLogging {

    
    @Test
    public void smoke() {
        LoggerFinder finder = System.LoggerFinder.getLoggerFinder();
        Logger systemLogger = finder.getLogger("x", null);
        systemLogger.log(Level.INFO, "hello");
    }
}
