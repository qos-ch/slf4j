package org.slf4j.ext.events;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Himanshu Vijay
 */
public class NodeTest {
    class TestNode extends Node{
        public TestNode(String name, Node parent){
            super(name, parent);
        }

        @Override
        protected void setToDefault() {}
    }

    @Test
    public void testFqnWhenNoParent(){
        Node n1 = new TestNode("root", null);
        assertEquals("root", n1.FQN);
    }

    @Test
    public void testFqnWhenParent(){
        Node n1 = new TestNode("root", null);
        Node n2 = new TestNode("child1", n1);
        assertEquals("root.child1", n2.FQN);
        Node n3 = new TestNode("grandChild1", n2);
        assertEquals("root.child1.grandChild1", n3.FQN);
    }
}
