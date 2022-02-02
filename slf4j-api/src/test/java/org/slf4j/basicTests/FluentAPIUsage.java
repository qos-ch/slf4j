package org.slf4j.basicTests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class FluentAPIUsage {

    @Test
    public void smoke() { 
        String name = "smoke";
        Logger logger = LoggerFactory.getLogger(name);
        logger.atTrace().addKeyValue("a", "n").setCause(new Throwable()).log("hello");
    }
    

    @Test
    public void smokxce() {
        String name = "smoke";
        Logger logger = LoggerFactory.getLogger(name);
        assertFalse(logger.isEnabledForLevel(Level.DEBUG));
    }

    @Test
    public void fluentApiWithLambda() {
        Logger logger = LoggerFactory.getLogger("fluentApiWithLambda");
        logger.atDebug(l-> l.setMessage("Hello {}")
                            .addArgument("world")
                            .setCause(new Throwable()));

    }

}
