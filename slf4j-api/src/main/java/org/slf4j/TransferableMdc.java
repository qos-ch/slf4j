package org.slf4j;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.spi.MDCAdapter;

/**
 * An read-only {@link MDCAdapter} which allows to copy state of {@link MDC} between {@linkplain Thread threads}.
 * <p>
 * This class is not thread-safe, but it correctly transfers {@link MDC} if used according the provided idiom.
 * <p>
 * <b>Usage examples (Java 8 syntax is used).</b>
 * <p>
 * <i>Correct</i>:
 * <pre>{@code
 * TransferableMdc mdc = TransferableMdc.current();
 * executor.submit(() -> {
 *  try (@SuppressWarnings("unused") Mdc mdcTmp = mdc.apply()) {
 *      logger.info("This call can access contents of mdc via org.slf4j.MDC");
 *  }
 *  logger.info("This call can not access contents of mdc via org.slf4j.MDC");
 * });
 * }</pre>
 * <i>Incorrect 1</i>:
 * <pre>{@code
 * executor.submit(() -> {
 *  try (@SuppressWarnings("unused") Mdc mdcTmp = TransferableMdc.current().apply()) {
 *      logger.info("This call can access contents of mdc via org.slf4j.MDC");
 *  }
 *  logger.info("This call can not access contents of mdc via org.slf4j.MDC");
 * });
 * }</pre>
 * This is incorrect because this way we do not have access to the context data of the "parent" thread.
 * <p>
 * <i>Incorrect 2</i>:
 * <pre>{@code
 * TransferableMdc mdc = TransferableMdc.current();
 * executor.submit(() -> {//task1
 *  try (TransferableMdc mdcTmp = mdc.apply()) {
 *      //...
 *  }
 * });
 * executor.submit(() -> {//task2
 *  try (TransferableMdc mdcTmp = mdc.apply()) {
 *      //...
 *  }
 * });
 * }</pre>
 * This is incorrect because {@code task1} and {@code task2} may be executed concurrently and hence
 * methods {@link #apply()} and {@link #close()} may be executed concurrently, but these methods are not thread-safe.
 * The simple way to fix the incorrect example is as follows:
 * <pre>{@code
 * TransferableMdc mdc1 = TransferableMdc.current();
 * executor.submit(() -> {//task1
 *  try (TransferableMdc mdcTmp = mdc1.apply()) {
 *      //...
 *  }
 * });
 * TransferableMdc mdc2 = TransferableMdc.current();
 * executor.submit(() -> {//task2
 *  try (TransferableMdc mdcTmp = mdc2.apply()) {
 *      //...
 *  }
 * });
 * }</pre>
 */
//@NotThreadSafe
public final class TransferableMdc implements MDCAdapter, Closeable {
    /**
     * Creates {@link TransferableMdc} which holds current {@linkplain Thread thread}'s
     * {@link MDC} {@linkplain MDC#getCopyOfContextMap() context map}.
     *
     * @return New {@link TransferableMdc} which state is identical to the current
     * {@linkplain Thread thread}'s state of {@link MDC}.
     */
    public static final TransferableMdc current() {
        return new TransferableMdc(MDC.getCopyOfContextMap());
    }

    //@Nullable
    private final Map<String, String> context;
    //@Nullable
    private Map<String, String> backup;

    private TransferableMdc(/*@Nullable*/ final Map<String, String> contextMap) {
        context = (contextMap == null || contextMap.isEmpty()) ? null : new HashMap<String, String>(contextMap);
    }

    /**
     * Merges this {@link TransferableMdc} into the current {@link Thread}'s {@link MDC}
     * and retains original state of the current {@link MDC}.
     *
     * @return {@code this}.
     */
    public final TransferableMdc apply() {
        if (context != null) {
            //@Nullable
            final Map<String, String> currentContext = MDC.getCopyOfContextMap();
            backup = (currentContext == null || currentContext.isEmpty()) ? null : currentContext;
            for (final Map.Entry<String, String> contextEntry : context.entrySet()) {
                MDC.put(contextEntry.getKey(), contextEntry.getValue());
            }
        }
        return this;
    }

    /**
     * Restores current {@link Thread}'s {@link MDC} to its state before {@link #apply()}
     * (this state was retained by {@link #apply()}).
     */
    //@Override
    public void close() {
        if (context != null) {
            MDC.clear();
            if (backup != null) {
                MDC.setContextMap(backup);
                backup = null;
            }
        }
    }

    /**
     * @throws UnsupportedOperationException Always.
     */
    //@Override
    public final void put(final String key, final String val) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    //@Override
    //@Nullable
    public final String get(final String key) {
        if (key == null) {
            throw new NullPointerException("The argument key must not be null");
        }
        return (context == null) ? null : context.get(key);
    }

    /**
     * @throws UnsupportedOperationException Always.
     */
    //@Override
    public final void remove(final String key) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException Always.
     */
    //@Override
    public final void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    //@Override
    //@Nullable
    public final Map<String, String> getCopyOfContextMap() {
        return (context == null) ? null : new HashMap<String, String>(context);
    }

    /**
     * @throws UnsupportedOperationException Always.
     */
    //@Override
    public final void setContextMap(/*@Nullable*/ final Map<String, String> contextMap) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName()
            + "(context=" + context
            + ')';
    }
}