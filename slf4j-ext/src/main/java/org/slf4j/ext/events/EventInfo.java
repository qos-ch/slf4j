package org.slf4j.ext.events;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Himanshu Vijay
 */
public abstract class EventInfo implements Cloneable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventInfo.class);

    private static final InheritableThreadLocal<EventInfo> holder = new InheritableThreadLocal<EventInfo>(){
        @Override
        protected EventInfo initialValue() {
            return EventInfo.newInstance();
        }

        @Override
        protected EventInfo childValue(EventInfo parentValue) {
            if (parentValue != null) {
                try {
                    return (EventInfo) parentValue.clone();
                } catch (CloneNotSupportedException e) {
                    LOGGER.error(e.getMessage());
                }
            }
            return EventInfo.newInstance();
        }
    };

    /**
     * <b>Must override this method.</b>
     */
    protected static EventInfo newInstance(){
        throw new NotImplementedException();
    }

    public static EventInfo get() {
        return (EventInfo) holder.get();
    }

    public static void reset() {
        holder.get().resetToDefault();
    }

    /**
     * Replaces only if other != null.
     * @throws CloneNotSupportedException If clone() has not been implemented by child class.
     */
    public static void replace(EventInfo other) throws CloneNotSupportedException {
        if (other != null) {
            holder.set((EventInfo)other.clone());
        }
    }

    protected abstract void resetToDefault();
}
