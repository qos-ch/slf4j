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

package org.apache.log4j;

import java.net.URL;
import java.util.Properties;

import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

/**
 * An nop implementation of PropertyConfigurator.
 */
public class PropertyConfigurator implements Configurator {
    public static void configure(Properties properties) {
    }

    public static void configure(String configFilename) {
    }

    public static void configure(java.net.URL configURL) {
    }

    public static void configureAndWatch(String configFilename) {
    }

    public static void configureAndWatch(String configFilename, long delay) {
    }

    public void doConfigure(Properties properties, LoggerRepository hierarchy) {
    }

    public void doConfigure(String configFileName, LoggerRepository hierarchy) {
    }

    public void doConfigure(URL configURL, LoggerRepository hierarchy) {
    }
}
