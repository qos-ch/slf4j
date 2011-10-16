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
package org.apache.log4j.spi;


import org.apache.log4j.spi.LoggerRepository;
import java.net.URL;

/**
   Implemented by classes capable of configuring log4j using a URL.

   @since 1.0
   @author Anders Kristensen
 */
public interface Configurator {

  /**
     Special level value signifying inherited behaviour. The current
     value of this string constant is <b>inherited</b>. {@link #NULL}
     is a synonym.  */
  public static final String INHERITED = "inherited";

  /**
     Special level signifying inherited behaviour, same as {@link
     #INHERITED}. The current value of this string constant is
     <b>null</b>. */
  public static final String NULL = "null";



  /**
     Interpret a resource pointed by a URL and set up log4j accordingly.

     The configuration is done relative to the <code>hierarchy</code>
     parameter.

     @param url The URL to parse
     @param repository The hierarchy to operation upon.
   */
  void doConfigure(URL url, LoggerRepository repository);
}
