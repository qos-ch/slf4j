package org.slf4j.jul;

import static org.junit.Assert.assertEquals;

import java.util.function.Supplier;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluentApiInvocationTest {

    ListHandler listHandler = new ListHandler();
    java.util.logging.Logger root = java.util.logging.Logger.getLogger("");
    Level oldLevel;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void setUp() throws Exception {
        oldLevel = root.getLevel();
        root.setLevel(Level.FINE);
        // removeAllHandlers(root);
        root.addHandler(listHandler);
    }

    @After
    public void tearDown() throws Exception {
        root.setLevel(oldLevel);
        removeListHandlers(root);
    }

    void removeListHandlers(java.util.logging.Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler h : handlers) {
            if (h instanceof ListHandler)
                logger.removeHandler(h);
        }
    }

    @Test
    public void singleMessage() {
        String msg = "Hello world.";
        logger.atDebug().log(msg);
        assertLogMessage(msg, 0);
    }

    @Test
    public void messageWithArguments() {
        String msg = "Hello {}.";
        logger.atDebug().addArgument("world").log(msg);
        assertLogMessage("Hello world.", 0);
    }

    @Test
    public void messageWithTwoArguments() {
        int old = 15;
        int t = 16;

        {
            String msg = "Temperature set to {}. Old temperature was {}.";
            logger.atDebug().addArgument(t).addArgument(old).log(msg);
            assertLogMessage("Temperature set to 16. Old temperature was 15.", 0);
        }

        {
            String msg = "Temperature set to {}. Old temperature was {}.";
            logger.atDebug().log(msg, t, old);
            assertLogMessage("Temperature set to 16. Old temperature was 15.", 0);
        }

        {
            String msg = "Temperature set to {}. Old temperature was {}.";
            logger.atDebug().addArgument(t).log(msg, old);
            assertLogMessage("Temperature set to 16. Old temperature was 15.", 0);
        }

        {
            String msg = "Temperature set to {}. Old temperature was {}.";
            logger.atDebug().addArgument(() -> t16()).log(msg, old);
            assertLogMessage("Temperature set to 16. Old temperature was 15.", 0);
        }
    }

    @Test
    public void supplierArguments() {
        Supplier<String> stringSupplier = () -> "world";
        logger.atInfo().addArgument(stringSupplier).log("hello {}");
        assertLogMessage("hello world", 0);
    }

    public int t16() {
        return 16;
    }

    @Test
    public void messageWithThrowable() {
        String msg = "Hello world.";
        Throwable t = new IllegalStateException();
        logger.atDebug().setCause(t).log(msg);
        assertLogMessage("Hello world.", 0);
        assertThrowable(t, 0);
    }

    @Test
    public void messageWithArgumentsAndThrowable() {
        String msg = "Hello {}.";
        Throwable t = new IllegalStateException();

        logger.atDebug().setCause(t).addArgument("world").log(msg);
        assertLogMessage("Hello world.", 0);
        assertThrowable(t, 0);
    }

    @Test
    public void messageWithKeyValuePair() {
        String msg = "Hello world.";
        logger.atDebug().addKeyValue("k", "v").log(msg);
        assertLogMessage("k=v Hello world.", 0);

        int oldT = 15;
        int newT = 16;
        logger.atDebug().addKeyValue("oldT", oldT).addKeyValue("newT", newT).log("Temperature changed.");
        assertLogMessage("oldT=15 newT=16 Temperature changed.", 1);

    }

    private void assertLogMessage(String expected, int index) {
        LogRecord logRecord = listHandler.recordList.get(index);
        Assert.assertNotNull(logRecord);
        assertEquals(expected, logRecord.getMessage());
    }

    private void assertThrowable(Throwable expected, int index) {
        LogRecord logRecord = listHandler.recordList.get(index);
        Assert.assertNotNull(logRecord);
        assertEquals(expected, logRecord.getThrown());
    }
}
