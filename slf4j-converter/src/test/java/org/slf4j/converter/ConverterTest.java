package org.slf4j.converter;

import junit.framework.TestCase;

public class ConverterTest extends TestCase {

  public void testMatch() {
    Converter converter = new Converter();
    converter.init();
    String smatch = "converter";
    assertNotNull(converter.isMatching(smatch));
    smatch = " converter";
    assertNotNull(converter.isMatching(smatch));
    smatch = " converter toto";
    assertNotNull(converter.isMatching(smatch));
    smatch = "blabla converter toto";
    assertNotNull(converter.isMatching(smatch));
    smatch = "con verter";
    assertNull(converter.isMatching(smatch));
    }

}
