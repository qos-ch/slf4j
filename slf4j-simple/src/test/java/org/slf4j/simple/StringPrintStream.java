package org.slf4j.simple;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringPrintStream extends PrintStream {

    public static final String LINE_SEP = System.getProperty("line.separator");
    PrintStream other;
    boolean duplicate = false;

    List<String> stringList = Collections.synchronizedList(new ArrayList<String>());

    public StringPrintStream(PrintStream ps, boolean duplicate) {
        super(ps);
        other = ps;
        this.duplicate = duplicate;
    }

    public StringPrintStream(PrintStream ps) {
        this(ps, false);
    }

    public void print(String s) {
        if (duplicate)
            other.print(s);
        stringList.add(s);
    }

    public void println(String s) {
        if (duplicate)
            other.println(s);
        stringList.add(s);
    }

    public void println(Object o) {
        if (duplicate)
            other.println(o);
        stringList.add(o.toString());
    }
}