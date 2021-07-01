package org.slf4j.log4j12;

import org.slf4j.helpers.BasicMDCAdapterTest;
import org.slf4j.spi.MDCAdapter;

public class Log4jMDCAdapterTest extends BasicMDCAdapterTest {
    
    protected MDCAdapter instantiateMDC() {
        return new Log4jMDCAdapter();
    }
    

}
