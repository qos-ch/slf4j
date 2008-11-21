package org.slf4j.migrator.helper;

public class Abbreviator {
  static final String FILLER = "...";

  final char folderSeparator;
  final int invariantPrefixLength;
  final int desiredLength;

  public Abbreviator(int invariantPrefixLength, int desiredLength,
      char folderSeparator) {
    this.invariantPrefixLength = invariantPrefixLength;
    this.desiredLength = desiredLength;
    this.folderSeparator = folderSeparator;
  }

  public String abbreviate(String filename) {
    if (filename.length() <= desiredLength) {
      return filename;
    } else {

      int firstIndex = filename.indexOf(folderSeparator, invariantPrefixLength);
      if (firstIndex == -1) {
        // we cant't process this string
        return filename;
      }
      StringBuffer buf = new StringBuffer(desiredLength);
      buf.append(filename.substring(0, firstIndex + 1));
      buf.append(FILLER);
      int nextIndex = computeNextIndex(filename, firstIndex);
      if (nextIndex != -1) {
        buf.append(filename.substring(nextIndex));
      } else {
        // better long than wrong
        return filename;
      }

      if (buf.length() < filename.length()) {
        return buf.toString();
      } else {
        // we tried our best but we are still could not shorten the input
        return filename;
      }
    }
  }

  int computeNextIndex(String filename, int firstIndex) {
    int nextIndex = firstIndex + 1;
    int hitCount = 0;
    int minToRemove = filename.length() - desiredLength + FILLER.length();
    while (nextIndex < firstIndex + minToRemove) {
      int tmpIndex = filename.indexOf(folderSeparator, nextIndex + 1);
      if (tmpIndex == -1) {
        if (hitCount == 0) {
          return -1;
        } else {
          return nextIndex;
        }
      } else {
        hitCount++;
        nextIndex = tmpIndex;
      }
    }
    return nextIndex;
  }
}
