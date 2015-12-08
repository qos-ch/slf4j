/**
 * Copyright (c) 2015 QOS.ch
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
 */

package org.slf4j.osgi.logservice.impl;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

/**
 * <code>LogReaderServiceFactory</code> creates {@link org.osgi.service.log.LogReaderService} implementations.
 * ServiceFactory is necessary to capture the bundle's listeners that are registered with the LogReaderService so
 * they can be removed when the bundle is stopped or the LogReaderService is returned.
 *
 * @author Matt Bishop
 */
class LogReaderServiceFactory implements ServiceFactory {

    private final Log log;

    LogReaderServiceFactory(Log log) {
    this.log = log;
  }

    public Object getService(Bundle bundle, ServiceRegistration registration) {
        return new LogReaderServiceImpl(log);
    }

    public void ungetService(Bundle bundle, ServiceRegistration registration, Object service) {
        ((LogReaderServiceImpl) service).stop();
    }
}
