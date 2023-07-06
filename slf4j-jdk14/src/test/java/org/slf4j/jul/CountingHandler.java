package org.slf4j.jul;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CountingHandler extends Handler {

    final AtomicLong eventCount = new AtomicLong(0);

    @Override
    public void publish(LogRecord record) {
        eventCount.getAndIncrement();
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }

}
