package org.slf4j;

import java.util.HashSet;
import java.util.Set;

public class MDCHelper  {


    Set<String> keySet = new HashSet();

    public MDCHelper() {

    }

    public MDCHelper put(String key, String value) {
        MDC.put(key, value);
        keySet.add(key);
        return this;
    }

    public MDCHelper addKey(String key) {
        keySet.add(key);
        return this;
    }

    public MDCHelper addKeys(String... keys) {
        for(String k: keys) {
            keySet.add(k);
        }
        return this;
    }


    public void removeSet() {
        for(String key: keySet) {
            MDC.remove(key);
        }
    }
}
