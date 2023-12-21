package org.slf4j.event;

import java.util.Objects;

/**
 * Instances of this class store the key value pair passed to a {@link org.slf4j.Logger logger} via
 * the {@link org.slf4j.spi.LoggingEventBuilder#addKeyValue(String, Object)} method of the fluent API.
 *
 * @since 2.0.0
 */
public class KeyValuePair {

    public final String key;
    public final Object value;

    public KeyValuePair(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(key) + "=\"" + String.valueOf(value) +"\"";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        KeyValuePair that = (KeyValuePair) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
