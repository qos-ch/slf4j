package org.dummy;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

class SingleMessageHandler extends Handler {

    LogRecord logRecord = null;

    public void close() throws SecurityException {
    }

    public void flush() {

    }

    public void publish(LogRecord message) {
        if (logRecord == null) {
            this.logRecord = message;
        } else {
            throw new IllegalStateException("accepts only a single message");
        }
    }

}
