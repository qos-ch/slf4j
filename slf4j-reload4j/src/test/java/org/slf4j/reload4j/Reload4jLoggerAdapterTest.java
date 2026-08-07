package org.slf4j.reload4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.junit.Test;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

public class Reload4jLoggerAdapterTest {
    static Map<String, Object> propertiesFromConvertedEvent(LoggingEvent event) {
        List<org.apache.log4j.spi.LoggingEvent> capturedEvents = new ArrayList<>();

        Logger logger = Logger.getRootLogger();
        logger.removeAllAppenders();
        logger.addAppender(new AppenderSkeleton() {
            @Override
            public void close() {
            }

            @Override
            public boolean requiresLayout() {
                return false;
            }

            @Override
            protected void append(org.apache.log4j.spi.LoggingEvent loggingEvent) {
                capturedEvents.add(loggingEvent);
            }
        });
        Reload4jLoggerAdapter adapter = new Reload4jLoggerAdapter(logger);

        adapter.log(event);

        if (capturedEvents.size() != 1) {
            throw new IllegalStateException();
        }
        return capturedEvents.get(0).getProperties();
    }

    @Test
    public void propertiesAreEmptyWhenNotProvided() {
        LoggingEvent event = new DefaultLoggingEvent(Level.INFO, null);

        assertEquals(emptyMap(), propertiesFromConvertedEvent(event));
    }

    @Test
    public void mdcContentsArePresentInEventProperties() {
        try {
            MDC.put("mdc-key", "value");
            LoggingEvent event = new DefaultLoggingEvent(Level.INFO, null);
            assertEquals(singletonMap("mdc-key", "value"), propertiesFromConvertedEvent(event));
        } finally {
            MDC.remove("mdc-key");
        }
    }

    @Test
    public void keyValuesArePlacedInEventProperties() {
        DefaultLoggingEvent event = new DefaultLoggingEvent(Level.INFO, null);
        event.addKeyValue("kv-key", "value");

        assertEquals(singletonMap("kv-key", "value"), propertiesFromConvertedEvent(event));
    }

    @Test
    public void mdcAndKeyValuesAreMergedInEventProperties() {
        try {
            MDC.put("mdc-key", "value");
            DefaultLoggingEvent event = new DefaultLoggingEvent(Level.INFO, null);
            event.addKeyValue("kv-key", "value");
            Map<String, String> expectedProperties = new HashMap<>();
            expectedProperties.put("mdc-key", "value");
            expectedProperties.put("kv-key", "value");

            assertEquals(expectedProperties,
                    propertiesFromConvertedEvent(event));
        } finally {
            MDC.remove("mdc-key");
        }
    }

    @Test
    public void keyValuePropertiesOverrideMdc() {
        try {
            MDC.put("clashing-key", "mdc-value");
            DefaultLoggingEvent event = new DefaultLoggingEvent(Level.INFO, null);
            event.addKeyValue("clashing-key", "kv-value");
            assertEquals(
                    singletonMap("clashing-key", "kv-value"),
                    propertiesFromConvertedEvent(event));
        } finally {
            MDC.remove("clashing-key");
        }
    }
}
