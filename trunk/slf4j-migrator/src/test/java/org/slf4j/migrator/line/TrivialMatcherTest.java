package org.slf4j.migrator.line;

import org.slf4j.migrator.line.LineConverter;

import junit.framework.TestCase;

public class TrivialMatcherTest extends TestCase {

  public void testSimpleReplacement() {
    LineConverter trivialLC = new LineConverter(new TrivialMatcher());


    // "import org.slf4j.converter" -- > simple replacement with an unique
    // capturing group
    assertEquals("simple replacement with an unique capturing group",
        trivialLC.getOneLineReplacement("import org.slf4j.converter"));

    assertEquals("1st group second group 4th group", trivialLC
        .getOneLineReplacement("first group second group third group 4th group"));

  }

}
