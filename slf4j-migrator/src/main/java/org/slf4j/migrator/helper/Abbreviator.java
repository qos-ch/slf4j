/**
 * Copyright (c) 2004-2011 QOS.ch
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
package org.slf4j.migrator.helper;

public class Abbreviator {
    static final String FILLER = "...";

    final char folderSeparator;
    final int invariantPrefixLength;
    final int desiredLength;

    public Abbreviator(int invariantPrefixLength, int desiredLength, char folderSeparator) {
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
            StringBuilder buf = new StringBuilder(desiredLength);
            buf.append(filename, 0, firstIndex + 1);
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
