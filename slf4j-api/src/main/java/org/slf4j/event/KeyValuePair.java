package org.slf4j.event;

public class KeyValuePair {

    public final String key;
    public final Object value;

    public KeyValuePair(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=\"" + String.valueOf(value) +"\"";
    }
}
