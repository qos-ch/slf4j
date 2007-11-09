package org.slf4j.converter;

import junit.framework.TestCase;

public class TrivialMatcherTest extends TestCase {

  public void testSimpleReplacement() {
    TrivialMatcher trivialMatcher = new TrivialMatcher();
    trivialMatcher.initRules();

    // "import org.slf4j.converter" -- > simple replacement with an unique
    // capturing group
    assertEquals("simple replacement with an unique capturing group",
        trivialMatcher.getReplacement("import org.slf4j.converter"));

    assertEquals("1st group second group 4th group", trivialMatcher
        .getReplacement("first group second group third group 4th group"));

  }

}
