package org.slf4j.simple;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Test class to verify configuration parameters of SimpleLogger
 *
 * @since 2.0.18
 */
public class ConfigParamsTest {


    private ListAppendingOutputStream prepareSink(List<String> outputList) {
        return new ListAppendingOutputStream(outputList);
    }

    @BeforeClass
    static public void resetConfigParams() {
        SimpleLogger.CONFIG_PARAMS.init();
    }

    public Logger createLogger(ListAppendingOutputStream outputStream, Level level, String warnLevelString) {
        SimpleLogger.CONFIG_PARAMS.outputChoice = new OutputChoice(new PrintStream(outputStream));

        SimpleLogger.CONFIG_PARAMS.warnLevelString = warnLevelString;

        SimpleLogger logger = new SimpleLogger(ConfigParamsTest.class.getName());
        logger.currentLogLevel = SimpleLoggerConfiguration.stringToLevel(level.toString());
        return logger;
    }

    @Test
    public void simpleTest(){
        String WARN_LEVEL_STRING = "WXYZ";
        ArrayList<String> outputList = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(outputList), Level.TRACE,  WARN_LEVEL_STRING);

        configuredLogger.warn("This is a test");

        String str0 = outputList.get(0);
        assertTrue(str0.contains(WARN_LEVEL_STRING));

    }
}
