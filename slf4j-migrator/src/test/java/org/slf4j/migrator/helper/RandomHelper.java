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

import java.util.Random;

public class RandomHelper {

    private Random random = new Random(100);
    final char folderSeparator;

    RandomHelper(char folderSeparator) {
        this.folderSeparator = folderSeparator;
    }

    private String randomString(int len) {
        StringBuilder buf = new StringBuilder();
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
        StringBuilder buf = new StringBuilder();
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
