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
package org.slf4j.helpers;

/**
 * Holds the results of formatting done by {@link MessageFormatter}.
 * 
 * @author Joern Huxhorn
 */
public class FormattingTuple {

    static public FormattingTuple NULL = new FormattingTuple(null);

    private String message;
    private Throwable throwable;
    private Object[] unusedArgArray;

    public FormattingTuple(String message) {
        this(message, null, null);
    }

    /**
     * Constructs a FormattingTuple.
     * @param message the formatted message
     * @param unusedArgArray the args that were not used in the formatted message
     * @param throwable a Throwable
     */
    public FormattingTuple(String message, Object[] unusedArgArray, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
        this.unusedArgArray = unusedArgArray;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Gets the args that were not used in the formatted message.
     * @return the unused args
     * @deprecated use {@link #getUnusedArgArray()}
     */
    @Deprecated
    public Object[] getArgArray() {
        return getUnusedArgArray();
    }

    /**
     * Gets the args that were not used in the formatted message.
     * @return the unused args
     */
    public Object[] getUnusedArgArray() {
        return unusedArgArray;
    }

    /**
     * Gets the message including any unused args, separated by a space.
     * This is done on-demand to avoid incurring the cost when it isn't used.
     */
    public String getMessageWithUnusedArgs() {
        String message = getMessage();
        if ((getUnusedArgArray() != null) && (getUnusedArgArray().length > 0)) {
            StringBuilder stringBuilder = new StringBuilder(message);
            for(int i = 0; i < getUnusedArgArray().length; i++) {
                Object arg = getUnusedArgArray()[i];
                if (arg != null) {
                    stringBuilder.append(' ').append(arg);
                }
            }
            message = stringBuilder.toString();
        }
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
