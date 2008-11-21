package org.slf4j.migrator.helper;

import java.util.Random;

public class RandomHelper {

  private Random random = new Random(100);
  final char folderSeparator;
  
  RandomHelper(char folderSeparator) {
   this.folderSeparator = folderSeparator;
  }
  
  private String randomString(int len) {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < len; i++) {
      int offset = random.nextInt(26);
      char c = (char) ('a' + offset);
      buf.append(c);
    }
    return buf.toString();
  }

  int nextInt(int n) {
    return random.nextInt(n);
  }
  
  String buildRandomFileName(int averageNodeLength, int totalLength) {
    StringBuffer buf = new StringBuffer();
    int MAX_NODE_LENGTH = averageNodeLength * 2;
    while (buf.length() < totalLength) {
      int remaining = totalLength - buf.length();
      int currentNodeLength;
      if (remaining > MAX_NODE_LENGTH) {
        currentNodeLength = random.nextInt(MAX_NODE_LENGTH) + 1;
        buf.append(randomString(currentNodeLength));
        buf.append('/');
      } else {
        currentNodeLength = remaining;
        buf.append(randomString(currentNodeLength));
      }
    }
    return buf.toString();
  }

}
