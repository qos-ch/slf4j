package org.slf4j.simple;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ListAppendingOutputStream extends OutputStream {

    private final StringBuilder stringBuilder = new StringBuilder();
    private final List<String> targetList;

    ListAppendingOutputStream(List<String> list) {this.targetList = list;}


    @Override
    public void write(int b) throws IOException {
        stringBuilder.append((char) b);
    }

    @Override
    public void flush() {
        targetList.add(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
    }
}
