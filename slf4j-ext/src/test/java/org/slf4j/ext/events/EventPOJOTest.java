package org.slf4j.ext.events;

import org.junit.Test;
import org.slf4j.ext.events.example1.Event;

/**
 * @author Himanshu Vijay
 */
public class EventPOJOTest {
    @Test
    public void testCSKV(){
        Event.get().getWho().setId("johndoe");
        Event.get().getWhat().getOutcome().getError().set("Unknown Error");

        //EventExample2.get().who.
    }
}
