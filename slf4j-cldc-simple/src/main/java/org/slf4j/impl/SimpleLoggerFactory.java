/* 
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.ch
 *
 * All rights reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 * 
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

package org.slf4j.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * An implementation of {@link ILoggerFactory} which always returns
 * {@link SimpleLogger} instances.
 * 
 * <p>
 * Modified version to support CLDC 1.1
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @author Marcel Patzlaff
 */
public class SimpleLoggerFactory implements ILoggerFactory {

    final static SimpleLoggerFactory INSTANCE= new SimpleLoggerFactory();

    Hashtable loggerMap;
    int logLevel;

    public SimpleLoggerFactory() {
        loggerMap= new Hashtable();
        logLevel= getLogLevel();
    }

    /**
     * Return an appropriate {@link SimpleLogger} instance by name.
     */
    public Logger getLogger(String name) {
        Logger slogger= null;
        // protect against concurrent access of the loggerMap
        synchronized (this) {
            slogger= (Logger) loggerMap.get(name);
            if (slogger == null) {
                slogger= new SimpleLogger(name, logLevel);
                loggerMap.put(name, slogger);
            }
        }
        return slogger;
    }
    
    private int getLogLevel() {
        InputStream in= getClass().getResourceAsStream("/logLevel.slf4j");
        
        if(in != null) {
            try {
                if(in.available() >= 1) {
                    return Character.digit((char) in.read(), 10);
                }
            } catch (IOException e) {
                // fall through
            } finally {
                try {in.close();} catch (IOException e) {}
            }
        }
        
        System.err.println("Defaulting to log level INFO");
        return 3;
    }
}
