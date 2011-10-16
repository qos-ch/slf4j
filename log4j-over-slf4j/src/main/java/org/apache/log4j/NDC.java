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
package org.apache.log4j;

import org.slf4j.MDC;

import java.util.Stack;

/**
 * A log4j's NDC implemented in terms of SLF4J MDC primitives.
 *
 * @since SLF4J 1.6.0
 */

public class NDC {

  public final static String PREFIX = "NDC";

  public static void clear() {
    int depth = getDepth();
    for (int i = 0; i < depth; i++) {
      String key = PREFIX + i;
      MDC.remove(key);
    }
  }

  public static Stack cloneStack() {
    return null;
  }

  public static void inherit(Stack stack) {
  }

  static public String get() {
    return null;
  }

  public static int getDepth() {
    int i = 0;
    while (true) {
      String val = MDC.get(PREFIX + i);
      if (val != null) {
        i++;
      } else {
        break;
      }
    }
    return i;
  }

  public static String pop() {
    int next = getDepth();
    if (next == 0) {
      return "";
    }
    int last = next - 1;
    String key = PREFIX + last;
    String val = MDC.get(key);
    MDC.remove(key);
    return val;
  }

  public static String peek() {
    int next = getDepth();
    if (next == 0) {
      return "";
    }
    int last = next - 1;
    String key = PREFIX + last;
    String val = MDC.get(key);
    return val;
  }

  public static void push(String message) {
    int next = getDepth();
    MDC.put(PREFIX + next, message);
  }

  static public void remove() {
    clear();
  }

  static public void setMaxDepth(int maxDepth) {
  }

}
