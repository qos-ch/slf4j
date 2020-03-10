package org.slf4j.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JSystemLoggerFinder extends System.LoggerFinder {

    @Override
    public System.Logger getLogger(String name, Module module) {
        // TODO do we need to use the `module`?
        Logger slf4JLogger = LoggerFactory.getLogger(name);
        return new SLF4JSystemLogger(slf4JLogger);
    }

}