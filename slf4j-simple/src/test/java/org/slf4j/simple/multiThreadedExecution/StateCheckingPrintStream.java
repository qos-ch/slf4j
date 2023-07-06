/**
 * Copyright (c) 2004-2021 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
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
package org.slf4j.simple.multiThreadedExecution;

import java.io.PrintStream;
import java.util.regex.Pattern;

public class StateCheckingPrintStream extends PrintStream {

    static String PACKAGE_NAME = "org.slf4j.simple.multiThreadedExecution";
    
    
    enum State {
        INITIAL, UNKNOWN, HELLO, THROWABLE, AT1, AT2, OTHER;
    }

    PrintStream other;

    volatile State currentState = State.INITIAL;

    public StateCheckingPrintStream(PrintStream ps) {
        super(ps);
    }

    public void print(String s) {
    }

    public void println(String s) {

        State next = computeState(s);
        //System.out.println(next + " " + s);
        switch (currentState) {
        case INITIAL:
            currentState = next;
            break;

        case UNKNOWN:
            currentState = next;
            break;

        case OTHER:
            if (next == State.UNKNOWN) {
                currentState = State.UNKNOWN;
                return;
            }

            if (next != State.OTHER && next != State.HELLO) {
                throw badState(s, currentState, next);
            }
            currentState = next;
            break;

        case HELLO:
            if (next != State.THROWABLE) {
                throw badState(s, currentState, next);
            } 
            currentState = next;
            break;
        case THROWABLE:
            if (next != State.AT1) {
                throw badState(s, currentState, next);
            }
            currentState = next;
            break;

        case AT1:
            if (next != State.AT2) {
                throw badState(s, currentState, next);
            }
            currentState = next;
            break;

        case AT2:
            currentState = next;
            break;
        default:
            throw new IllegalStateException("Unreachable code");
        }
    }

    private IllegalStateException badState(String s, State currentState2, State next) {
        return new IllegalStateException("Unexpected state " + next + " for current state " + currentState2 + " for " + s);

    }

    String OTHER_PATTERN_STR = ".*Other \\d{1,5}";
    String HELLO_PATTERN_STR = ".*Hello \\d{1,5}";
    String THROWABLE_PATTERN_STR = "java.lang.Throwable: i=\\d{1,5}";
    String AT1_PATTERN_STR = "\\s*at " + PACKAGE_NAME + ".*";
    String AT2_PATTERN_STR = "\\s*at " + ".*Thread.java.*";

    Pattern PATTERN_OTHER = Pattern.compile(OTHER_PATTERN_STR);
    Pattern PATTERN_HELLO = Pattern.compile(HELLO_PATTERN_STR);
    Pattern PATTERN_THROWABLE = Pattern.compile(THROWABLE_PATTERN_STR);
    Pattern PATTERN_AT1 = Pattern.compile(AT1_PATTERN_STR);
    Pattern PATTERN_AT2 = Pattern.compile(AT2_PATTERN_STR);

    private State computeState(String s) {

        if (PATTERN_OTHER.matcher(s).matches()) {
            return State.OTHER;
        } else if (PATTERN_HELLO.matcher(s).matches()) {
            return State.HELLO;
        } else if (PATTERN_THROWABLE.matcher(s).matches()) {
            return State.THROWABLE;
        } else if (PATTERN_AT1.matcher(s).matches()) {
            return State.AT1;
        } else if (PATTERN_AT2.matcher(s).matches()) {
            return State.AT2;
        } else {
            return State.UNKNOWN;
        }
    }

    public void println(Object o) {
        println(o.toString());
    }
}