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

import org.apache.log4j.*;

/**
   Listen to events occuring within a {@link
   org.apache.log4j.Hierarchy Hierarchy}.

   @author Ceki G&uuml;lc&uuml;
   @since 1.2

 */
public interface HierarchyEventListener {

    // public
    // void categoryCreationEvent(Category cat);

    public void addAppenderEvent(Category cat, Appender appender);

    public void removeAppenderEvent(Category cat, Appender appender);

}
