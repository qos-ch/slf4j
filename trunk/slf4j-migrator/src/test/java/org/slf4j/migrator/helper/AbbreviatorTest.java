package org.slf4j.migrator.helper;

import org.slf4j.migrator.helper.Abbreviator;

import junit.framework.TestCase;

public class AbbreviatorTest extends TestCase {

  static final char FS = '/';
  static final String INPUT_0 = "/abc/123456/ABC";
  static final String INPUT_1 = "/abc/123456/xxxxx/ABC";

  RandomHelper rh = new RandomHelper(FS);

  public AbbreviatorTest(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testSmoke() {
    {
      Abbreviator abb = new Abbreviator(2, 100, FS);
      String r = abb.abbreviate(INPUT_0);
      assertEquals(INPUT_0, r);
    }

    {
      Abbreviator abb = new Abbreviator(3, 8, FS);
      String r = abb.abbreviate(INPUT_0);
      assertEquals("/abc/.../ABC", r);
    }
    {
      Abbreviator abb = new Abbreviator(3, 8, FS);
      String r = abb.abbreviate(INPUT_0);
      assertEquals("/abc/.../ABC", r);
    }
  }

  public void testImpossibleToAbbreviate() {
    Abbreviator abb = new Abbreviator(2, 20, FS);
    String in = "iczldqwivpgm/mgrmvbjdxrwmqgprdjusth";
    String r = abb.abbreviate(in);
    assertEquals(in, r);
  }

  public void testNoFS() {
    Abbreviator abb = new Abbreviator(2, 100, FS);
    String r = abb.abbreviate("hello");
    assertEquals("hello", r);

  }

  public void testZeroPrefix() {
    {
      Abbreviator abb = new Abbreviator(0, 100, FS);
      String r = abb.abbreviate(INPUT_0);
      assertEquals(INPUT_0, r);
    }
  }

  public void testTheories() {
    int MAX_RANDOM_FIXED_LEN = 20;
    int MAX_RANDOM_AVG_LEN = 20;
    int MAX_RANDOM_MAX_LEN = 100;
    for (int i = 0; i < 10000; i++) {

      //System.out.println("Test number " + i);

      // 0 <= fixedLen < MAX_RANDOM_FIXED_LEN
      int fixedLen = rh.nextInt(MAX_RANDOM_FIXED_LEN);
      // 5 <= averageLen < MAX_RANDOM_AVG_LEN
      int averageLen = rh.nextInt(MAX_RANDOM_AVG_LEN) + 3;
      // System.out.println("fixedLen="+fixedLen+", averageLen="+averageLen);

      int maxLen = rh.nextInt(MAX_RANDOM_MAX_LEN) + fixedLen;
      if (maxLen <= 1) {
        continue;
      }
      // System.out.println("maxLen="+maxLen);
      int targetLen = (maxLen / 2) + rh.nextInt(maxLen / 2) + 1;

      if (targetLen > maxLen) {
        targetLen = maxLen;
      }
      String filename = rh.buildRandomFileName(averageLen, maxLen);

      Abbreviator abb = new Abbreviator(fixedLen, targetLen, FS);
      String result = abb.abbreviate(filename);
      assertTheory0(averageLen, filename, result, fixedLen, targetLen);
      assertUsefulness(averageLen, filename, result, fixedLen, targetLen);
      assertTheory1(filename, result, fixedLen, targetLen);
      assertTheory2(filename, result, fixedLen, targetLen);
    }
  }

  // result length is smaller than original length 
  void assertTheory0(int averageLen, String filename, String result,
      int fixedLen, int targetLength) {
      assertTrue("filename=[" + filename + "] result=[" + result + "]", result
        .length() <= filename.length());
  }

  // if conditions allow, result length should be to target length
  void assertUsefulness(int averageLen, String filename, String result,
      int fixedLen, int targetLength) {
    int resLen = result.length();

    int margin = averageLen * 4;
    if (targetLength > fixedLen + margin) {
      assertTrue("filename=[" + filename + "], result=[" + result
          + "] resultLength=" + resLen + " fixedLength=" + fixedLen
          + ", targetLength=" + targetLength + ", avgLen=" + averageLen, result
          .length() <= targetLength + averageLen);
    }
  }

  // result start with prefix found in filename
  void assertTheory1(String filename, String result, int fixedLen,
      int targetLength) {
    String prefix = filename.substring(0, fixedLen);
    assertTrue(result.startsWith(prefix));
  }

  // The string /.../ is found in the result once at a position higher
  // than fixedLen
  void assertTheory2(String filename, String result, int fixedLen,
      int targetLength) {
    if (filename == result) {
      return;
    }
    int fillerIndex = result.indexOf(Abbreviator.FILLER);
    assertTrue(fillerIndex >= fixedLen);
  }
}
