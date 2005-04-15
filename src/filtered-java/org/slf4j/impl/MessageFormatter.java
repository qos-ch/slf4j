package org.slf4j.impl;


/**
 * Formats messages according to very simple rules. 
 * See {@link #format(String, Object)} and 
 * {@link #format(String, Object, Object)} for more details.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class MessageFormatter {
  static final char DELIM_START = '{';
  static final char DELIM_STOP = '}';

  /**
   * Performs single argument substitution for the 'messagePattern' passed as
   * parameter.
   * <p>
   * For example, <code>MessageFormatter.format("Hi {}.", "there");</code> will
   * return the string "Hi there.".
   * <p>
   * The {} pair is called the formatting element. It serves to designate the
   * location where the argument needs to be inserted within the pattern.
   * 
   * @param messagePattern The message pattern which will be parsed and formatted
   * @param argument The argument to be inserted instead of the formatting element
   * @return The formatted message
   */
  public static String format(String messagePattern, Object argument) {
    int j = messagePattern.indexOf(DELIM_START);
    int len = messagePattern.length();
    char escape = 'x';

    // if there are no { characters or { is the last character of the messsage
    // then we just return messagePattern
    if (j == -1 || (j+1 == len)) {
      return messagePattern;
    } else {
      if(j+1 == len) {
      }
      
      char delimStop = messagePattern.charAt(j + 1);
      if (j > 0) {
        escape = messagePattern.charAt(j - 1);
      }
      if ((delimStop != DELIM_STOP) || (escape == '\\')) {
        // invalid DELIM_START/DELIM_STOP pair or espace character is
        // present
        return messagePattern;
      } else {
        StringBuffer sbuf = new StringBuffer(len + 20);
        sbuf.append(messagePattern.substring(0, j));
        sbuf.append(argument);
        sbuf.append(messagePattern.substring(j + 2));
        return sbuf.toString();
      }
    }
  }

  /**
   * /**
   * Performs a two argument substitution for the 'messagePattern' passed as
   * parameter.
   * <p>
   * For example, <code>MessageFormatter.format("Hi {}. My name is {}.", 
   * "there", "David");</code> will return the string "Hi there. My name is David.".
   * <p>
   * The '{}' pair is called a formatting element. It serves to designate the
   * location where the arguments need to be inserted within the message pattern.
   * 
   * @param messagePattern The message pattern which will be parsed and formatted
   * @param arg1 The first argument to replace the first formatting element
   * @param arg2 The second argument to replace the second formatting element
   * @return The formatted message
   */
  public static String format(String messagePattern, Object arg1, Object arg2) {
    int i = 0;
    int len = messagePattern.length();
    int j = messagePattern.indexOf(DELIM_START);

    StringBuffer sbuf = new StringBuffer(messagePattern.length() + 50);

    for (int L = 0; L < 2; L++) {
      j = messagePattern.indexOf(DELIM_START, i);

      if (j == -1 || (j+1 == len)) {
        // no more variables
        if (i == 0) { // this is a simple string
          return messagePattern;
        } else { // add the tail string which contains no variables and return the result.
          sbuf.append(messagePattern.substring(i, messagePattern.length()));
          return sbuf.toString();
        }
      } else {
        char delimStop = messagePattern.charAt(j + 1);
        if ((delimStop != DELIM_STOP)) {
          // invalid DELIM_START/DELIM_STOP pair
          sbuf.append(messagePattern.substring(i, messagePattern.length()));
          return sbuf.toString();
        }
        sbuf.append(messagePattern.substring(i, j));
        sbuf.append((L == 0) ? arg1 : arg2);
        i = j + 2;
      }
    }
    // append the characters following the second {} pair.
    sbuf.append(messagePattern.substring(i, messagePattern.length()));
    return sbuf.toString();
  }
}
