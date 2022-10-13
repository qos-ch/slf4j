package org.slf4j.jul;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ListHandler extends Handler {

    public List<LogRecord> recordList = new ArrayList<>();

    @Override
    public void publish(LogRecord record) {
        recordList.add(record);
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}