/**
 * Copyright (c) 2021 QOS.ch
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

package org.slf4j.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

// This class has been inspired by the article "A JUnit Rule to Run a Test in Its Own Thread"
// published by Frank Appel, author of the book "Testing with JUnit" published by Packt publishing. 
// 
// See also
// https://www.codeaffine.com/2014/07/21/a-junit-rule-to-run-a-test-in-its-own-thread/
    
public class RunInNewThreadRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        RunInNewThread desiredAnnotaton = description.getAnnotation(RunInNewThread.class);
        
        if (desiredAnnotaton ==  null) {
            System.out.println("test "+ description.getMethodName() +" not annotated");
            return base;
        } else {
            long timeout = desiredAnnotaton.timeout();
            System.out.println("running "+ description.getMethodName() +" in separate tjread");
            return new RunInNewThreadStatement(base, timeout);
        }
    }

}
