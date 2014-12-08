package org.slf4j;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.shape.Square;

public class ShapeTest {

    @Test
    public void verifyLoggerDefinedInBaseWithOverridenGetClassMethod() {
       Square square = new Square();
       System.out.println(square.logger.getName());

    }


    private static void setTrialEnabled(boolean enabled) {
        // The system property is read into a static variable at initialization time
        // so we cannot just reset the system property to test this feature.
        // Therefore we set the variable directly.
        LoggerFactory.AUTO_NAMED_LOGGER_FIELD_TRIAL = enabled;
    }
}
