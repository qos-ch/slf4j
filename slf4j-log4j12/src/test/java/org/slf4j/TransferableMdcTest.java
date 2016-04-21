package org.slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertSame;

public final class TransferableMdcTest {
    private static volatile ExecutorService executor;

    public TransferableMdcTest() {
    }

    @BeforeClass
    public static final void beforeClass() {
        executor = Executors.newSingleThreadExecutor();
    }

    @AfterClass
    public static final void afterClass() {
        executor.shutdownNow();
    }

    @Test
    public final void idiom() {
        final String keyTransferred = "kTransferred";
        final String valueTransferred = "vTransferred";
        MDC.put(keyTransferred, valueTransferred);
        final TransferableMdc mdc = MDC.createTransferable();
        executor.submit(new Callable<Void>() {
            public Void call() {
                final String key = "k";
                final String value = "v";
                MDC.put(key, value);
                TransferableMdc mdcTmp = mdc.apply();
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
        });
    }
}