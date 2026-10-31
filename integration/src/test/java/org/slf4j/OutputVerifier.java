/*
 * Copyright (C) 2004-2026, QOS.ch (Switzerland)
 * All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *  without limitation  the rights to  use, copy, modify,  merge, publish,
 *  distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  permit persons to whom the Software  is furnished to do so, subject to
 *  the following conditions:
 *
 *  The  above  copyright  notice  and  this permission  notice  shall  be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 *  EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 *  MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.slf4j;

import static junit.framework.Assert.assertTrue;

public class OutputVerifier {

    static void noProvider(StringPrintStream sps) {
        dump(sps);
        int lineCount = sps.stringList.size();
        assertTrue("number of lines should be 6 but was " + lineCount, lineCount == 6);

        // expected output: (version 1.8)
        // SLF4J: No SLF4J providers were found.
        // SLF4J: Defaulting to no-operation (NOP) logger implementation
        // SLF4J: See http://www.slf4j.org/codes.html#noProviders for further details.
        // SLF4J: Class path contains SLF4J bindings targeting slf4j-api versions prior to 1.8.
        // SLF4J: Ignoring binding found at
        // [jar:file:..../slf4j-simple-1.4.2.jar!/org/slf4j/impl/StaticLoggerBinder.class]
        // SLF4J: See http://www.slf4j.org/codes.html#ignoredBindings for an explanation.

        {
            String s = (String) sps.stringList.get(0);
            assertTrue(s.contains("No SLF4J providers were found."));
        }
        {
            String s = (String) sps.stringList.get(1);
            assertTrue(s.contains("Defaulting to no-operation (NOP) logger implementation"));
        }
        {
            String s = (String) sps.stringList.get(2);
            assertTrue(s.contains("See https://www.slf4j.org/codes.html#noProviders for further details."));
        }

        {
            String s = (String) sps.stringList.get(3);
            assertTrue(s.contains("Class path contains SLF4J bindings targeting slf4j-api versions 1.7.x or earlier."));
        }

        {
            String s = (String) sps.stringList.get(4);
            assertTrue(s.contains("Ignoring binding found at"));
        }
        {
            String s = (String) sps.stringList.get(5);
            assertTrue(s.contains("See https://www.slf4j.org/codes.html#ignoredBindings for an explanation"));
        }
    }

    public static void dump(StringPrintStream sps) {
        for (String s : sps.stringList) {
            System.out.println(s);
        }
    }
}
