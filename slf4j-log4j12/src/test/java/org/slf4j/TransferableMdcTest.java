package org.slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertSame;

/**
 * @author Valentin Kovalenko
 */
public final class TransferableMdcTest {
    private static final String KEY_OUTER = "KEY_OUTER";
    private static final String VALUE_OUTER = "VALUE_OUTER";
    private static final String KEY_SHARED = "KEY_SHARED";
    private static final String VALUE_SHARED_OUTER = "VALUE_SHARED_OUTER";
    private static final String VALUE_SHARED_INNER = "VALUE_SHARED_INNER";
    private static final String KEY_INNER = "KEY_INNER";
    private static final String VALUE_INNER = "VALUE_INNER";

    private ExecutorService executor;

    public TransferableMdcTest() {
    }

    @Before
    public final void before() {
        executor = Executors.newSingleThreadExecutor();
    }

    @After
    public final void after() {
        executor.shutdownNow();
    }

    @Test
    public final void apply() throws ExecutionException, InterruptedException {
        {//prepare outer MDC
            MDC.clear();
            MDC.put(KEY_OUTER, VALUE_OUTER);
            MDC.put(KEY_SHARED, VALUE_SHARED_OUTER);
        }
        final TransferableMdc outerMdc = MDC.createTransferable();
        assertSame(VALUE_OUTER, MDC.get(KEY_OUTER));
        assertSame(VALUE_SHARED_OUTER, MDC.get(KEY_SHARED));
        assertNull(MDC.get(KEY_INNER));
        executor.submit(new Runnable() {
            public void run() {
                {//prepare inner MDC
                    MDC.clear();
                    MDC.put(KEY_SHARED, VALUE_SHARED_INNER);
                    MDC.put(KEY_INNER, VALUE_INNER);
                }
                final TransferableMdc transferredMdc = outerMdc.transfer();
                try {
                    assertSame(VALUE_OUTER, MDC.get(KEY_OUTER));
                    assertSame(VALUE_SHARED_OUTER, MDC.get(KEY_SHARED));
                    assertNull(MDC.get(KEY_INNER));
                } finally {
                    transferredMdc.close();
                }
                assertNull(MDC.get(KEY_OUTER));
                assertSame(VALUE_SHARED_INNER, MDC.get(KEY_SHARED));
                assertSame(VALUE_INNER, MDC.get(KEY_INNER));
            }
        }).get();
    }

    @Test
    public final void applyEmpty() throws ExecutionException, InterruptedException {
        {//prepare outer MDC
            MDC.clear();
        }
        final TransferableMdc outerMdc = MDC.createTransferable();
        executor.submit(new Runnable() {
            public void run() {
                {//prepare inner MDC
                    MDC.clear();
                    MDC.put(KEY_SHARED, VALUE_SHARED_INNER);
                    MDC.put(KEY_INNER, VALUE_INNER);
                }
                final TransferableMdc transferredMdc = outerMdc.transfer();
                try {
                    assertNull(MDC.get(KEY_OUTER));
                    assertNull(MDC.get(KEY_SHARED));
                    assertNull(MDC.get(KEY_INNER));
                } finally {
                    transferredMdc.close();
                }
                assertNull(MDC.get(KEY_OUTER));
                assertSame(VALUE_SHARED_INNER, MDC.get(KEY_SHARED));
                assertSame(VALUE_INNER, MDC.get(KEY_INNER));
            }
        }).get();
    }

    @Test
    public final void restoreEmpty() throws ExecutionException, InterruptedException {
        {//prepare outer MDC
            MDC.clear();
            MDC.put(KEY_OUTER, VALUE_OUTER);
            MDC.put(KEY_SHARED, VALUE_SHARED_OUTER);
        }
        final TransferableMdc outerMdc = MDC.createTransferable();
        executor.submit(new Runnable() {
            public void run() {
                {//prepare inner MDC
                    MDC.clear();
                }
                final TransferableMdc transferredMdc = outerMdc.transfer();
                try {
                    assertSame(VALUE_OUTER, MDC.get(KEY_OUTER));
                    assertSame(VALUE_SHARED_OUTER, MDC.get(KEY_SHARED));
                    assertNull(MDC.get(KEY_INNER));
                } finally {
                    transferredMdc.close();
                }
                assertNull(MDC.get(KEY_OUTER));
                assertNull(MDC.get(KEY_SHARED));
                assertNull(MDC.get(KEY_INNER));
            }
        }).get();
    }

    @Test
    public final void applyInTheSameThread() {
        {//prepare outer MDC
            MDC.clear();
            MDC.put(KEY_OUTER, VALUE_OUTER);
            MDC.put(KEY_SHARED, VALUE_SHARED_OUTER);
        }
        final TransferableMdc outerMdc = MDC.createTransferable();
        assertSame(VALUE_OUTER, MDC.get(KEY_OUTER));
        assertSame(VALUE_SHARED_OUTER, MDC.get(KEY_SHARED));
        assertNull(MDC.get(KEY_INNER));
        {//prepare inner MDC
            MDC.clear();
            MDC.put(KEY_SHARED, VALUE_SHARED_INNER);
            MDC.put(KEY_INNER, VALUE_INNER);
        }
        final TransferableMdc transferredMdc = outerMdc.transfer();
        try {
            assertSame(VALUE_OUTER, MDC.get(KEY_OUTER));
            assertSame(VALUE_SHARED_OUTER, MDC.get(KEY_SHARED));
            assertNull(MDC.get(KEY_INNER));
        } finally {
            transferredMdc.close();
        }
        //once transferredMdc is closed, we must see the inner MDC
        assertNull(MDC.get(KEY_OUTER));
        assertSame(VALUE_SHARED_INNER, MDC.get(KEY_SHARED));
        assertSame(VALUE_INNER, MDC.get(KEY_INNER));
    }

    @Test(expected = IllegalStateException.class)
    public final void closeMultipleTimes() {
        final TransferableMdc transferredMdc = MDC.createTransferable().transfer();
        transferredMdc.close();
        transferredMdc.close();
    }
}
