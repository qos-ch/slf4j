package org.slf4j.simple;

import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.test.LoggerTestSuite;

import java.io.PrintStream;

public class AcceptanceTest extends LoggerTestSuite {

    @Override
    public Logger createLogger(ListAppendingOutputStream outputStream, Level level) {
        SimpleLogger.CONFIG_PARAMS.outputChoice = new OutputChoice(new PrintStream(outputStream));

        SimpleLogger logger = new SimpleLogger("TestSuiteLogger");
        logger.currentLogLevel = SimpleLoggerConfiguration.stringToLevel(level.toString());
        return logger;
    }

    @Override
    public String extractMessage(String message) {
        return message
                .split("\n")[0]
                .split("- ")[1];
    }

    @Override
    public String extractExceptionMessage(String message) {
        String[] logLines = message.split("\n");

        if (logLines.length < 2) {
            return null;
        }
        String exceptionLine = logLines[1];
        return exceptionLine.split(": ")[1];
    }

    @Override
    public String extractExceptionType(String message) {
        String[] logLines = message.split("\n");

        if (logLines.length < 2) {
            return null;
        }
        String exceptionLine = logLines[1];
        return exceptionLine.split(": ")[0];
    }

}
