/**
 * Copyright (c) 2004-2021 QOS.ch
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
package org.slf4j.jdk.platform.logging;

/**
 * Uses {@link SLF4JPlatformLoggerFactory#getLogger(String)} to get a logger
 * that is adapted for {@link System.Logger}.
 * 
 * @since 2.0.0
 */
public class SLF4JSystemLoggerFinder extends System.LoggerFinder {

    final SLF4JPlatformLoggerFactory platformLoggerFactory = new SLF4JPlatformLoggerFactory();
    
    @Override
    public System.Logger getLogger(String name, Module module) {
        // JEP 264[1], which introduced the Platform Logging API,
        // contains the following note:
        //
        //  > An implementation of the LoggerFinder service should make it
        //  > possible to distinguish system loggers (used by system classes
        //  > from the Bootstrap Class Loader (BCL)) and application loggers
        //  > (created by an application for its own usage). This distinction
        //  > is important for platform security. The creator of a logger can
        //  > pass the class or module for which the logger is created to the
        //  > LoggerFinder so that the LoggerFinder can figure out which kind
        //  > of logger to return.
        //
        // If backends support this distinction and once `LoggerFactory`'s API 
        // is updated to forward a module, we should do that here.
        //
        // [1] https://openjdk.java.net/jeps/264
        SLF4JPlatformLogger adapter = platformLoggerFactory.getLogger(name);
        return adapter;
    }

}