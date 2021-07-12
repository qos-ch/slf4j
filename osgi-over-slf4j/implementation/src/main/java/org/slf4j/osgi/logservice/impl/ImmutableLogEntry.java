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
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;

import java.util.Date;

/**
 * Immutable implementation of {@link LogEntry}.
 *
 * @author Matt Bishop
 */
class ImmutableLogEntry implements LogEntry {

  private final Bundle bundle;
  private final ServiceReference serviceReference;
  private final int level;
  private final String message;
  private final Throwable exception;
  private final long time;

  ImmutableLogEntry(Bundle bundle, int level, String message) {
    this(bundle, null, level, message, null);
  }

  ImmutableLogEntry(Bundle bundle, int level, String message, Throwable exception) {
    this(bundle, null, level, message, exception);
  }

  ImmutableLogEntry(Bundle bundle, ServiceReference reference, int level, String message) {
    this(bundle, reference, level, message, null);
  }

  ImmutableLogEntry(Bundle bundle, ServiceReference reference, int level, String message, Throwable exception) {
    this.bundle = bundle;
    this.serviceReference = reference;
    this.level = level;
    this.message = message;
    this.exception = exception;
    this.time = System.currentTimeMillis();
  }

  public Bundle getBundle() {
    return bundle;
  }

  public ServiceReference getServiceReference() {
    return serviceReference;
  }

  public int getLevel() {
    return level;
  }

  public String getMessage() {
    return message;
  }

  public Throwable getException() {
    return exception;
  }

  public long getTime() {
    return time;
  }

  public String toString() {
    return "LogEntry {" +
            "bundle=" + bundle +
            ", serviceReference=" + serviceReference +
            ", level=" + level +
            ", message='" + message + '\'' +
            ", exception=" + exception +
            ", time=" + new Date(time) +
            '}';
  }
}
