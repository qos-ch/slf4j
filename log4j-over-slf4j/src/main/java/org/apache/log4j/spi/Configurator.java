/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
