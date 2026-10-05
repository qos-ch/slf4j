package org.slf4j.helpers;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Test;
import org.slf4j.spi.MDCAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MDCAdapterDefaultPeekTest {

    @Test
    public void missingDequeReturnsNull() {
        MDCAdapter adapter = new NOPMDCAdapter();
        assertNull(adapter.peekByKey("missing"));
        assertNull(adapter.peekByKey(null));
    }

    @Test
    public void emptyDequeReturnsNull() {
        MDCAdapter adapter = adapterWithStack(new ArrayDeque<>());
        assertNull(adapter.peekByKey("stack"));
    }

    @Test
    public void defaultMethodPeeksWithoutRemovingValues() {
        Deque<String> stack = new ArrayDeque<>();
        stack.push("first");
        stack.push("second");
        MDCAdapter adapter = adapterWithStack(stack);
        assertEquals("second", adapter.peekByKey("stack"));
        assertEquals("second", adapter.peekByKey("stack"));
        assertEquals(2, stack.size());
        assertEquals("second", stack.pop());
        assertEquals("first", adapter.peekByKey("stack"));
        stack.pop();
        assertNull(adapter.peekByKey("stack"));
    }

    private MDCAdapter adapterWithStack(Deque<String> stack) {
        // This adapter inherits the new default method without implementing it.
        return new NOPMDCAdapter() {
            @Override
            public Deque<String> getCopyOfDequeByKey(String key) {
                return new ArrayDeque<>(stack);
            }
        };
    }
}
