/**
 * Copyright (c) 2004-2021 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.helpers;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Copied from org.slfj.helpers.
 *  
 * Currently it is not possible to use test-jar from tests running on the module-path.
 * 
 * @author ceki
 */
public class StringPrintStream extends PrintStream {

    public static final String LINE_SEP = System.getProperty("line.separator");
    PrintStream other;
    boolean duplicate = false;

    public List<String> stringList = Collections.synchronizedList(new ArrayList<>());

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
