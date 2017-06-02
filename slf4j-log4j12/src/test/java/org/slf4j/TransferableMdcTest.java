package org.slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.AfterClass;
import org.junit.Test;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertSame;

public final class TransferableMdcTest {
    private static volatile ExecutorService executor;

    static {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            public void run() {
                //this task forces the executor to pre-initialize a thread
            }
        });
    }

    @AfterClass
    public static final void afterClass() {
        executor.shutdownNow();
    }

    public TransferableMdcTest() {
    }

    @Test
    public final void idiom() throws Exception {
        final String keyTransferred = "kTransferred";
        final String key = "k";
        final String valueTransferred = "vTransferred";
        MDC.put(keyTransferred, valueTransferred);
        final TransferableMdc mdc = MDC.createTransferable();
        executor.submit(new Callable<Void>() {
            public Void call() {
                final String value = "v";
                MDC.put(key, value);
                final TransferableMdc mdcTmp = mdc.apply();
                try {
                    assertSame(valueTransferred, MDC.get(keyTransferred));
                    assertSame(value, MDC.get(key));
                } finally {
                    mdcTmp.close();
                }
                assertNull(MDC.get(keyTransferred));
                assertSame(value, MDC.get(key));
                return null;
            }
        }).get();
        assertSame(valueTransferred, MDC.get(keyTransferred));
        assertNull(MDC.get(key));
    }
}