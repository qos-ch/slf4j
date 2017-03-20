package org.apache.commons.logging;

import org.apache.commons.logging.impl.SLF4JLogFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SLF4JLogFactoryAttributesTest {

    private SLF4JLogFactory slf4JLogFactory;

    class Dummy {

        String name;

        public Dummy(String name) {
            this.name = name;
        }
    }

    @Before
    public void setup() {
        slf4JLogFactory = new SLF4JLogFactory();
    }

    @Test
    public void testGetSetAttribute_String() throws Exception {
        //setup
        String key = "a-string-attribute";
        String stringValue = "stringValue";
        slf4JLogFactory.setAttribute(key, stringValue);
        assertThat((String) slf4JLogFactory.getAttribute(key), is(stringValue));

        //when
        slf4JLogFactory.setAttribute(key, null);

        //then
        assertEquals("value must be null", null, slf4JLogFactory.getAttribute(key));
    }

    @Test
    public void testGetSetAttribute_Integer() throws Exception {
        //setup
        String key = "an-int-attribute";
        int intValue = 100;
        slf4JLogFactory.setAttribute(key, intValue);
        assertThat((Integer) slf4JLogFactory.getAttribute(key), is(intValue));

        //when
        slf4JLogFactory.setAttribute(key, null);

        //then
        assertEquals("value must be null", null, slf4JLogFactory.getAttribute(key));
    }

    @Test
    public void testGetSetAttribute_Object() throws Exception {
        //setup
        String key = "an-object-attribute";
        Dummy objectValue = new Dummy("Sample");

        //when
        slf4JLogFactory.setAttribute("an-object-attribute", objectValue);

        //then
        assertThat((Dummy) slf4JLogFactory.getAttribute(key), is(equalTo(objectValue)));
    }

    @Test
    public void testRemoveAttribute_Object() throws Exception {
        //setup
        String key = "key";
        int value = 10;
        slf4JLogFactory.setAttribute(key, value);
        assertEquals(value, slf4JLogFactory.getAttribute(key));

        //when
        slf4JLogFactory.removeAttribute(key);

        //then
        assertEquals(null, slf4JLogFactory.getAttribute(key));
    }

    @Test
    public void testGetAttributeNames() throws Exception {
        //setup
        String[] result = slf4JLogFactory.getAttributeNames();
        assertThat("attributes are empty array", result.length, is(0));
        slf4JLogFactory.setAttribute("key1", "val1");
        slf4JLogFactory.setAttribute("key2", "val2");
        slf4JLogFactory.setAttribute("key3", "val3");

        //when
        result = slf4JLogFactory.getAttributeNames();

        //then
        assertThat("attributes must be 3", result.length, is(3));
    }
}
