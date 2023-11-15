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
