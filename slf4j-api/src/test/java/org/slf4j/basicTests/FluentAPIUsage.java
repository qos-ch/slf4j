package org.slf4j.basicTests;

import static org.junit.Assert.assertFalse;

import com.google.errorprone.CompilationTestHelper;
import com.google.errorprone.bugpatterns.CheckReturnValue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class FluentAPIUsage {

    @Test
    public void smoke() {
        String name = "smoke";
        Logger logger = LoggerFactory.getLogger(name);
        logger.atTrace().addKeyValue("a", "n").setCause(new Throwable()).log("hello");
    }


    @Test
    public void smokxce() {
        String name = "smoke";
        Logger logger = LoggerFactory.getLogger(name);
        assertFalse(logger.isEnabledForLevel(Level.DEBUG));
    }

    /**
     * Errorprone (or other static analysis tools) should be able to detect
     * non-terminated fluent methods calls.
     *
     * @see <a href="https://github.com/KengoTODA/errorprone-slf4j/issues/143">Detect non terminated fluent API call (GitHub issue)</a>
     */
    @Test
    public void detectNonTerminatedFluentMethodCall() {
        CompilationTestHelper.newInstance(CheckReturnValue.class, getClass())
                .addSourceLines(
                        "NonTerminatedFluentMethodCall.java",
                        "import org.slf4j.Logger;\n"
                                + "import org.slf4j.LoggerFactory;\n"
                                + "\n"
                                + "class NonTerminatedFluentMethodCall {\n"
                                + "    void atLogLevel() {\n"
                                + "        Logger logger = LoggerFactory.getLogger(getClass());\n"
                                + "        // BUG: Diagnostic contains: Ignored return value of 'atInfo', which is annotated with @CheckReturnValue\n"
                                + "        logger.atInfo();\n"
                                + "        // BUG: Diagnostic contains: Ignored return value of 'addKeyValue', which is annotated with @CheckReturnValue\n"
                                + "        logger.atInfo().addKeyValue(\"k\", \"v\");\n"
                                + "        // BUG: Diagnostic contains: Ignored return value of 'setCause', which is annotated with @CheckReturnValue\n"
                                + "        logger.atInfo().setCause(new Throwable());\n"
                                + "        // BUG: Diagnostic contains: Ignored return value of 'addMarker', which is annotated with @CheckReturnValue\n"
                                + "        logger.atInfo().addMarker(null);\n"
                                + "        // BUG: Diagnostic contains: Ignored return value of 'addArgument', which is annotated with @CheckReturnValue\n"
                                + "        logger.atInfo().addArgument(null);\n"
                                + "    }\n"
                                + "}")
                .doTest();
    }
}
