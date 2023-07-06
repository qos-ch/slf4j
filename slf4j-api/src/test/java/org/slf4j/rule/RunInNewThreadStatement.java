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

import org.junit.runners.model.Statement;

//This class has been inspired by the article "A JUnit Rule to Run a Test in Its Own Thread"
//published by Frank Appel, author of the book "Testing with JUnit" published by Packt publishing. 
//
//See also
//https://www.codeaffine.com/2014/07/21/a-junit-rule-to-run-a-test-in-its-own-thread/

public class RunInNewThreadStatement extends Statement implements Runnable {

    final Statement base;
    final long timeout;
    Throwable throwable;
    
    RunInNewThreadStatement(Statement base, long timeout) {
        this.base = base;
        this.timeout = timeout;
    }
    
    @Override
    public void evaluate() throws Throwable {
       Thread thread = new Thread(this);
       thread.start();
       System.out.println("Timeout is "+timeout);
       thread.join(timeout);
       
       if (throwable != null) {
           throw throwable;
       }
    }

    @Override
    public void run() {
        try {
            base.evaluate();
        } catch (Throwable e) {
            this.throwable = e;
        }
    }

    
}
